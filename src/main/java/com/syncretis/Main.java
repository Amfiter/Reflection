package com.syncretis;

import com.syncretis.abstractions.FieldName;
import com.syncretis.entities.House;
import com.syncretis.entities.Person;
import com.syncretis.parser.Parser;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Person person = new Person("Vova",9991773700l);
        House house = new House("Red",14);

        Parser<Person> personParser = new Parser<>();
        personParser.serialize(person);

        Parser<House> houseParser = new Parser<>();
        houseParser.serialize(house);
    }
}
