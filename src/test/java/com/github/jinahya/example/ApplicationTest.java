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


package com.github.jinahya.example;


import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import static org.mockito.Mockito.mock;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class ApplicationTest {


    @Test
    public void loadDestroy() throws XletStateChangeException {

        final XletContext context = mock(XletContext.class);

        final Xlet xlet = new Application();
        // LOADED

        xlet.destroyXlet(true);
        // DESTROYED
    }


    @Test
    public void initDestroy() throws XletStateChangeException {

        final XletContext context = mock(XletContext.class);

        final Xlet xlet = new Application();
        // LOADED

        xlet.initXlet(context);
        // INITIALIZED

        xlet.destroyXlet(true);
        // DESTROYED
    }


    @Test
    public void startDestroy() throws XletStateChangeException {

        final XletContext context = mock(XletContext.class);

        final Xlet xlet = new Application();
        // LOADED

        xlet.initXlet(null);
        // PAUSED

        xlet.startXlet();
        // ACTIVE

        xlet.destroyXlet(true);
        // DESTROYED
    }


    @Test
    public void pauseDestroy() throws XletStateChangeException {

        final XletContext context = mock(XletContext.class);

        final Xlet xlet = new Application();

        xlet.initXlet(context);
        xlet.startXlet();
        xlet.pauseXlet();
        xlet.destroyXlet(true);
    }


}

