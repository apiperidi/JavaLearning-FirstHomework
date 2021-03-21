package com.example.MyFirstClassProject;

public class MyConstructor {
    int age;
    String name;

    public MyConstructor (int age,String name)
    {
        if (age>0)
        {
            this.age=age;
            this.name=name;
        }
        else
            System.out.println("Age must be positive number");

    }
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
