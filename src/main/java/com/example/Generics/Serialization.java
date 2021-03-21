package com.example.Generics;

import java.io.*;

public class Serialization {
    private static final String filediectory= System.getProperty("user.home")+ File.separator+"Desktop\\Work\\Εκπαίδευση Java\\";

    public static void main(String[] args) {




    }

private void writeObject (Person p, String path)
{
   try(OutputStream ous =new FileOutputStream(filediectory);
       ObjectOutputStream ob=new ObjectOutputStream(ous);)
    {ob.writeObject(p);
   }

   catch (IOException e) {
       e.printStackTrace();
   }

}

}
