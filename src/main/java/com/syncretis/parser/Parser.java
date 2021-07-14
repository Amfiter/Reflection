package com.syncretis.parser;

import com.syncretis.abstractions.FieldName;
import com.syncretis.entities.Person;

import java.lang.reflect.Field;

public class Parser<T> {

    public StringBuilder serialize(T object) throws IllegalAccessException {
        StringBuilder bracket = new StringBuilder("{");
        Field[] fields = object.getClass().getDeclaredFields();
        int count = fields.length - 1;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            isNumericOrNot(fields[i]);
            if (fields[i].getType().equals(String.class)) {
                StringBuilder tempString = new StringBuilder();
                tempString.append("\n\t\"")
                        .append(fields[i].getAnnotation(FieldName.class).value())
                        .append("\" : \"")
                        .append(fields[i].get(object))
                        .append("\"");
                common(i, count, tempString);
                bracket.append(tempString);
            } else if (isNumericOrNot(fields[i])) {
                StringBuilder tempString = new StringBuilder();
                tempString.append("\n\t\"")
                        .append(fields[i].getAnnotation(FieldName.class).value())
                        .append("\" : ")
                        .append(fields[i].get(object));
                common(i, count, tempString);
                bracket.append(tempString);
            }
        }
        System.out.println(bracket);
        return bracket;
    }

    public Object deserialize(String string, Class<?> clazz) {
        Person person = new Person();
        return person;
    }

    private int common(int index, int count, StringBuilder stringBuilder) {
        if (index != count) {
            stringBuilder.append(",");
        } else {
            stringBuilder.append("\n}");
        }
        return count;
    }

    private boolean isNumericOrNot(Field field) {
        if (field.getType().equals(long.class)
                || field.getType().equals(short.class)
                ||field.getType().equals(double.class)
                ||field.getType().equals(int.class)
                ||field.getType().equals(float.class))
        {
            return true;
        }
        return false;
    }

}
