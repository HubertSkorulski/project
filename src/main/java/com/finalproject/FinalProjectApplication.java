package com.finalproject;

import com.finalproject.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class FinalProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}
}
