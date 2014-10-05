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


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Jin Kwon
 */
@XmlRootElement
@XmlType(propOrder = {"parent", "children"})
public class Family {


    public String getLastName() {

        return lastName;
    }


    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }


    public Parent getParent() {

        return parent;
    }


    public void setParent(final Parent parent) {

        this.parent = parent;
    }


    public List<Child> getChildren() {

        if (children == null) {
            children = new ArrayList<>();
        }

        return children;
    }


    @XmlAttribute(required = true)
    private String lastName;


    @XmlElement(nillable = true, required = true)
    private Parent parent;


    @XmlElementWrapper(name = "children", nillable = true, required = true)
    @XmlElement(name = "child", nillable = true)
    private List<Child> children;


}

