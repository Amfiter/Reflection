package com.syncretis.parser;

import com.syncretis.abstractions.FieldName;
import com.syncretis.utils.Checks;
import com.syncretis.utils.Style;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
                /*String.valueOf(fields[i]);
                System.out.println(fields[i]);*/
                StringBuilder tempString = new StringBuilder();
                tempString.append(style.styleJsonForNumbers(fields[i], object));
                style.common(i, count, tempString);
                finishString.append(tempString);
            }
        }
        return finishString;
    }

    public <T> T deserialize(Map<String, String> stringFields, Class<T> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<T> constructor = type.getConstructor();
        T instance = constructor.newInstance();

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldType = field.getType();
            FieldName annotation = field.getDeclaredAnnotation(FieldName.class);

            if (!Objects.isNull(annotation)) {
                if(stringFields.containsKey(annotation.value())) {
                    setStringFieldToInstance(field, stringFields.get(annotation.value()),instance);
                } else {
                    field.set(instance, stringFields.get(field.getName()));
                }
            } else {
                field.set(instance, stringFields.get(field.getName()));
            }
        }
        return instance;
    }

    public void setStringFieldToInstance(Field field, String fieldValue, Object instance) throws IllegalAccessException {
        if(!(field.getType() == String.class)){
            Object objValue = parseTypeFromString(field.getType(), fieldValue);
            field.set(instance, objValue);
        } else{
            field.set(instance, fieldValue);
        }
    }

    public Object parseTypeFromString(Class clazz, String value){
        if(Boolean.class == clazz ) {
            return Boolean.parseBoolean(value);
        }
        if(Integer.class == clazz || Integer.TYPE == clazz) {
            return Integer.parseInt(value);
        }
        if(Long.class == clazz) {
            return Long.parseLong(value);
        }
        if(Double.class == clazz) {
            return Double.parseDouble(value);
        }
        return value;
    }

}
