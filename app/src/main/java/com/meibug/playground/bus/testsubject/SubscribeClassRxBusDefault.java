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

import com.meibug.playground.bus.TestEvent;

import org.greenrobot.eventbus.Subscribe;

public class SubscribeClassRxBusDefault {
    private PerfTestRxBus perfTestRxBus;

    public SubscribeClassRxBusDefault(PerfTestRxBus perfTestEventBus) {
        this.perfTestRxBus = perfTestEventBus;
    }

    @Subscribe
    public void onEvent(TestEvent event) {
        perfTestRxBus.eventsReceivedCount.incrementAndGet();
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
