package com.javademo.bulk_update_initiative_demo.service;

import com.javademo.bulk_update_initiative_demo.model.Initiative;
import com.javademo.bulk_update_initiative_demo.repository.InitiativeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final Executor executor;

    private static final int CHUNK_SIZE = 100;

    // Add single initiative
    @Transactional
    public Initiative addInitiative(Initiative initiative){
        return initiativeRepository.save(initiative);
    }

    @Transactional
    public Initiative updateInitiative(Long id, Initiative updated){
        Initiative existing = initiativeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Initiative not found with id " + id));

        existing.setTitle(updated.getTitle());
        existing.setStatus(updated.getStatus());
        existing.setOwner(updated.getOwner());
        existing.setDescription(updated.getDescription());

        return initiativeRepository.save(existing);
    }

    // bulk update initiatives (Thread-safe & parallel)
    public void bulkUpdate(List<Initiative> initiatives) {
        List<List<Initiative>> partitions = partitionList(initiatives, CHUNK_SIZE);

        List<CompletableFuture<Void>> futures = partitions.stream()
                .map(chunk -> CompletableFuture.runAsync(() -> batchUpdate(chunk), executor))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }


    private void batchUpdate(List<Initiative> initiatives) {
        String sql = "UPDATE initiatives SET title=?, description=?, owner=?, status=? WHERE id=?";

        jdbcTemplate.batchUpdate(sql, initiatives, initiatives.size(), (ps, initiative) -> {
            ps.setString(1, initiative.getTitle());
            ps.setString(2, initiative.getDescription());
            ps.setString(3, initiative.getOwner());
            ps.setString(4, initiative.getStatus());
            ps.setLong(5, initiative.getId());
        });
    }

    private List<List<Initiative>> partitionList(List<Initiative> list, int size) {
        List<List<Initiative>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size){
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

}
