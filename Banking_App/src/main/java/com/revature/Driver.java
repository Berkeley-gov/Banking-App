package com.revature;

import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountInputException;
import com.revature.service.Menu;
import org.apache.log4j.Logger;


public class Driver {

	private static final Logger log = Logger.getLogger(Driver.class);

	public static void main(String[] args) {
		do {
			try {
				log.info("WORLD BANK: MAIN MENU");
				Menu.loginMenu(); // The program executes through an infinite loop
			} catch (InvalidAccountInputException | InsufficientFundsException e) {
				e.printStackTrace();
			}
		} while(true);
	}
}
