package com.chatapp.controller;

import com.chatapp.model.Moment;
import com.chatapp.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/moments")
@CrossOrigin(origins = "*")
public class MomentController {

    @Autowired
    private DataService dataService;

    @GetMapping
    public ResponseEntity<List<Moment>> getAllMoments() {
        return ResponseEntity.ok(dataService.getAllMoments());
    }

    @PostMapping
    public ResponseEntity<Moment> createMoment(@RequestBody Moment moment) {
        moment.setId(UUID.randomUUID().toString());
        moment.setTimestamp(System.currentTimeMillis());
        moment.setLikes(0);
        moment.setComments(new ArrayList<>());
        dataService.saveMoment(moment);
        return ResponseEntity.ok(moment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMoment(@PathVariable String id) {
        if (dataService.deleteMoment(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
