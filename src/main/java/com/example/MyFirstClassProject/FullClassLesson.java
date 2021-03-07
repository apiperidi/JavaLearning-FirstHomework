package com.example.MyFirstClassProject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FullClassLesson {
    private static final String filediectory= System.getProperty("user.home")+ File.separator+"Desktop\\Work\\Εκπαίδευση Java\\";

    public static void main(String[] args) {
       // System.out.println(Directory.HOME_DIRECTORY.getPath()); //C:\Users\fabel
       // System.out.println(Directory.JAVA_HOME.getPath()); //C:\Program Files\Java\jdk-11.0.10
       //  System.out.println(Directory.FILE_DIRECTORY.getPath()); //C:\Users\fabel\data_files\

        PersonEte myPerson = new PersonEte();
        myPerson.callEmployee();
        myPerson.fillComparingList();
        myPerson.writeComparingList();
        myPerson.addList();
        myPerson.FinalReadListFromFile();


       // File dataFiles = new File(filediectory);
       //  listFileAttributes(dataFiles);
       // listSubFiles(dataFiles);
       String answer= readInputWithBufferedReader();
        if (String.valueOf(answer).toLowerCase().equalsIgnoreCase("Yes"))
        {
            deleteDirectoryAndFile();
        }
        else
            System.out.println(answer);
    }


   static class PersonEte extends findEmployee implements AddListOfEmployees
    {
        List<String> myNewTestList=null;
        List<String> internalList=null;
        List<String> externalList;
        @Override
        void callEmployee() {
            myNewTestList= generateStringNames();
        }

        @Override
        void fillComparingList() {

            internalList=myNewTestList.stream().filter(i->i.length()<=5)
                    .sorted(Comparator.comparing(String::valueOf)).collect(Collectors.toList());
          //  internalList.forEach(i->System.out.printf("%s " ,i));
        }

        @Override
        void writeComparingList() {
//θα γράψω την λίστα
            writeListToFile(internalList,false);
        }


        @Override
        public void addList() {
            externalList=myNewTestList.stream().filter(i->i.length()>5)
                    .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            externalList.add("Piperidi");
          //  externalList.forEach(i->System.out.printf("%s " ,i));
            writeListToFile(externalList,true);
        }
    }

    abstract static class findEmployee {

        abstract  void callEmployee();
        abstract void fillComparingList();
        abstract void writeComparingList();
        final void FinalReadListFromFile(){ readListFromFile(); }

    }


   interface AddListOfEmployees {
            void addList();
    }


    public static void writeListToFile(List<String> testList,boolean append)
    {
        System.out.println("dd");
        try (Writer out =new FileWriter(filediectory+"Test_txt"+File.separator+"TestList.txt",append);
             BufferedWriter bw=new BufferedWriter(out))
        {

            String word=" Employees";
            bw.write(word);
            bw.newLine();
            testList.forEach(i-> {
                try {
                    bw.write(i);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readListFromFile()
    {
        try (Reader in =new FileReader(filediectory+"Test_txt"+File.separator+"TestList.txt") )
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

    private static List<String> generateStringNames() {
        List<String> firstSet = new ArrayList<>() ;

        firstSet.add("Angela");
        firstSet.add("George");
        firstSet.add("Kostas");
        firstSet.add("Xara");
        firstSet.add("Manos");
        firstSet.add("Antonis");
        firstSet.add("Vasilis");
        firstSet.add("Anda");
        firstSet.add("Angela");
        return firstSet;
    }

    private static void listFileAttributes(File file) {
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.canWrite());
        System.out.println(file.canRead());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(new Date(file.lastModified()));
        System.out.println(file.length());
    }

    private static void listSubFiles(File file) {
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            File f = new File(filediectory, list[i]);
            if (f.isDirectory()) {
                System.out.println(f.getName());
            }
        }
    }

    private static void deleteDirectoryAndFile() {
        try {
            Path directory = Paths.get(filediectory + "Test_txt");
            String[] list = directory.toFile().list();
            for (String fileName : list) {
                Path filePath = Paths.get(directory + File.separator + fileName);
                Files.delete(filePath);
            }
           // Files.delete(directory);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private static String readInputWithBufferedReader() {
        String answer =null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Do you want to delete all files");
            answer = br.readLine();
         //   System.out.println(answer);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return answer;
    }
}
