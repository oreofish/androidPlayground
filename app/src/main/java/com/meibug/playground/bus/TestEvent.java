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

package com.meibug.playground.bus;

/** Used by otto and EventBus */
public class TestEvent {

    public static TestEvent createEvent(int index) {
        switch (index) {
            case 0:
                return new TestEvent0();
            case 1:
                return new TestEvent1();
            case 2:
                return new TestEvent2();
            case 3:
                return new TestEvent3();
            case 4:
                return new TestEvent4();
            case 5:
                return new TestEvent5();
            case 6:
                return new TestEvent6();
            case 7:
                return new TestEvent7();
            case 8:
                return new TestEvent8();
            default:
            case 9:
                return new TestEvent9();
        }
    }



}