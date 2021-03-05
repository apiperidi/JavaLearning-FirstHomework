package com.example.MyFirstClassProject;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLambdas {
    public static void main(String[] args) {
Stream<String> emptyStream= Stream.empty();
Stream<String> FirstStream= Stream.of("Athens","Thesalloniki","Patra", "larisa","Ioannina");
Stream<String> SecondStream= Pattern.compile(",").splitAsStream("a,b,c,d,e,f");
Stream<String> ThirdStream= Arrays.asList("one","two").stream();
//1o paradeigma
List<String> xxx=generateStringNames().stream().filter(i->i.startsWith("A"))
        .map(String::toUpperCase).sorted().collect(Collectors.toList());
 xxx.forEach(i->System.out.printf("%s " ,i));
        System.out.println();
 //2o paradeigma limit(Number) pairnei ta 2 prwta
        // Yparxei kai to skip(Number) kai to distinct(), sorted()
  generateStreamNames().limit(4).forEach(System.out::println);
  System.out.println();

      boolean x1=  generateStreamNames().anyMatch(s->s.length()>=4);
//Sorted me kritiria
 generateStreamNames().sorted(Comparator.comparing(String::length).thenComparing(String::valueOf))
         .forEach(System.out::println);
        System.out.println();
 generateStreamNames().sorted(Comparator.comparing(String::length).thenComparing(String::valueOf))
                .map(String::toLowerCase).forEach(System.out::println);
        System.out.println();
 //Flatmap (δομή 1 προς Ν)
        generateStreamNames().distinct().sorted().flatMap(s->s.chars().mapToObj(i->(char)i))
                .forEach(System.out::println);
        System.out.println();
//foreach ordered
        generateStreamNames().forEachOrdered(System.out::println);
        System.out.println();
//to set δεν θα φέρει τα dublicates
        generateStreamNames().collect(Collectors.toSet()).forEach(System.out::println);
        System.out.println();
        // Na τη γυρίσω σε LinkedList
        LinkedList<String> myList= generateStreamNames().collect(Collectors.toCollection(LinkedList::new));
   myList.forEach(System.out::println);
        System.out.println();
   //να την γυρίσω σε ένα array kai μετά την γθρνάω σε list (aslist) για να εκμεταλλευτώ το foreach
   String[] myAr=generateStreamNames().distinct().sorted().toArray(String[]::new);
   Arrays.asList(myAr).forEach(System.out::println);
        System.out.println();


   //collect grouping by p.x ton 1 xaraktira
Map<Character,List<String>> groupbyMap=generateStreamNames().distinct().sorted().collect(Collectors.groupingBy(s->s.charAt(0)));
        System.out.println(groupbyMap);
        System.out.println();


Map<Character,Long> groupbyMapSec=generateStreamNames().distinct().sorted().collect(Collectors.groupingBy(s->s.charAt(0),Collectors.counting()));
        System.out.println(groupbyMapSec);
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
    private static Stream<String> generateStreamNames() {
        return Stream.of("Athens","Thesalloniki","Patra", "larisa","Ioannina","Volos","Mykonos","Naxos","Paros");
    }


}
