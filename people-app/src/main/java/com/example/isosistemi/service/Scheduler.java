package com.example.isosistemi.service;

import com.example.isosistemi.model.Person;
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

    private List<Person> people;

    @PostConstruct
    private void setup() {
        people = new ArrayList<>();
        people.add(new Person("Stefano", "Terrile", 100));
        people.add(new Person("Alessandro", "Dieni", 25));
        people.add(new Person("Maurizio", "Romeo", 99));
        people.add(new Person("Rocky", "Balboa", 74));
    }

    @Scheduled(fixedRate = 3000)
    public void publishRandomPerson() {
        log.info("Sending random person...");
        this.template.convertAndSend("/people/random", pickRandomPerson());
    }

    Person pickRandomPerson() {
       return people.get(new Random().nextInt(people.size()));
    }

}