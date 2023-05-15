package com.ankoki.joyonghan.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.database.Database;
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
	 * @return the result of the login.
	 */
	public static Pair<Account, AuthResult> attemptLogin(String email, char[] password) {
		Database database = Joyonghan.getInstance().getDatabase();
		try {
			ResultSet set = database.executeQuery("SELECT * FROM Auth WHERE email=?;", email);
			if (set == null || !set.isBeforeFirst())
				return new Pair<>(null, AuthResult.EMAIL_NOT_FOUND);
			String passwordHash = set.getNString("password");
			if (!Joyonghan.getInstance().getHasher().compare(password, passwordHash))
				return new Pair<>(null, AuthResult.INCORRECT_PASSWORD);
			String username = set.getNString("username");
			UUID uuid = UUID.fromString(set.getNString("uuid"));
			Account account = new Account(username, uuid, email, passwordHash);
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

}
