package com.example.MyFirstClassProject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class inputoutputIO {

  private static final String filediectory= System.getProperty("user.home")+ File.separator+"datafiles";

    public static void main(String[] args) {
        writeBytesToFile();
        readBytesFromFile();
        writeBytesToFileWord();
        readBytesFromFile();
        readBytesFromFileWord();
        System.out.println(filediectory);
    }


    public static void writeBytesToFile()
    {
        try (FileOutputStream out =new FileOutputStream("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File1.txt"))
        {
            out.write(100); //ASCII
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void readBytesFromFile()
    {
        try (FileInputStream in =new FileInputStream("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File1.txt"))
        {
            //int value=in.read();
            byte[] bytes =in.readAllBytes();
            for (byte abytes:bytes) {
                System.out.println(abytes);
            }
        }

        catch (IOException e) {
           // e.printStackTrace();
            System.out.println("Does not exist");
        }


    }

    public static void writeBytesToFileWord()
    {
            try (FileOutputStream out =new FileOutputStream("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File1.txt"))
            {String word="Hello";
                out.write(word.getBytes(StandardCharsets.UTF_8)); //ASCII
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void readBytesFromFileWord()
    {
        try (FileInputStream in =new FileInputStream("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File1.txt"))
        {
            byte[] bytes =in.readAllBytes();
            for (byte abytes:bytes) {
                System.out.print((char)abytes);
            }
        }

        catch (IOException e) {
            // e.printStackTrace();
            System.out.println("Does not exist");
        }


    }

    public static void writeBytesToFileBuffer()
    {
        try (FileOutputStream out =new FileOutputStream("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File1.txt"))
        {
            BufferedOutputStream bos=new BufferedOutputStream(out);
            String word="Hello Buffer";
            bos.write(word.getBytes(StandardCharsets.UTF_8)); //ASCII
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void writeCharToFileWord()
    {
        try (Writer out =new FileWriter("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File2.txt") ;
        BufferedWriter bw=new BufferedWriter(out))
        {
        String word="Hello";
            bw.write(word);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ReadCharToFileWord()
    {
        try (Reader in =new FileReader("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File2.txt") )
        {
            int character;
          while((character=in.read()) !=-1)
          {
              System.out.print((char)character);
          }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertStackTrace()
    {
        StringWriter sw=new StringWriter();
        PrintWriter pw =new PrintWriter(sw);
        try (Reader in =new FileReader("C:\\Users\\fabel\\Desktop\\Work\\Εκπαίδευση Java\\File2.txt") )
        {

        }
        catch (IOException e) {
            e.printStackTrace(pw);
            String s=  sw.toString();
        }

    }


}
