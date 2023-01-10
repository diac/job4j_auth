package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.exception.InvalidPasswordFormatException;
import ru.job4j.repository.PersonRepository;
import ru.job4j.util.Passwords;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePersonService implements PersonService {

    private final PersonRepository personRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        personRepository.findAll().iterator().forEachRemaining(people::add);
        return people;
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Person save(Person person) {
        if (!Passwords.isValidPasswordFormat(person.getPassword().toCharArray())) {
            throw new InvalidPasswordFormatException("Invalid password format");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public boolean existsById(int id) {
        return personRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }
}