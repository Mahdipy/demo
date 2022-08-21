package com.neshan.demo.Domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ConfigurationProperties("my-properties")
@Data
public class MyProperties {

    @NotBlank
    private String host;

    @Min(1000)
    private int port;

}
