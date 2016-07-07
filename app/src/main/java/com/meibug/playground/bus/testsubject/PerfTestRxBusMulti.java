/*
 * Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.meibug.playground.bus.testsubject;

import android.content.Context;

import com.meibug.playground.bus.Test;
import com.meibug.playground.bus.TestEvent;
import com.meibug.playground.bus.TestEvent0;
import com.meibug.playground.bus.TestEvent1;
import com.meibug.playground.bus.TestEvent2;
import com.meibug.playground.bus.TestEvent3;
import com.meibug.playground.bus.TestEvent4;
import com.meibug.playground.bus.TestEvent5;
import com.meibug.playground.bus.TestEvent6;
import com.meibug.playground.bus.TestEvent7;
import com.meibug.playground.bus.TestEvent8;
import com.meibug.playground.bus.TestEvent9;
import com.meibug.playground.bus.TestParams;
import com.meibug.playground.rx.RxBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import rx.Subscription;
import rx.functions.Action1;

public abstract class PerfTestRxBusMulti extends Test {

    private final ArrayList<Object> subscribers;
    private final ArrayList<Subscription> eventSubscribers;
    private final Class<?> subscriberClass;
    private final int eventCount;
    private final int expectedEventCount;
    protected final int EVENTKINDCOUNT = 10;

    public PerfTestRxBusMulti(Context context, TestParams params) {
        super(context, params);
        subscribers = new ArrayList<Object>();
        eventSubscribers = new ArrayList<>();
        eventCount = params.getEventCount();
        expectedEventCount = eventCount * params.getSubscriberCount();
        subscriberClass = getSubscriberClassForThreadMode();
    }

    @Override
    public void prepareTest() {
        try {
            Constructor<?> constructor = subscriberClass.getConstructor(PerfTestRxBusMulti.class);
            for (int i = 0; i < params.getSubscriberCount(); i++) {
                Object subscriber = constructor.newInstance(this);
                subscribers.add(subscriber);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getSubscriberClassForThreadMode() {
        switch (params.getThreadMode()) {
            case MAIN:
                return SubscribeClassRxBusMain.class;
            case BACKGROUND:
                return SubscribeClassRxBusBackground.class;
            case ASYNC:
                return SubscribeClassRxBusAsync.class;
            case POSTING:
                return SubscribeClassRxBusDefaultMulti.class;
            default:
                throw new RuntimeException("Unknown: " + params.getThreadMode());
        }
    }

    private static String getDisplayModifier(TestParams params) {
        String inheritance = params.isEventInheritance() ? "" : ", no event inheritance";
        String ignoreIndex = params.isIgnoreGeneratedIndex() ? ", ignore index" : "";
        return inheritance + ignoreIndex;
    }

    protected void unSubscribeAll() {
        for (Subscription sub: eventSubscribers) {
            sub.unsubscribe();
        }
    }

    public static class Post extends PerfTestRxBusMulti {
        public Post(Context context, TestParams params) {
            super(context, params);
        }

        @Override
        public void prepareTest() {
            super.prepareTest();
            super.registerSubscribers();
        }

        public void runTest() {
            TestEvent event;
            long timeStart = System.nanoTime();
            for (int i = 0; i < super.eventCount; i++) {
                event = TestEvent.createEvent(i % EVENTKINDCOUNT);
                RxBus.INSTANCE.post(event);
                if (canceled) {
                    break;
                }
            }
            long timeAfterPosting = System.nanoTime();
            waitForReceivedEventCount(super.expectedEventCount);
            long timeAllReceived = System.nanoTime();

            primaryResultMicros = (timeAfterPosting - timeStart) / 1000;
            primaryResultCount = super.expectedEventCount;
            long deliveredMicros = (timeAllReceived - timeStart) / 1000;
            int deliveryRate = (int) (primaryResultCount / (deliveredMicros / 1000000d));
            otherTestResults = "Post and delivery time: " + deliveredMicros + " micros<br/>" + //
                    "Post and delivery rate: " + deliveryRate + "/s";

            unSubscribeAll();
        }

        @Override
        public String getDisplayName() {
            return "RxBusMulti Post Events, " + params.getThreadMode() + getDisplayModifier(params);
        }

    }

    public static class RegisterAll extends PerfTestRxBusMulti {
        public RegisterAll(Context context, TestParams params) {
            super(context, params);
        }

        public void runTest() {
            super.registerUnregisterOneSubscribers();
            long timeNanos = super.registerSubscribers();
            primaryResultMicros = timeNanos / 1000;
            primaryResultCount = params.getSubscriberCount();
        }

        @Override
        public String getDisplayName() {
            return "RxBusMulti Register, no unregister" + getDisplayModifier(params);
        }
    }

    public static class RegisterOneByOne extends PerfTestRxBusMulti {
        protected Method clearCachesMethod;

        public RegisterOneByOne(Context context, TestParams params) {
            super(context, params);
        }

        public void runTest() {
            long time = 0;
            if (clearCachesMethod == null) {
                // Skip first registration unless just the first registration is tested
                super.registerUnregisterOneSubscribers();
            }
            for (Object subscriber : super.subscribers) {
                if (clearCachesMethod != null) {
                    try {
                        clearCachesMethod.invoke(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                long beforeRegister = System.nanoTime();
                // super.eventBus.register(subscriber);
                long afterRegister = System.nanoTime();
                long end = System.nanoTime();
                long timeMeasureOverhead = (end - afterRegister) * 2;
                long timeRegister = end - beforeRegister - timeMeasureOverhead;
                time += timeRegister;
                // super.eventBus.unregister(subscriber);
                if (canceled) {
                    return;
                }
            }

            primaryResultMicros = time / 1000;
            primaryResultCount = params.getSubscriberCount();
        }

        @Override
        public String getDisplayName() {
            return "RxBusMulti Register" + getDisplayModifier(params);
        }
    }

    public static class RegisterFirstTime extends RegisterOneByOne {

        public RegisterFirstTime(Context context, TestParams params) {
            super(context, params);
            try {
                Class<?> clazz = Class.forName("org.greenrobot.eventbus.SubscriberMethodFinder");
                clearCachesMethod = clazz.getDeclaredMethod("clearCaches");
                clearCachesMethod.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getDisplayName() {
            return "RxBusMulti Register, first time" + getDisplayModifier(params);
        }

    }

    public class SubscribeClassRxBusMain {
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onEventMainThread(TestEvent event) {
            eventsReceivedCount.incrementAndGet();
        }

        public void dummy() {
        }

        public void dummy2() {
        }

        public void dummy3() {
        }

        public void dummy4() {
        }

        public void dummy5() {
        }
    }

    public class SubscribeClassRxBusBackground {
        @Subscribe(threadMode = ThreadMode.BACKGROUND)
        public void onEventBackgroundThread(TestEvent event) {
            eventsReceivedCount.incrementAndGet();
        }

        public void dummy() {
        }

        public void dummy2() {
        }

        public void dummy3() {
        }

        public void dummy4() {
        }

        public void dummy5() {
        }
    }

    public class SubscribeClassRxBusAsync {
        @Subscribe(threadMode = ThreadMode.ASYNC)
        public void onEventAsync(TestEvent event) {
            eventsReceivedCount.incrementAndGet();
        }

        public void dummy() {
        }

        public void dummy2() {
        }

        public void dummy3() {
        }

        public void dummy4() {
        }

        public void dummy5() {
        }
    }

    private long registerSubscribers() {
        long time = 0;
        for (Object subscriber : subscribers) {
            long timeStart = System.nanoTime();
            // eventBus.register(subscriber);

            // switch thread
            RxBus.ThreadMode mode;
            if (subscriber instanceof SubscribeClassRxBusBackground) {
                mode = RxBus.ThreadMode.BACKGROUND;
            } else if (subscriber instanceof SubscribeClassRxBusMain) {
                mode = RxBus.ThreadMode.MAIN;
            } else if (subscriber instanceof SubscribeClassRxBusAsync) {
                mode = RxBus.ThreadMode.ASYNC;
            } else {
                mode = RxBus.ThreadMode.POSTING;
            }

            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent0.class, mode).subscribe(new Action1<TestEvent0>() {
                @Override
                public void call(TestEvent0 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent1.class, mode).subscribe(new Action1<TestEvent1>() {
                @Override
                public void call(TestEvent1 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent2.class, mode).subscribe(new Action1<TestEvent2>() {
                @Override
                public void call(TestEvent2 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent3.class, mode).subscribe(new Action1<TestEvent3>() {
                @Override
                public void call(TestEvent3 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent4.class, mode).subscribe(new Action1<TestEvent4>() {
                @Override
                public void call(TestEvent4 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));

            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent5.class, mode).subscribe(new Action1<TestEvent5>() {
                @Override
                public void call(TestEvent5 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent6.class, mode).subscribe(new Action1<TestEvent6>() {
                @Override
                public void call(TestEvent6 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent7.class, mode).subscribe(new Action1<TestEvent7>() {
                @Override
                public void call(TestEvent7 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent8.class, mode).subscribe(new Action1<TestEvent8>() {
                @Override
                public void call(TestEvent8 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));
            eventSubscribers.add(RxBus.INSTANCE.toObserverable(TestEvent9.class, mode).subscribe(new Action1<TestEvent9>() {
                @Override
                public void call(TestEvent9 event) {
                    eventsReceivedCount.incrementAndGet();
                }
            }));


            long timeEnd = System.nanoTime();
            time += timeEnd - timeStart;
            if (canceled) {
                return 0;
            }
        }
        return time;
    }

    private void registerUnregisterOneSubscribers() {
        if (!subscribers.isEmpty()) {
            Object subscriber = subscribers.get(0);
            // eventBus.register(subscriber);
            // eventBus.unregister(subscriber);
        }
    }

}
