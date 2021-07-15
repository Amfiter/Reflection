package com.syncretis.utils;

import com.syncretis.abstractions.FieldName;

import java.lang.reflect.Field;
import java.util.Objects;

public class Checks {

    public boolean isNumericOrNot(Field field) {
        if (field.getType().equals(long.class)
                || field.getType().equals(short.class)
                || field.getType().equals(double.class)
                || field.getType().equals(int.class)
                || field.getType().equals(float.class)) {
            return true;
        }
        return false;
    }

    public String hasFieldNameOrNot(Field field) {
        FieldName fieldName = field.getDeclaredAnnotation(FieldName.class);
        if (!Objects.isNull(fieldName)) {
            return fieldName.value();
        } else {
            return field.getName();
        }
    }
}
