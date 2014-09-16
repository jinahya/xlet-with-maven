

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


    public void initXlet(final XletContext xc) throws XletStateChangeException {

        PropertyConfigurator.configure("/microlog.properties");

        final Properties properties = new Properties();
        {
            final InputStream resource = getClass().getResourceAsStream(
                NAME_ENVIRONMENT_PROPERTIES);
            if (resource == null) {
                throw new XletStateChangeException(
                    "resource not found: " + NAME_ENVIRONMENT_PROPERTIES);
            }
            try {
                try {
                    properties.load(resource);
                } finally {
                    resource.close();
                }
            } catch (final IOException ioe) {
                logger.fatal("failed to loading " + NAME_ENVIRONMENT_PROPERTIES,
                             ioe);
                throw new XletStateChangeException(ioe.getMessage());
            }
        }
        for (final Enumeration keys = properties.keys();
             keys.hasMoreElements();) {
            final String key = (String) keys.nextElement();
            final String value = properties.getProperty(key);
            logger.info("property: " + key + " / " + value);
        }
    }


    public void startXlet() throws XletStateChangeException {

        logger.info("startXlet()");
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

