package com.company.myapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


interface DataConnection {
    int loadDatas(int sum) throws Exception;
    void saveData(int year, int qq) throws IOException;
}

public class MyApp implements DataConnection {


    public static class MyAppFactory {
        public static MyApp create(String y) {
            return new MyApp(y);
        }
    }

    MyApp(String y) {
        this.y = y;
    }

    private String y;
    private static int COUNT = 0;
    private static int COUNT1 = 0;
    protected static int startYear = 1990;
    protected static int endYear = 2020;

    /**
     * @param args
     */

    public static void main(String[] args) {

        String y;
        int sum;
        double qq;
        MyApp app;

        try {
            System.out.println("app v.1.13");
            for (int i = startYear; i < endYear; i++) {

                COUNT = 0;
                sum = 0;
                y = String.valueOf(i);
                app = MyApp.MyAppFactory.create(y);

                sum = app.loadDatas(sum);
                qq = sum > 0 ? (double) sum / (double) COUNT : 0;
                if (qq > 0) {
                    System.out.println(i + " " + qq);
                }
                app.saveData(i, (int) qq);
            }
            System.out.println("gotovo");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("oshibka!");
        }
    }


    // Чтение из файла
    @Override
    public int loadDatas(int sum) throws ArrayIndexOutOfBoundsException, NumberFormatException {

        int i;
        String s = new String();

        try (FileInputStream fis = new FileInputStream("/home/ariarti/atom/MyApp/src/com/company/myapp/1.txt")) {
            while ((i = fis.read()) != -1) {
                s = s + new String((new byte[]{(byte) i}));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return strAnalysis(s, sum);
    }


    // Анализ текста
    private int strAnalysis(String s, int sum) throws ArrayIndexOutOfBoundsException, NumberFormatException {

        String[] ss = s.split("\n");
        String[] sss;

        for (String num : ss) {
            sss = num.split("\\s");
            if (sss[2].contains(this.y)) {
                sum = sum + Integer.parseInt(sss[3]);
            }
            COUNT++;
        }
        return sum;
    }


    // Запись в файл
    @Override
    public void saveData(int year, int qq) {

        String s;

        try {
            FileOutputStream fis = new FileOutputStream(
                    new File("/home/ariarti/atom/MyApp/src/com/company/myapp/statistika.txt"), true);
            s = COUNT1 + " " + year + " " + qq + "\n";
            fis.write(s.getBytes());
            fis.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
