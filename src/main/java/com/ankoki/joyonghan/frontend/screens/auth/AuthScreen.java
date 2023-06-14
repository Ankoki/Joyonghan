package com.ankoki.joyonghan.frontend.screens.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.auth.Account;
import com.ankoki.joyonghan.auth.AuthAssistant;
import com.ankoki.joyonghan.auth.AuthResult;
import com.ankoki.joyonghan.frontend.Screen;
import com.ankoki.joyonghan.frontend.screens.home.HomeScreen;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.Pair;

import javax.swing.*;

public abstract class AuthScreen extends Screen {

	/**
	 * Creates a new screen with the given parent.
	 *
	 * @param parent the parent frame.
	 */
	public AuthScreen(JFrame parent) {
		super(parent);
	}

	/**
	 * Attempts to log in to Joyonghan.
	 *
	 * @param email the email.
	 * @param pass the password.
	 * @param error the error label.
	 * @param persistent whether to stay logged in over restart.
	 */
	protected void attemptLogin(String email, char[] pass, JLabel error, boolean persistent) {
		if (!Misc.hasInternet()) {
			error.setText("You are not connected to the internet. Please check you have a valid connection.");
			return;
		}
		Pair<Account, AuthResult> pair = AuthAssistant.attemptLogin(email, pass);
		Account account = pair.getFirst();
		if (account != null) {
			Joyonghan.getInstance().setAccount(account, persistent);
			Joyonghan.getInstance().getFrontend().showScreen(new HomeScreen(this.getParent()));
		} else {
			AuthResult result = pair.getSecond();
			switch (result) {
				case EMAIL_NOT_FOUND -> error.setText("The given email was not found! Did you mean to register?");
				case INCORRECT_PASSWORD -> error.setText("The password was incorrect.");
				case NO_INTERNET -> error.setText("You are not connection to the internet. Please check you have a valid connection.");
				case FAILURE -> error.setText("There was an internal error. Do you have internet? Please try again.");
			}
		}
	}

	/**
	 * Attempts to register to Joyonghan.
	 *
	 * @param email the email.
	 * @param user the username.
	 * @param pass the password.
	 * @param error the error label.
	 * @param persistent whether to stay logged in over restart or not.
	 */
	protected void attemptRegister(String email, String user, char[] pass, JLabel error, boolean persistent) {
		if (!Misc.hasInternet()) {
			error.setText("You are not connected to the internet. Please check you have a valid connection.");
			return;
		}
		Pair<Account, AuthResult> pair = AuthAssistant.attemptRegister(email, user, pass);
		Account account = pair.getFirst();
		if (account == null) {
			switch (pair.getSecond()) {
				case EMAIL_IN_USE -> error.setText("The given email is already registered. Did you mean to log in?");
				case INVALID_EMAIL -> error.setText("The given email is invalid. Are you sure you typed it right?");
				case USERNAME_IN_USE -> error.setText("The given username is already in use.");
				case INVALID_USERNAME -> error.setText("The given username is not valid. Make sure to use 4-16 alphanumeric characters.");
				case INVALID_PASSWORD -> error.setText("The password requires 8-20 characters which contain an uppercase character, lowercase character, and a digit.");
				case NO_INTERNET -> error.setText("There is no internet on this device currently. Check your connection.");
				case FAILURE -> error.setText("There was an internal error. Please try again.");
			}
		} else {
			Joyonghan.getInstance().setAccount(account, persistent);
			Joyonghan.getInstance().getFrontend().showScreen(new HomeScreen(this.getParent()));
			Joyonghan.getInstance().getData().getPersistant().put("stay-logged-in", persistent);
		}
	}

}
