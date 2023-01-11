package ru.job4j.service;

import ru.job4j.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> findById(int id);

    boolean save(Person person);

    boolean delete(Person person);

    boolean existsById(int id);

    boolean deleteById(int id);
}