

package com.github.jinahya.test;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import net.sf.microlog.core.PropertyConfigurator;


public class HelloWorldXlet implements Xlet {


    private static final Logger logger
        = LoggerFactory.getLogger(HelloWorldXlet.class);


    private static final String NAME_ENVIRONMENT_PROPERTIES
        = "/environment.properties";


    public static void main(final String[] args) throws IOException {

        final Properties properties = new Properties();

        final InputStream resource = HelloWorldXlet.class.getResourceAsStream(
            NAME_ENVIRONMENT_PROPERTIES);
        if (resource == null) {
            throw new RuntimeException(
                "resource not found: " + NAME_ENVIRONMENT_PROPERTIES);
        }
        try {
            properties.load(resource);
        } finally {
            resource.close();
        }
        for (final Enumeration e = properties.keys(); e.hasMoreElements();) {
            final String key = (String) e.nextElement();
            final String value = properties.getProperty(key);
            logger.info("property: " + key + " / " + value);
        }
    }


    public void initXlet(final XletContext xc) throws XletStateChangeException {

        PropertyConfigurator.configure("/microlog.properties");

        logger.info("initXlet(" + xc + ")");
    }


    public void startXlet() throws XletStateChangeException {

        logger.info("startXlet()");

        // JSON
        // XML
    }


    public void pauseXlet() {

        logger.info("pauseXlet()");
    }


    public void destroyXlet(boolean unconditional)
        throws XletStateChangeException {

        logger.info("destroyXlet(" + unconditional + ")");

        LoggerFactory.shutdown();
    }


}

