package com.example.demo;

import com.example.demo.exceptions.InvalidMatch;
import com.example.demo.exceptions.InvalidTeamException;
import com.example.demo.managers.MatchManager;
import com.example.demo.service.BallService;
import com.example.demo.service.LiveStaticService;
import com.example.demo.service.MatchService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("Welcome");
		GameController gameController = new GameController(new MatchService(new MatchManager()), new BallService(), new LiveStaticService());
		try {
			gameController.intiGame();
		} catch (InvalidMatch e){
			System.out.println("There is some issue game creation.");
		} catch (InvalidTeamException e){
			System.out.println("There is some issue game creation.");
		}
//		SpringApplication.run(DemoApplication.class, args);
	}

}
