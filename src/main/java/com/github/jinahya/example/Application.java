

package com.github.jinahya.example;


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


public class Application implements Xlet {


    static {

        PropertyConfigurator.configure("/microlog.properties");
    }


    private static void printProperties(final String name) throws IOException {

        final Logger logger = LoggerFactory.getLogger(Application.class);

        logger.info("printing properties: " + name);

        final InputStream stream = Application.class.getResourceAsStream(name);
        if (stream != null) {
            try {
                final Properties properties = new Properties();
                properties.load(stream);
                for (final Enumeration e = properties.keys();
                     e.hasMoreElements();) {
                    final Object key = e.nextElement();
                    final Object val = properties.get(key);
                    logger.info(key + "=" + val);
                }
            } finally {
                stream.close();
            }
        }
    }


    public static void main(final String[] args) throws IOException {

        final Logger logger = LoggerFactory.getLogger(Application.class);

        printProperties("/application.properties");
        printProperties("/microlog.properties");
    }


    public Application() {

        super();

        // empty
    }


    public void initXlet(final XletContext context)
        throws XletStateChangeException {

        logger.debug("initXlet(" + context + ")");

        try {
            printProperties("/application.properties");
        } catch (final IOException ioe) {
            logger.error("failed to print /application.properties", ioe);
        }
        try {
            printProperties("/microlog.properties");
        } catch (final IOException ioe) {
            logger.error("failed to print /microlog.properties", ioe);
        }
    }


    public void startXlet() throws XletStateChangeException {

        logger.debug("startXlet()");

        final XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
        } catch (final XmlPullParserException xppe) {
            xppe.printStackTrace(System.err);
            throw new XletStateChangeException(xppe.getMessage());
        }

        // parse xml kdom
        try {
            final Reader input = new InputStreamReader(
                getClass().getResourceAsStream("/family.xml"), "UTF-8");
            try {
                try {
                    final XmlPullParser parser = factory.newPullParser();
                    parser.setInput(input);
                    final Document document = new Document();
                    document.parse(parser);
                    final Element family = document.getRootElement();
                    logger.info("family/@lastName: "
                                + family.getAttributeValue(null, "lastName"));
                } catch (final XmlPullParserException xppe) {
                    xppe.printStackTrace(System.err);
                }
            } finally {
                input.close();
            }
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        // parse xml pull
        try {
            final Reader input = new InputStreamReader(
                getClass().getResourceAsStream("/family.xml"), "UTF-8");
            try {
                try {
                    final XmlPullParser parser = factory.newPullParser();
                    parser.setInput(input);
                    parser.nextTag(); // start element
                    parser.require(XmlPullParser.START_TAG,
                                   "http://github.com/jinahya/test", "family");
                    logger.info("family/@lastName: "
                                + parser.getAttributeValue(null, "lastName"));
                } catch (final XmlPullParserException xppe) {
                    xppe.printStackTrace(System.err);
                }
            } finally {
                input.close();
            }
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        try {
            final Reader reader = new InputStreamReader(
                getClass().getResourceAsStream("/family.json"), "UTF-8");
            try {
                final JSONTokener tokener = new JSONTokener(reader);
                final JSONObject family = new JSONObject(tokener);
                logger.info("family.lastName: " + family.get("lastName"));
            } finally {
                reader.close();
            }
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
        }

        try {
            final Reader reader = new InputStreamReader(
                getClass().getResourceAsStream("/family.json"), "UTF-8");
            try {
                final JSONParser parser = new JSONParser();
                try {
                    final org.json.simple.JSONObject family
                        = (org.json.simple.JSONObject) parser.parse(reader);
                    logger.info("family.lastName: " + family.get("lastName"));
                } catch (final ParseException pe) {
                    pe.printStackTrace(System.err);
                }
            } finally {
                reader.close();
            }
        } catch (final IOException ioe) {
            ioe.printStackTrace(System.err);
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


    private transient final Logger logger = LoggerFactory.getLogger(getClass());


}

