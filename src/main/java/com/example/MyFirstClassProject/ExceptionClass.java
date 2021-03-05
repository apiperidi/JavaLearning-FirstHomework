package com.example.MyFirstClassProject;

import java.util.ArrayList;
import java.util.List;

public class ExceptionClass {
    public static void main(String[] args)  {
        try {
            Class.forName("random");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
           method1();

         }
          catch (negative e)
          {
              System.out.printf("cdd");

          }
          //(NullPointerException e){System.out.println(e);}
    //    catch (Exception e){}


    }

    public static void method1() throws negative
    {
        int n=-5;
        if (n<0){
           // throw new negative("bla");
        }

    }

    public class negative extends Exception{



    }


    private static List<String> generateStringNames() {
        List<String> firstSet = new ArrayList<>() ;
        firstSet.add("Angela");
        firstSet.add("George");
        firstSet.add("Kostas");
        firstSet.add("Charoula");
        firstSet.add("Manolis");
        firstSet.add("Antonis");
        firstSet.add("Vasilis");
        firstSet.add("Anda");
        firstSet.add("Angela");
        return firstSet;
    }



}
