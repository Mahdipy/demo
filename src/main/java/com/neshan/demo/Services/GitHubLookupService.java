package com.neshan.demo.Services;

import com.neshan.demo.Domain.Student;
import com.neshan.demo.Domain.User;
import com.neshan.demo.Domain.User2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {
    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User2> findUserAsync(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User2 results = restTemplate.getForObject(url, User2.class);
        try (final FileOutputStream fout = new FileOutputStream("test.txt", true);
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(results.toString());
            out.flush();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }


    @Async
    public CompletableFuture<Student> printUserAsync(Student student, ObjectOutputStream out) throws IOException {
        out.writeObject(student.toString());
        out.flush();
        return CompletableFuture.completedFuture(student);
    }
}
