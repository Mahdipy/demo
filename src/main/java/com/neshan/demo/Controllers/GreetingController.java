package com.neshan.demo.Controllers;

import com.neshan.demo.Domain.Greeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Value("${my.greeting: default value}")
    private String greetingMessage;

    @Value("static message")
    private String staticMessage;

    @Value("${my.values}")
    private List<String> values;

    @GetMapping("/greeting")
    Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    @GetMapping("/greeting2")
    Greeting greeting2(){
        return new Greeting(counter.incrementAndGet(), greetingMessage);
    }

    @GetMapping("/greeting3")
    Greeting greeting3(){
        return new Greeting(counter.incrementAndGet(), staticMessage);
    }

    @GetMapping("/greeting4")
    Greeting greeting4(){
        return new Greeting(counter.incrementAndGet(), String.join(", ", values));
    }
}
