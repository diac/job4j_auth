package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;
import ru.job4j.handler.GlobalExceptionHandler;
import ru.job4j.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());


    private final PersonService personService;

    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public ResponseEntity<List<Person>> findAll() {
        List<Person> people = personService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .header("Job4jCustomHeader", "job4j")
                .body(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Person not found for the following ID:  %d", id)
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        boolean success = personService.save(person);
        return new ResponseEntity<>(
                person,
                success ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        boolean success = personService.save(person);
        return new ResponseEntity<>(
                success ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean success = personService.deleteById(id);
        return new ResponseEntity<>(
                success ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PatchMapping("/{id}/change_login")
    public ResponseEntity<Void> changeLogin(@PathVariable int id, @RequestBody PersonDto personDto) {
        Person person = personService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Person not found for the following ID:  %d", id)
                        )
                );
        person.setLogin(personDto.getLogin());
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/change_password")
    public ResponseEntity<Void> changePassword(@PathVariable int id, @RequestBody PersonDto personDto) {
        Person person = personService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Person not found for the following ID:  %d", id)
                        )
                );
        person.setPassword(personDto.getPassword());
        personService.save(person);
        return ResponseEntity.ok().build();
    }
}