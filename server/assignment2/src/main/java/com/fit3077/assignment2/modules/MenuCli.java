package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.UserState;

import com.fit3077.assignment2.obervers.ConcreteWatched;
import com.fit3077.assignment2.obervers.ConcreteWatcher;
import com.fit3077.assignment2.obervers.Watcher;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuCli extends ConcreteWatched {
    private static Scanner sc = new Scanner(System.in);
	private static MenuCli menuCli;
	private static Watcher newInstanceWatcher;

	private UserState userSessionToken = null;

	public UserState getUserSessionToken() {
		return this.userSessionToken;
	}

    private MenuCli() {/*..*/}

    public static MenuCli getInstance() {
        if (menuCli == null) {
            menuCli = new MenuCli();
			if (newInstanceWatcher == null) {
				// Clear all previous watchers
				menuCli.clearAllWatchers();
				// Create a newInstanceWatcher
				newInstanceWatcher = new ConcreteWatcher();
				// Add watchers to this loginCli
				menuCli.addWatcher(newInstanceWatcher);
				// Notify observer that a new instance of LoginCli has been instantiated
				menuCli.notifyWatchers("A new Instance of Menu CLI has been created");
			}
        }
        return menuCli;
    }

	public void menuCli() throws IOException, InterruptedException {
		System.out.println("Current time: " + ServerConfig.getInstance().instantToIsoString(Instant.now()) + "\n");

		int actionCode = 0;
		System.out.println("\n===== COVID Test Registration System =====");
		while (actionCode != 99) {
//			System.out.print("Enter 1 to login, 2 to browse as guest, "+
//			"3 if you are a frontdesk staff member, 4 if you are a healthcare staff member, or " + CliConfig.EXIT_CODE + " to exit application: ");
			System.out.println("Please enter the number to:");
			System.out.println("[1] Login");
			System.out.println("[2] Browse test sites as Guest");
			System.out.println("[3] Manage Booking as Front Desk Staff");
			System.out.println("[4] Manage Tests as Healthcare Staff");
			System.out.println("["+CliConfig.EXIT_CODE+"] Exit Application");
			System.out.print("Select: ");

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
				log.warn("Invalid input.");
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
			System.out.print("Select: ");

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
			System.out.print("Select: ");
			actionCode = sc.nextInt();
			if (actionCode == 1) {
				System.out.println("\n===== On-Site Booking =====");
				JSONObject testsite = TestSiteManager.getInstance().chooseSite();
				TestBookingCli.getInstance().bookTestOnSite(testsite, userSessionToken);
			}
			else if (actionCode == 2) {
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
			System.out.print("Select: ");
			actionCode = sc.nextInt();
			if (actionCode == 1) {
				System.out.println("\n===== On-Site Testing =====");
				JSONObject testsite = TestSiteManager.getInstance().chooseSite();
				JSONObject booking = TestSiteManager.getInstance().chooseBooking(testsite);
				OnSiteTestCli.getInstance().onSiteTestForm(testsite, userSessionToken, booking);
			}
			else if (actionCode == 2) {
				return CliConfig.RETURN_CODE;
			}
		}
		return actionCode;
	}

}
