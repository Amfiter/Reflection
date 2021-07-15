package com.syncretis.entities;

import com.syncretis.abstractions.FieldName;

import java.util.StringJoiner;

public class Car {
    String id;
    String color;
    String carBrand;

    public Car(String id, String color,String carBrand) {
        this.id = id;
        this.color = color;
        this.carBrand = carBrand;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("color='" + color + "'")
                .toString();
    }
}
