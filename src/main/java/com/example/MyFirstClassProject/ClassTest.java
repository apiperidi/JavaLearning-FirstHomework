package com.example.MyFirstClassProject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassTest {
    public static void main(String[] args) {
        List<String> myNewTestList=generateStringNames().stream().filter(i->i.length()>=5)
                .sorted(Comparator.comparing(String::valueOf)).collect(Collectors.toList());
        myNewTestList.forEach(i->System.out.printf("%s " ,i));
        System.out.println();

        List<String> myNewTestListTwo=generateStringNames().stream().filter(i->i.length()>=5)
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        myNewTestListTwo.forEach(i->System.out.printf("%s " ,i));
        System.out.println();
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


}
