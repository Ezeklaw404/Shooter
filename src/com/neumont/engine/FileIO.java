package com.neumont.engine;

import java.io.*;
import java.lang.reflect.Field;

public class FileIO {
    public static File getFile(String pathName, boolean create){
        File file = null;
        try {
            file = new File(pathName);
            if (create) file.createNewFile();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return file;
    }


    public static boolean writeToFile(String pathName, String string){
        File file = getFile(pathName, false);

        try (var fileWriter = new BufferedWriter(new FileWriter(file))) {
//            int[] a = new int[3];
//            int i = a[3];
//            int i = 4 / 0;
//            int i = 0;
//            if (i == 0) {throw new Exception("Stupid move");}


            fileWriter.write(string);
            fileWriter.close();
            return true;
        } catch (ArithmeticException w) {
            System.out.println("divide by 0 error");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("invalid index");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            System.out.println("finally");
        }

        return false;
    }

    public static String readFromFile(String pathName){
        File file = getFile(pathName, false);


        try {
            var fileReader = new BufferedReader(new FileReader(file));
            char[] c = new char[(int)file.length()];
            fileReader.read(c);
            fileReader.close();

            return new String(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }

        return null;
    }

}
