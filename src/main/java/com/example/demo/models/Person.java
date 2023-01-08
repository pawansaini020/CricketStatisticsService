package com.example.demo.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }
}
