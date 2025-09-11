package com.jparepository.demo_jpa_repo.services;

import com.jparepository.demo_jpa_repo.model.Person;

import java.util.List;

public interface PersonService {
    Person createPerson(Person person);
    Person getPerson(Long id);
    Person editPerson(Person person);
    void deletePerson(Person person);
    void deletePerson(Long id);
    List<Person> getAllPersons(int pageNumber, int pageSize);
    List<Person> getAllPersons();
    long countPersons();
}
