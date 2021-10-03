package com.music;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MusicWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MusicWebApplication.class, args);
	}
//	@Scheduled(cron = "0/2 * * * * ?")
//	public void schedule() {
//		System.out.println("Hello world: "+new Date().toString());
//	}
}
