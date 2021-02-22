package com.example.MyFirstClassProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Object Person
public class Person { /*To object Person Heap Space*/
    int age; // stack memory02
     String firstName; /*To attribute Heap Space*/
    String lastName;

    public static void main(String[] args)
    {
        int a=10;
        int b=20;
      //  System.out.println(a++);//10
       // System.out.println(a);//11
     //  System.out.println(++b);//21

        int c=++a+b++;//(11+1=12 + 21= 33
      //  System.out.println(c);
      //  System.out.println(b);//22

        int [] array =new int[2];
        array[0]=8;
        array[1]=9;
        for (int number :array)
        {System.out.println(number);}

        int myInt =10;
        myInt+=5;
      //  System.out.println(myInt); //15
      /*  myInt %=2;

        System.out.println(myInt);
        myInt*=10;
        System.out.println(myInt);*/

        int result=(myInt==10)? myInt: 100;
      //  System.out.println("results:"+result);//100

        List<String> myNewList= new ArrayList<>();
        List<String> myNewListBackup= new LinkedList<>();
        manipopulateList (myNewList);
        manipopulateList (myNewListBackup);
        String textList = myNewList.get(0);
        System.out.println(textList); // Attiki


       // Dog myDog =new Dog();
       // myDog.bark();
       // myDog.run();

    }

    private static void manipopulateList(List<String> targetList)
    {targetList.add("Attiki");}


}



class Accountx{
    private static boolean flag;
    private String username;
    private String email;

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        Accountx.flag = flag;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

/*
abstract  class  Animal{

    abstract  void bark();
    void run(){
        System.out.println("aaaaa");

    }
}
class Dog extends Animal{
    @Override
    void bark() {
        System.out.println("ddddd");
    }
    @Override
    void run() {
        System.out.println("bbbb");
    }
}*/