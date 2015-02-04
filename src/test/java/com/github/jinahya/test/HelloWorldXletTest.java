/*
 * Copyright 2014 Jin Kwon.
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
package com.github.jinahya.test;


import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletStateChangeException;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon
 */
public class HelloWorldXletTest {


    @Test
    public void loadDestroy() throws XletStateChangeException {

        Xlet xlet = new MyXlet();

        xlet.destroyXlet(true);

        xlet = null;
    }


    @Test
    public void initDestroy() throws XletStateChangeException {

        Xlet xlet = new MyXlet();

        xlet.initXlet(null);
        xlet.destroyXlet(true);

        xlet = null;
    }


    @Test
    public void startDestroy() throws XletStateChangeException {

        Xlet xlet = new MyXlet();

        xlet.initXlet(null);
        xlet.startXlet();
        xlet.destroyXlet(true);

        xlet = null;
    }


    @Test
    public void pauseDestroy() throws XletStateChangeException {

        Xlet xlet = new MyXlet();

        xlet.initXlet(null);
        xlet.startXlet();
        xlet.pauseXlet();
        xlet.destroyXlet(true);

        xlet = null;
    }


}

