package com.jparepository.demo_jpa_repo;

import com.jparepository.demo_jpa_repo.model.Person;
import com.jparepository.demo_jpa_repo.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoJpaRepoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoJpaRepoApplication.class);

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
		SpringApplication.run(DemoJpaRepoApplication.class, args);
	}


    public void run(String... strings){
        log.info("Current objects in DB: {}", personService.countPersons());

        Person person = personService.createPerson(new Person("Shubham", 23));
        log.info("Person created in DB : {}", person);

        log.info("Current objects in DB: {}", personService.countPersons());

        person.setName("Programmer");
        Person editedPerson = personService.editPerson(person);
        log.info("Person edited in DB : {}", editedPerson);

        personService.deletePerson(person);
        log.info("After deletion, count: {}", personService.countPersons());


    }

}
