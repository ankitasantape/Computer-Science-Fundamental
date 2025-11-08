package com.javademo.bulk_update_initiative_demo.controller;

import com.javademo.bulk_update_initiative_demo.model.Initiative;
import com.javademo.bulk_update_initiative_demo.service.InitiativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/initiatives")
@RequiredArgsConstructor
public class InitiativeController {

    private final InitiativeService initiativeService;

    // add single
    @PostMapping("/add")
    public Initiative addInitiative(@RequestBody Initiative initiative){
        return initiativeService.addInitiative(initiative);
    }

    @PutMapping("/update/{id}")
    public Initiative updateInitiative(@PathVariable Long id, @RequestBody Initiative initiative){
        return initiativeService.updateInitiative(id, initiative);
    }

    @PutMapping("/bulkupdate")
    public String bulkUpdate(@RequestBody List<Initiative> initiatives){
        initiativeService.bulkUpdate(initiatives);
        return "Bulk update completed successfully";
    }

}
