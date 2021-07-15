package com.syncretis.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class Style {

    public StringBuilder styleJsonForString(Field field, Object object) throws IllegalAccessException {
        Checks proofs = new Checks();
        StringBuilder tempString = new StringBuilder();
        tempString.append("\n\t\"")
                .append(proofs.hasFieldNameOrNot(field))
                .append("\" : \"")
                .append(field.get(object));
        return tempString;
    }

    public StringBuilder styleJsonForNumbers(Field field, Object object) throws IllegalAccessException {
        Checks proofs = new Checks();
        StringBuilder tempString = new StringBuilder();
        tempString.append("\n\t\"")
                .append(proofs.hasFieldNameOrNot(field))
                .append("\" : ")
                .append(field.get(object));
        return tempString;
    }

    public void common(int index, int count, StringBuilder stringBuilder) {
        if (index != count) {
            stringBuilder.append(",");
        } else {
            stringBuilder.append("\n}");
        }
    }
    public void commonBetweenObjects(List<Object> list,int index,StringBuilder stringBuilder) {
        if (index+1 != list.size()) {
            stringBuilder.append(",\n");
        }
    }
}
