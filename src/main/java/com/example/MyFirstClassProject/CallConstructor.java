package com.example.MyFirstClassProject;

public class CallConstructor {
    public static void main(String[] args) {
        MyConstructor myCon =new MyConstructor(0, "Angela");
      System.out.printf("Get Age: %s. Get Name: %s:",myCon.getAge(), myCon.name);
        //Age must be positive number
       // Get Age: 0. Get Name: null:

        MyConstructor myCon1 =new MyConstructor(30, "Angela");
        System.out.printf("Get Age: %s. Get Name: %s",myCon1.getAge(), myCon1.name);
        //Get Age: 30. Get Name: Angela
    }
}
