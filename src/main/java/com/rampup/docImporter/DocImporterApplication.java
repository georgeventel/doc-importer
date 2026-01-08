package com.rampup.docImporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DocImporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocImporterApplication.class, args);
    }

}
