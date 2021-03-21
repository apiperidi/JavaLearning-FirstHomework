package com.example.Generics;

import java.util.ArrayList;
import java.util.List;

public class GenericClass {
    public static void main(String[] args) {
  GenericClass gen=new GenericClass();
  gen.genericProblem();


    }
//upper
public  void  genericProblem()
{
//**************1**********************
    List<String> ListString =new ArrayList<>();
    ListString.add("hello");
    //ListString.add(5); //compiler err
    for (String item: ListString){ System.out.println(item); }

//************2**********************
    List<Number> ListNumber =new ArrayList<>();
    ListNumber.add(1);
    ListNumber.add(1.58);
    for (Number item: ListNumber){ System.out.println(item); }

    //************3**********************
    List<Number> List =new ArrayList<>();

}


}
