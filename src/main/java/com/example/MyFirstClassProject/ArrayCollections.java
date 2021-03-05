package com.example.MyFirstClassProject;

import java.util.*;

public class ArrayCollections {
    public static void main(String[] args) {
        int[] integerArray= {1,2,3,4,5};
        int integerArraySec[]= {10,20,30,40,50};
        System.out.println("---------1----------");
      for (int i=0; i<integerArraySec.length; i++)
        {
            System.out.println(i +1+"."+ integerArraySec[i]);
        }
        System.out.println("--------2-----------");
        for (int i:integerArraySec)
        {
            System.out.println(i);
        }

        int[][] twoDimensionArray ={{1,2,3},{100,200,300}};

       /* for (int i=0; i<twoDimensionArray.length; i++)
        {
            for (int j=0; i<twoDimensionArray[i].length; j++)
            {
           //System.out.println(twoDimensionArray[i][j]);
            }
        }*/

       //Κλώνος
        int[] integerArrayClone=integerArraySec.clone(); //not equal

        int[] integerArrayNew=integerArraySec; //equal
        System.out.println("--------3----------");
        System.out.println("ccc "+(integerArrayNew ==integerArraySec ? "equal" :"not equal"));
        System.out.println("ccc "+(integerArrayClone ==integerArraySec ? "equal" :"not equal"));

        System.out.println("--------4----------");


        String[] cities=generate();
        for (int i=0; i<cities.length; i++)
        {
            System.out.println(i +1+"."+ cities[i]);
        }
        System.out.println("--------5----------");
        for (String i:cities)
        {
            System.out.printf("%s",i);
        }
        System.out.println();
        System.out.println("--------6----------");
       // Arrays.sort(cities);
        printCities(cities);

        System.out.println();
        System.out.println("--------7----------");

        var myCities =Arrays.copyOf(cities,cities.length);
        Arrays.sort(myCities);
        printCities(myCities);

        System.out.println();
        System.out.println("--------8----------");
        Arrays.sort(myCities, new Comparator<String>() { //Anonymous class
            @Override
            public int compare(String o1, String o2) {
                int results=o2.compareTo(o1);
               System.out.printf("Array comparison %s with %s resulted into %d",o1, o2,results);
                System.out.println();
                return o2.compareTo(o1);
            }
        });
        printCities(myCities);

        System.out.println();
       //26/02/2021
        demoHasSet();

        List<String> listTest= new ArrayList<>();
        listTest.add("ssss");
        listTest.add("gggg");
        listTest.add("jjjj");
        listTest.add("Ang");
        //listTest.add(null);
        listTest.sort(null);//sort alfariumhtika
       listTest.sort(String::compareToIgnoreCase);//sort
        listTest.removeIf(s->s.toLowerCase().contains("a"));
       Collections.sort(listTest);


    } //end main

    private static void demoHasSet() {
        Set<String> firstSet = getStrings();
        for (String item:firstSet)
        {
            System.out.println(item);
        }
        System.out.println();
        System.out.printf("Sting %s" ,firstSet.contains("Angela")? "exist" : "not exist");
        System.out.println();

        // Set<String> unModifierSet=Set.copyOf(getStrings());
        Set<String> unModifierSet=Set.of("Piper","papas","fff"); //το κάνω δηλαδή readonly
        // or
        Set unModifierSet2=Set.<String>of("Piper","papas","fff");

        // unModifierSet.add("ante gia"); θα βγάλει error δεν μπορώ να προσθέσω στην λίστα
        for (String item:unModifierSet)
        {
            System.out.println(item);
        }

    }

    private static Set<String> getStrings() {
        Set<String> firstSet = new HashSet<>();
        firstSet.add("First Item");
        firstSet.add("Second Item");
        firstSet.add("Third Item");
        firstSet.add("Fourth Item");
        firstSet.addAll(generateStringNames());
        return firstSet;
    }
    private static Set<String> generateStringNames() {
        Set<String> firstSet = new HashSet<>();
        firstSet.add("Angela");
        firstSet.add("George");
        firstSet.add("Kostas");
        firstSet.add("Xara");
        firstSet.add("Manos");
        return firstSet;
    }
    private static void printCities(String[] cities) {
        for (int i = 0; i< cities.length; i++)
        {
            System.out.printf("%d.%s ",i+1, cities[i]);
        }
    }


    static String[] generate()
    {
        String[] cities={"Athens","Volos","Patra","Thessaloniki"};
        return cities;
    }



}
