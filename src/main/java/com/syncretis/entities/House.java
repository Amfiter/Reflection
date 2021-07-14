package com.syncretis.entities;

import com.syncretis.abstractions.FieldName;

import java.util.StringJoiner;

public class House {
    @FieldName("House number")
    int id;
    @FieldName("House dfdfd")
    String color;

    public House() {
    }

    public House(String color,int id) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", House.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("color='" + color + "'")
                .toString();
    }
}
