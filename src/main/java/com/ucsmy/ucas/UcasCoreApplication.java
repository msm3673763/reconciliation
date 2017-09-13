package com.ucsmy.ucas;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan
@EnableBatchProcessing
public class UcasCoreApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UcasCoreApplication.class, args);

	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UcasCoreApplication.class);
    }

}
