//package com.neshan.demo;
//
//import com.neshan.demo.Domain.GitUser;
//import com.neshan.demo.Domain.Student;
//import com.neshan.demo.Repositories.StudentRepository;
//import com.neshan.demo.Services.GitHubLookupService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Component
//public class AppRunner implements CommandLineRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
//
//    private final GitHubLookupService gitHubLookupService;
//
//    private final StudentRepository studentRepository;
//
//    public AppRunner(GitHubLookupService gitHubLookupService,StudentRepository studentRepository) {
//        this.gitHubLookupService = gitHubLookupService;
//        this.studentRepository = studentRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Start the clock
//        long start = System.currentTimeMillis();
//
//        // Kick of multiple, asynchronous lookups
//        CompletableFuture<GitUser> page1 = gitHubLookupService.findUserAsync("PivotalSoftware");
//        CompletableFuture<GitUser> page2 = gitHubLookupService.findUserAsync("CloudFoundry");
//        CompletableFuture<GitUser> page3 = gitHubLookupService.findUserAsync("Spring-Projects");
//        CompletableFuture<GitUser> page4 = gitHubLookupService.findUserAsync("Mahdipy");
//
//        // Wait until they are all done
//        CompletableFuture.allOf(page1,page2,page3, page4).join();
//
//        // Print results, including elapsed time
//        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
//        logger.info("--> " + page1.get());
//        logger.info("--> " + page2.get());
//        logger.info("--> " + page3.get());
//
//        start = System.currentTimeMillis();
//        final FileOutputStream fout = new FileOutputStream("test.txt", true);
//        final ObjectOutputStream out = new ObjectOutputStream(fout);
//        List<Student> st = studentRepository.findAll();
//        int i =0;
////        CompletableFuture<Student> studentCompletableFuture1 = null;
////        CompletableFuture<Student> studentCompletableFuture2 = null;
////        CompletableFuture<Student> studentCompletableFuture3 = null;
////        List ls = new ArrayList();
//
//        for (i = 0; i<st.size(); i++){
//
//            CompletableFuture<Student> studentCompletableFuture = gitHubLookupService.printUserAsync(st.get(i), out );
////            ls.add(studentCompletableFuture);
////            CompletableFuture<Student> studentCompletableFuture2 = gitHubLookupService.printUserAsync(st.get(i+333), out );
////            CompletableFuture<Student> studentCompletableFuture3 = gitHubLookupService.printUserAsync(st.get(i+666), out );
//            CompletableFuture.allOf(studentCompletableFuture).join();
//        }
////        CompletableFuture.allOf().join();
//
//        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
//
//    }
//
//}
