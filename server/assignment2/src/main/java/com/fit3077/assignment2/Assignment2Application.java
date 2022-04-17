package com.fit3077.assignment2;

import java.util.Scanner;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.LoginStatusResponse;
import com.fit3077.assignment2.modules.LoginManager;
import com.fit3077.assignment2.modules.TestSiteManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment2Application {
	private static Scanner globalScannerInstance = new Scanner(System.in);

	private static LoginStatusResponse userSessionToken = null;

	public static LoginStatusResponse getUserSessionToken() {
		return userSessionToken;
	}

	public static void main(String[] args) {
		int actionCode = 0;
		System.out.println("COVID Test Registration System");
		while (actionCode != 99) {
			System.out.print("Enter 1 to login, 2 to browse as guest, 3 to browse as system admin, or 99 to exit application: ");
			actionCode = globalScannerInstance.nextInt();
			if (actionCode == 1 || actionCode == 3) {
				userSessionToken = LoginManager.getInstance().login();
				if (userSessionToken != null && userSessionToken.getLoginStatus()) {
					if (actionCode == 1){
						actionCode = userAndGuestPanel();
					} else if (actionCode == 3) {
						actionCode = adminPanel();
					}
				}
			}
			if (actionCode == 2){
				actionCode = userAndGuestPanel();
			}
		}
		globalScannerInstance.close();
	}

	public static int userAndGuestPanel() {
		int actionCode = 0;
		String loggedIn = userSessionToken != null && userSessionToken.getLoginStatus() ? " " : " not ";
		System.out.println("You are" + loggedIn + "logged in.");
		while (actionCode != 99) {
			System.out.println("===== Menu =====\n");
			System.out.println("[1] Search Testing Sites");
			System.out.println("[2] Login");
			System.out.println("[3] Return");
			System.out.println("[99] Quit");
			actionCode = globalScannerInstance.nextInt();
			if (actionCode == 1) {
				TestSiteManager.getInstance().search(userSessionToken);
			}
			if (actionCode == 2) {
				return 98;
			}
			if (actionCode == 3) {
				return 98;
			}
		}
		return 99;
	}

	public static int adminPanel() {
		int actionCode = 0;
		while (actionCode != 99) {
			System.out.println("===== Menu =====\n");
			System.out.println("[1] On-Site Booking");
			System.out.println("[2] On-site Testing");
			System.out.println("[3] Return");
			System.out.println("[99] Quit");
			actionCode = globalScannerInstance.nextInt();
			if (actionCode == 3) {
				return 98;
			}
		}
		return actionCode;
	}

}
