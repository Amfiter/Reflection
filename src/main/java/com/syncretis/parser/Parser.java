package com.syncretis.parser;

import com.syncretis.abstractions.FieldName;
import com.syncretis.utils.Checks;
import com.syncretis.utils.Style;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parser<T> {
    private final Checks checks = new Checks();
    private final Style style = new Style();

    public StringBuilder serialize(T object) throws IllegalAccessException {
        StringBuilder finishString = new StringBuilder("{");
        Field[] fields = object.getClass().getDeclaredFields();
        int count = fields.length - 1;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getType().equals(String.class)) {
                StringBuilder tempString = new StringBuilder();
                tempString.append(style.styleJsonForString(fields[i], object))
                        .append("\"");
                style.common(i, count, tempString);
                finishString.append(tempString);
            } else if (checks.isNumericOrNot(fields[i])) {
                StringBuilder tempString = new StringBuilder();
                tempString.append(style.styleJsonForNumbers(fields[i], object));
                style.common(i, count, tempString);
                finishString.append(tempString);
            }
        }
        return finishString;
    }

    public <T> T deserialize(StringBuilder str, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Constructor<T> constructor = clazz.getConstructor();
        T obj = constructor.newInstance();
        HashMap<String, String> stringMap = splitText(str.toString());

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                if (compareFields(field, entry.getKey()) && field.getType().equals(String.class)) {
                    field.set(obj, entry.getValue());
                } else if (compareFields(field, entry.getKey()) && field.getType().equals(long.class)) {
                    field.set(obj, Long.valueOf(entry.getValue()));
                } else if (compareFields(field, entry.getKey()) && field.getType().equals(int.class)) {
                    field.set(obj, Integer.valueOf(entry.getValue()));
                } else if (compareFields(field, entry.getKey()) && field.getType().equals(double.class)) {
                    field.set(obj, Double.valueOf(entry.getValue()));
                } else if (compareFields(field, entry.getKey()) && field.getType().equals(boolean.class)) {
                    field.set(obj, Boolean.valueOf(entry.getValue()));
                }
            }
        }
        System.out.println("Object from JSON: " + obj);
        return obj;
    }

    private boolean compareFields(Field field, String str) {
        if (str.equals(field.getName())) {
            return true;
        } else if (Objects.isNull(field.getAnnotation(FieldName.class))) {
            return false;
        } else if (str.equals(field.getAnnotation(FieldName.class).value())) {
            return true;
        }
        return false;
    }

    private HashMap<String, String> splitText(String text) {
        HashMap<String, String> map = new HashMap<>();
        String[] fieldsText = text.split(",\n");
        for (String fieldText : fieldsText) {
            fieldText = fieldText.replaceAll("[{}\" \n\t]", "");
            String[] str = fieldText.split(":");
            map.put(str[0], str[1]);
        }
        return map;
    }
}
