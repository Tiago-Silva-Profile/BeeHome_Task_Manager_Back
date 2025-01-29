package com.beehome.task_manager_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.beehome.task_manager_back")
public class TaskManagerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerBackApplication.class, args);
	}

}
