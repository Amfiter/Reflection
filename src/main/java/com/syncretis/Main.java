package com.syncretis;

import com.syncretis.abstractions.FieldName;
import com.syncretis.entities.Car;
import com.syncretis.entities.House;
import com.syncretis.entities.Person;
import com.syncretis.parser.Parser;
import com.syncretis.utils.FileWrite;
import com.syncretis.utils.Style;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        FileWrite fileWriter = new FileWrite();
        Style style = new Style();

        Person person =new Person("Vova",9991773700l);
        House house = new House("Vershinina street",14);
        Car car = new Car("32424","Red","Lamborghini");

        List<Object> list = new ArrayList<>();
        list.add(person);
        list.add(house);
        list.add(car);

        Parser<Object> parser = new Parser<>();

        StringBuilder personString = parser.serialize(person);
        StringBuilder houseString = parser.serialize(house);
        StringBuilder carString = parser.serialize(car);

        System.out.println(personString);
        System.out.println(houseString);
        System.out.println(carString);

        parser.deserialize(personString,Person.class);
        parser.deserialize(houseString,House.class);
        parser.deserialize(carString,Car.class);


        for (int i = 0; i < list.size(); i++) {
            StringBuilder finishString = parser.serialize(list.get(i));
            style.commonBetweenObjects(list,i,finishString);
            fileWriter.writeToJson(finishString);
        }
    }
}
