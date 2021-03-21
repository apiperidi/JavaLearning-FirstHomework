package com.example.Generics;

import java.io.Serializable;

public class Person  implements Serializable {
    private String name;
    private int age;
    private String adderss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
