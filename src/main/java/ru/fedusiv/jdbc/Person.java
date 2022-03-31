package ru.fedusiv.jdbc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Integer age;

    @Override
    public String toString() {
        return "<Person: " + this.id + " " + this.name + " " + this.age + ">";
    }

}
