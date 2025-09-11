package com.jparepository.demo_jpa_repo.repository;

import com.jparepository.demo_jpa_repo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
