package com.fit3077.assignment2;

import com.fit3077.assignment2.modules.MenuCli;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		MenuCli.getInstance().menuCli();
	}

}
