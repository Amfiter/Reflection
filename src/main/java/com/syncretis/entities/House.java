package com.syncretis.entities;

import com.syncretis.abstractions.FieldName;

import java.util.StringJoiner;

public class House {
    @FieldName("house number")
    int id;
    String location;

    public House() {
    }

    public House(String location,int id) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return location;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", House.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("location='" + location + "'")
                .toString();
    }
}
