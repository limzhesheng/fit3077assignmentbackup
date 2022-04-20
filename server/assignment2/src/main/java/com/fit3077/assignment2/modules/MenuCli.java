package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.return_types.UserState;

public class MenuCli {
    private static Scanner sc = new Scanner(System.in);
	private static MenuCli menuCli;

	private UserState userSessionToken = null;

	public UserState getUserSessionToken() {
		return this.userSessionToken;
	}

    private MenuCli() {/*..*/}

    public static MenuCli getInstance() {
        if (menuCli == null) {
            menuCli = new MenuCli();
        }
        return menuCli;
    }

	public void menuCli() throws IOException, InterruptedException {
		int actionCode = 0;
		System.out.println("COVID Test Registration System");
		while (actionCode != 99) {
			System.out.print("Enter 1 to login, 2 to browse as guest, "+
			"3 if you are a frontdesk staff member, 4 if you are a healthcare staff member, or " + CliConfig.EXIT_CODE + " to exit application: ");
			try {
				actionCode = sc.nextInt();
				if (actionCode == 1 || actionCode == 3 || actionCode == 4) {
					userSessionToken = LoginCli.getInstance().login(actionCode);
					if (userSessionToken != null && userSessionToken.getLoginStatus()) {
						if (actionCode == 1){
							actionCode = userAndGuestPanel();
						} else if (actionCode == 3) {
							actionCode = frontDeskPanel();
						} else if (actionCode == 4) {
							actionCode = healthWorkerPanel();
						}
					}
				}
				if (actionCode == 2){
					actionCode = userAndGuestPanel();
				}
			} catch (InputMismatchException e) {
				System.err.println("Invalid input.");
				sc.nextLine();
			}
		}
		sc.close();
	}

	private int userAndGuestPanel() {
		int actionCode = 0;
		String loggedIn = userSessionToken != null && userSessionToken.getLoginStatus() ? " " : " not ";
		System.out.println("You are" + loggedIn + "logged in.");
		while (actionCode != CliConfig.EXIT_CODE) {
			System.out.println(CliConfig.MENU_HEADER);
			System.out.println("[1] Search Testing Sites");
			System.out.println("[2] Login");
			System.out.println("[3] Return");
			System.out.println(CliConfig.EXIT_PROMPT);
			actionCode = sc.nextInt();
			if (actionCode == 1) {
				TestSiteSearchCli.getInstance().search(userSessionToken);
			}
			else if (actionCode == 2) {
				return 98;
			}
			else if (actionCode == 3) {
				return 98;
			}
		}
		return 99;
	}

	private int frontDeskPanel() {
		int actionCode = 0;
		while (actionCode != 99) {
			System.out.println(CliConfig.MENU_HEADER);
			System.out.println("[1] On-Site Booking");
			System.out.println("[2] Return");
			System.out.println(CliConfig.EXIT_PROMPT);
			actionCode = sc.nextInt();
			if (actionCode == 2) {
				return CliConfig.RETURN_CODE;
			}
		}
		return actionCode;
	}

	private int healthWorkerPanel() {
		int actionCode = 0;
		while (actionCode != 99) {
			System.out.println(CliConfig.MENU_HEADER);
			System.out.println("[1] On-Site Testing");
			System.out.println("[2] Return");
			System.out.println(CliConfig.EXIT_PROMPT);
			actionCode = sc.nextInt();
			if (actionCode == 1) {
				OnSiteTestCli.getInstance().onSiteTestForm();
			}
			else if (actionCode == 2) {
				return CliConfig.RETURN_CODE;
			}
		}
		return actionCode;
	}

}
