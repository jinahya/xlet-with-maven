package com.github.jinahya.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Properties;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import net.sf.microlog.core.PropertyConfigurator;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class HelloWorldXlet implements Xlet {


    public HelloWorldXlet() {

        super();

        PropertyConfigurator.configure("/microlog.properties");
        logger = LoggerFactory.getLogger(HelloWorldXlet.class);
    }


    public void initXlet(final XletContext context)
        throws XletStateChangeException {

        logger.debug("initXlet(" + context + ")");

        final InputStream resource = getClass().getResourceAsStream(
            "/application.properties");
        final Properties properties = new Properties();
        try {
            try {
                properties.load(resource);
            } finally {
                resource.close();
            }
        } catch (final IOException ioe) {
            throw new XletStateChangeException(ioe.getMessage());
        }
        for (final Enumeration keys = properties.keys();
             keys.hasMoreElements();) {
            final String key = (String) keys.nextElement();
            final String value = properties.getProperty(key);
            logger.debug("property: " + key + "=" + value);
        }
    }


    public void startXlet() throws XletStateChangeException {

        logger.debug("startXlet()");

        final XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
        } catch (final XmlPullParserException xppe) {
            logger.error("failed to create XmlPullParserFactory", xppe);
            throw new XletStateChangeException(xppe.getMessage());
        }

        // parse xml kdom
        try {
            final Reader input = new InputStreamReader(
                getClass().getResourceAsStream("/family.xml"), "UTF-8");
            try {
                final XmlPullParser parser = factory.newPullParser();
                parser.setInput(input);
                final Document document = new Document();
                document.parse(parser);
                final Element family = document.getRootElement();
                logger.info("family/@lastName: "
                            + family.getAttributeValue(null, "lastName"));
            } finally {
                input.close();
            }
        } catch (final XmlPullParserException xppe) {
            logger.error("failed to parse xml", xppe);
            throw new XletStateChangeException(xppe.getMessage());
        } catch (final IOException ioe) {
            logger.error("failed to parse xml", ioe);
            throw new XletStateChangeException(ioe.getMessage());
        }

        // parse xml pull
        try {
            final Reader input = new InputStreamReader(
                getClass().getResourceAsStream("/family.xml"), "UTF-8");
            try {
                final XmlPullParser parser = factory.newPullParser();
                parser.setInput(input);
                parser.nextTag(); // start element
                parser.require(XmlPullParser.START_TAG,
                               "http://github.com/jinahya/test", "family");
                logger.info("family/@lastName: "
                            + parser.getAttributeValue(null, "lastName"));
            } finally {
                input.close();
            }
        } catch (final XmlPullParserException xppe) {
            logger.error("failed to parse xml", xppe);
            throw new XletStateChangeException(xppe.getMessage());
        } catch (final IOException ioe) {
            logger.error("failed to parse xml", ioe);
            throw new XletStateChangeException(ioe.getMessage());
        }

        try {
            final JSONTokener tokener = new JSONTokener(new InputStreamReader(
                getClass().getResourceAsStream("/family.json"), "UTF-8"));
            final JSONObject family = new JSONObject(tokener);
            logger.info("family.lastName: " + family.get("lastName"));
        } catch (final IOException ioe) {
            logger.error("failed to parse", ioe);
            throw new XletStateChangeException(ioe.getMessage());
        }

        try {
            JSONParser parser = new JSONParser();
            final org.json.simple.JSONObject family
                = (org.json.simple.JSONObject) parser.parse(
                    new InputStreamReader(
                        getClass().getResourceAsStream("/family.json"),
                        "UTF-8"));
            logger.info("family.lastName: " + family.get("lastName"));
        } catch (final IOException ioe) {
            logger.error("failed to parse", ioe);
            throw new XletStateChangeException(ioe.getMessage());
        } catch (final ParseException pe) {
            logger.error("failed to parse", pe);
            throw new XletStateChangeException(pe.getMessage());

        }
    }


    public void pauseXlet() {

        logger.debug("pauseXlet()");
    }


    public void destroyXlet(boolean unconditional)
        throws XletStateChangeException {

        logger.debug("destroyXlet(" + unconditional + ")");

        LoggerFactory.shutdown();
    }


    private final Logger logger;


}

