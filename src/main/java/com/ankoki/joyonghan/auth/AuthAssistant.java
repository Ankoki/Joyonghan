package com.ankoki.joyonghan.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.database.Database;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.Pair;

import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthAssistant {

	/**
	 * Attempts to log in to Joyonghan.
	 *
	 * @param email the email to try.
	 * @param password the password to try.
	 * @return a pair containing the account and the result. The account will be null unless successful.
	 */
	public static Pair<Account, AuthResult> attemptLogin(String email, char[] password) {
		if (!Misc.hasInternet())
			return new Pair<>(null, AuthResult.NO_INTERNET);
		Database database = Joyonghan.getInstance().getDatabase();
		try {
			ResultSet set = database.executeQuery("SELECT * FROM Auth WHERE email=?;", email);
			if (set == null || !set.next())
				return new Pair<>(null, AuthResult.EMAIL_NOT_FOUND);
			String passwordHash = set.getNString("password");
			if (!Joyonghan.getInstance().getHasher().compare(password, passwordHash))
				return new Pair<>(null, AuthResult.INCORRECT_PASSWORD);
			String username = set.getNString("username");
			UUID uuid = UUID.fromString(set.getNString("uuid"));
			Account account = new Account(username, uuid, email, passwordHash);
			System.out.println("Successfully logged in as '" + username + "'.");
			return new Pair<>(account, AuthResult.SUCCESS);
		} catch (SQLException | GeneralSecurityException ex) {
			ex.printStackTrace();
		}
		return new Pair<>(null, AuthResult.FAILURE);
	}

	/**
	 * Attempts to log in using the hash instead of the password.
	 *
	 * @param email the email to try.
	 * @param hash the hash to try.
	 * @return true if valid.
	 */
	public static boolean attemptLogin(String email, String hash) {
		if (!Misc.hasInternet())
			return false;
		Database database = Joyonghan.getInstance().getDatabase();
		try {
			ResultSet set = database.executeQuery("SELECT * FROM Auth WHERE email = '" + email + "'");
			if (set == null || !set.isBeforeFirst())
				return false;
			String passwordHash = set.getNString("password");
			return passwordHash.equals(hash);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Attempts to register a new account with Joyonghan.
	 *
	 * @param email the email to use.
	 * @param username the username to use.
	 * @param password the password to use.
	 * @return a pair containing the account and the result. The account will be null unless successful.
	 */
	public static Pair<Account, AuthResult> attemptRegister(String email, String username, char[] password) {
		if (!Misc.hasInternet())
			return new Pair<>(null, AuthResult.NO_INTERNET);
		if (!Misc.isValidEmail(email))
			return new Pair<>(null, AuthResult.INVALID_EMAIL);
		if (!Misc.isValidUsername(username))
			return new Pair<>(null, AuthResult.INVALID_USERNAME);
		if (!Misc.isValidPassword(password))
			return new Pair<>(null, AuthResult.INVALID_PASSWORD);
		Database database = Joyonghan.getInstance().getDatabase();
		try {
			ResultSet set = database.executeQuery("SELECT * FROM Auth WHERE email = '" + email + "'");
			if (set != null && set.isBeforeFirst())
				return new Pair<>(null, AuthResult.EMAIL_IN_USE);
			set = database.executeQuery("SELECT * FROM Auth WHERE username = '" + username + "'");
			if (set != null && set.isBeforeFirst())
				return new Pair<>(null, AuthResult.USERNAME_IN_USE);
			UUID uuid = UUID.randomUUID();
			do {
				set = database.executeQuery("SELECT * FROM Auth WHERE uuid = '" + uuid + "'");
			} while (set != null && set.isBeforeFirst());
			String hash = Joyonghan.getInstance().getHasher().hash(password);
			Account account = new Account(username, uuid, email, hash);
			database.executeUpdate("INSERT INTO Auth(email,uuid,username,password) VALUES(?,?,?,?)", email, uuid.toString(), username, hash);
			Joyonghan.getInstance().setAccount(account);
			return new Pair<>(account, AuthResult.SUCCESS);
		} catch (GeneralSecurityException | SQLException ex) {
			ex.printStackTrace();
		}
		return new Pair<>(null, AuthResult.FAILURE);
	}

}
