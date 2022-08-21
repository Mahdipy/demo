package com.neshan.demo.Domain;

import com.neshan.demo.Repositories.StudentRepository;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private final StudentRepository studentRepository;

    public Scheduler(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void reportCurrentTime() {

        log.info("Average is: {}", studentRepository.findAgeAverage());
    }
}
