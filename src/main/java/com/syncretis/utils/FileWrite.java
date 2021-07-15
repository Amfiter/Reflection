package com.syncretis.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {

    public void writeToJson(StringBuilder string) {
        try (FileWriter writer = new FileWriter("JSON.txt", true)) {
            writer.write(string.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
