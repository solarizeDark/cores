package ru.fedusiv.lambda;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Main {

    static class Person {
        
        private String name;
        
        public Person(String name) {
            this.name = name;
        }
        
        public String getName() { return name; }
        public void setName(String name) {
            this.name = name;
        }

        public String toString() { return "<" + this.name + ">"; }

    }

    public static UnaryOperator<Person> nt =
            person -> {
                person.setName("NT_".concat(person.getName()));
                return person;
            };
    
    public static void main(String[] args) {
        Person p = new Person("foo");
        Person p2 = new Person("bar");
        Person p3 = new Person("baz");

        Stream.of(p, p2, p3).map(Person::getName).forEach(System.out::println);

        Stream.of(p, p2, p3).map(nt).forEach(System.out::println);
    }

}
