package com.example.MyFirstClassProject;

public class AbstractClass {
    public static void main(String[] args) {


Dog myDog=new Dog();
myDog.bark();
myDog.run();
        System.out.println("-------------------------------");

Car myCar =new Car();
//myCar.start();
myCar.park();
myCar.unPark();
myCar.gotoStartingLine();
myCar.race();
myCar.gotoPit();
myCar.leavePit();
myCar.setupEngine(4500,"ETE",Tyre.HARD);

    }
}

abstract class Animal{
abstract void bark();
void run()
{System.out.println("Im running");}
}

class Dog extends Animal
{
    @Override
    void bark() {
        System.out.println("Im barking");
    }
    @Override
    void run()
    {System.out.println("Im running faster");}
}

//*******
interface Racing {
    void gotoPit();
    void leavePit();
    void gotoStartingLine();
    void race();
    void setupEngine(int horsePower, String sponsor, Tyre typeOfTyre);
}
enum Tyre{SOFT,MEDIUM,HARD;}

abstract class Vehicle {
     Engine carEngine=new Engine();

    abstract void park();
    abstract void unPark();
   final void startEngine()
    {
        carEngine.start(); // ΕΔΩ Ξεκινάει το πρόβλημα
        //System.out.println("Start Engine");
        }
    void stopEngine()
    {
        carEngine.stop();
        //System.out.println("stop Engine");
        }
}
class Engine{
    void start()
    {System.out.println("Start Engine");}
    void stop()
    {System.out.println("Stop Engine");}
}

// έχω μια κλάση Car η οποία κληρονομεί την Vehicle
//Η Vehicle είναι abstract που σημαίνει ότι θα πρέπει να κάνω συγκεκριμένες υλοποιησεις
class Car extends Vehicle implements Racing
{
    @Override
    void park() {
        System.out.println("Im parking");
        stopEngine();
    }
    @Override
    void unPark() {

        System.out.println("Im un-parking");
        startEngine();
    }

    @Override
    public void gotoPit() {
        System.out.println("Entering Pit");
    }

    @Override
    public void leavePit() {
        System.out.println("Leaving Pit");
    }

    @Override
    public void gotoStartingLine() {
        System.out.println("Heading to starting Line");
    }

    @Override
    public void race() {
        System.out.println("Race just begin");
    }

    @Override
    public void setupEngine(int horsePower, String sponsor, Tyre typeOfTyre)
    {
        System.out.println("Horse Power is: " +horsePower +"Hp");
        System.out.println("Sponsor is: " +sponsor);
            switch (typeOfTyre)
            {
            case SOFT:
                System.out.println("type Of Tyre is Soft");
            case MEDIUM:
                System.out.println("type Of Tyre is MEDIUM");
            case HARD:
                System.out.println("type Of Tyre is HARD");
            }
    }
}


