package com.example.isosistemi.service;

import com.example.isosistemi.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@EnableScheduling
public class Scheduler {

    @Autowired
    private SimpMessagingTemplate template;

    private List<Pet> pets;

    @PostConstruct
    private void setup() {
        pets = new ArrayList<>();
        pets.add(new Pet("Bruno", "Dog"));
        pets.add(new Pet("Fannie", "Cat"));
        pets.add(new Pet("Maya", "Dog"));
        pets.add(new Pet("Simba", "Lion"));
    }

    @Scheduled(fixedRate = 3000)
    public void publishRandomPerson() {
        log.info("Sending random person...");
        this.template.convertAndSend("/pets/random", pickRandomPet());
    }

    Pet pickRandomPet() {
       return pets.get(new Random().nextInt(pets.size()));
    }

}