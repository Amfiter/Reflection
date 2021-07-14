package com.syncretis.entities;

import com.syncretis.abstractions.FieldName;

import java.util.StringJoiner;

public class Person {
    @FieldName("nickname")
    private String name;
    @FieldName("phone number")
    private long phoneNumber ;


    public Person() {

    }

    public Person(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;

    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("phoneNumber=" + phoneNumber)
                .toString();
    }
}
