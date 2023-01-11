package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    public boolean save(Person person) {
        if (!Passwords.isValidPasswordFormat(person.getPassword().toCharArray())) {
            throw new InvalidPasswordFormatException("Invalid password format");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        try {
            personRepository.save(person);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Person person) {
        boolean success = false;
        if (personRepository.existsById(person.getId())) {
            personRepository.delete(person);
            success = true;
        }
        return success;
    }

    @Override
    public boolean existsById(int id) {
        return personRepository.existsById(id);
    }

    @Override
    public boolean deleteById(int id) {
        boolean success = false;
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            success = true;
        }
        return success;
    }
}