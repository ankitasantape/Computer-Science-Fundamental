package com.javademo.bulk_update_initiative_demo.repository;

import com.javademo.bulk_update_initiative_demo.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
}
