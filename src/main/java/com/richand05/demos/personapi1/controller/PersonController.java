package com.richand05.demos.personapi1.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    List<Person> people = new ArrayList<>();

    int id = 1;

    @GetMapping("/getPeople")
    public List<Person> getHelloWorld() {
        return people;
    }

    @PostMapping("/savePerson")
    public String savePerson(@RequestBody Person person){

        for (int i=0; i<=500; i++) {
            Person p;
            if(i % 2 == 0){
                p = new Person(id, "Name ".concat(String.valueOf(id)), "Lastname ".concat(String.valueOf(id)),18);
            }else{
                p = new Person(id, "Single name ".concat(String.valueOf(id)), "Lastname ".concat(String.valueOf(id)),30);
            }
            people.add(p);
            id++;
        }
        Person newPerson = new Person(id, person.name, person.lastname,20);
        people.add(newPerson);
        id++;
        return "Person saved: ".concat(person.name).concat(" ").concat(person.lastname);
    }

    @GetMapping("getPersonById/{id}")
    public Person getPersonById(@PathVariable int id){
        Person searchPerson = people.stream()
                .filter(person -> person.id == id)
                .findFirst()
                .get();

        return searchPerson;
    }

    @DeleteMapping("/deletePersonById/{id}")
    public String deletePersonById(@PathVariable int id){

        Person finalPerson = people.stream()
                .filter(person -> person.id == id)
                .findFirst()
                .get();

        String name = finalPerson.name;
        String lastname = finalPerson.lastname;

        people.remove(finalPerson);

        return "Person removed: ".concat(name).concat(" ").concat(lastname);
    }

    @GetMapping("/getPeopleNameStartWithS")
    public List<Person> getPeopleNameStartWithS(){

        return people.stream()
                .filter(person -> person.name.startsWith("S"))
                .collect(Collectors.toList());

    }

    @GetMapping("/groupingPeopleByAge")
    public Map<Integer, List<Person>> groupingByAge(){

        return people.stream()
                .collect(Collectors.groupingBy(person -> person.age));

    }
}

class Person{

    public int id;
    public String name;
    public String lastname;

    public int age;

    public Person(int id, String name, String lastname, int age) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }
}