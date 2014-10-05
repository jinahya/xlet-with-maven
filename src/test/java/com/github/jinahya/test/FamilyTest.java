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


import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon
 */
public class FamilyTest {


    private static boolean next() {

        return ThreadLocalRandom.current().nextBoolean();
    }


    private static Family family0() {

        final Family family = new Family();
        family.setLastName("Doe");

        final Parent parent = new Parent();
        family.setParent(parent);

        final Father father = new Father();
        parent.setFather(father);
        father.setAge(40);
        father.setName("John");

        final Mother mother = new Mother();
        parent.setMother(mother);
        mother.setAge(38);
        mother.setMaidenName("Roe");
        mother.setName("Jane");

        final Child daughter = new Child();
        family.getChildren().add(daughter);
        daughter.setGender(Gender.FEMALE);
        daughter.setAge(17);
        daughter.setName("Janie");

        final Child sun = new Child();
        family.getChildren().add(sun);
        sun.setGender(Gender.MALE);
        sun.setAge(15);
        sun.setName("Jonnie");

        return family;
    }


    protected static Family family() {

        final Family family = new Family();
        if (next()) {
            family.setLastName("Doe");
        }

        final Parent parent = new Parent();
        family.setParent(parent);

        if (next()) {
            final Father father = new Father();
            parent.setFather(father);
            if (next()) {
                father.setAge(40);
            }
            if (next()) {
                father.setName("John");
            }
        }

        if (next()) {
            final Mother mother = new Mother();
            parent.setMother(mother);
            if (next()) {
                mother.setAge(38);
            }
            if (next()) {
                mother.setMaidenName("Roe");
            }
            if (next()) {
                mother.setName("Jane");
            }
        }

        family.getChildren();

        if (next()) {
            final Child child = new Child();
            family.getChildren().add(child);
            if (next()) {
                child.setGender(Gender.FEMALE);
            }
            if (next()) {
                child.setAge(17);
            }
            if (next()) {
                child.setName("Janie");
            }
        }

        if (next()) {
            final Child child = new Child();
            family.getChildren().add(child);
            if (next()) {
                child.setGender(Gender.MALE);
            }
            if (next()) {
                child.setAge(15);
            }
            if (next()) {
                child.setName("Jonnie");
            }
        }

        return family;
    }


    static InputStream familyXmlStream() throws JAXBException {

        final JAXBContext context = JAXBContext.newInstance(Family.class);
        final Marshaller marshaller = context.createMarshaller();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(family(), baos);
        System.out.println(new String(baos.toByteArray(), UTF_8));

        return new ByteArrayInputStream(baos.toByteArray());
    }


    static Reader familyXmlReader() throws JAXBException {

        return new InputStreamReader(familyXmlStream(), UTF_8);
    }


    static InputStream familyJsonStream() throws IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final AnnotationIntrospector introspector
            = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, family());
        System.out.println(new String(baos.toByteArray(), UTF_8));

        return new ByteArrayInputStream(baos.toByteArray());
    }


    static Reader familyJsonReader() throws IOException {

        return new InputStreamReader(familyJsonStream(), UTF_8);
    }


    @Test
    public void generateXsd() throws JAXBException, IOException {

        final JAXBContext context
            = JAXBContext.newInstance("com.github.jinahya.test");

        context.generateSchema(new SchemaOutputResolver() {


            @Override
            public Result createOutput(final String namespaceUri,
                                       final String suggestedFileName)
                throws IOException {
                return new StreamResult(System.out) {

                    @Override
                    public String getSystemId() {
                        return "";
                    }


                };
            }


        });
    }


    @Test
    public void marshalXml() throws JAXBException, IOException {

        final JAXBContext context
            = JAXBContext.newInstance("com.github.jinahya.test");

        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(family0(), System.out);
    }


    @Test
    public void unmarshalXml() throws JAXBException, IOException {

        final JAXBContext context
            = JAXBContext.newInstance("com.github.jinahya.test");

        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final InputStream source
            = getClass().getResourceAsStream("/family.xml");
        assertNotNull(source);
        try {
            final Family family = (Family) unmarshaller.unmarshal(source);
        } finally {
            source.close();
        }
    }


    @Test
    public void marshalJson() throws JAXBException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        final AnnotationIntrospector introspector
            = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(System.out, family0());
    }


    @Test
    public void unmarshalJson() throws JAXBException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        final AnnotationIntrospector introspector
            = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);

        try (final InputStream source
            = getClass().getResourceAsStream("/family.json")) {
            assertNotNull(source);
            final Family family = mapper.readValue(source, Family.class);
        }
    }


}

