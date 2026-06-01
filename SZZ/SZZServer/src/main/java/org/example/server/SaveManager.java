package org.example.server;

import java.io.*;

public class SaveManager {

    public static void save(Object obj, String filename) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(filename))) {

            out.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object load(String filename) {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(filename))) {

            return in.readObject();

        } catch (Exception e) {
            return null;
        }
    }
}