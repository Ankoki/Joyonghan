package com.ankoki.joyonghan.misc;

import com.ankoki.sakura.JSON;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class Misc {

	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern USERNAME_REGEX = Pattern.compile("^[A-Za-z]\\w{4,14}$");
	private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");

	/**
	 * TODO fix
	 * Checks if the current device is connected to the internet.
	 *
	 * @return true if connected.
	 */
	public static boolean hasInternet() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if the given email is a valid email format.
	 *
	 * @param email the string to test.
	 * @return true if valid.
	 */
	public static boolean isValidEmail(String email) {
		return EMAIL_REGEX.matcher(email).matches();
	}

	/**
	 * Checks if the given username is valid.
	 *
	 * @param username the string to test.
	 * @return true if valid.
	 */
	public static boolean isValidUsername(String username) {
		return USERNAME_REGEX.matcher(username).matches();
	}

	/**
	 * Checks if the given password is valid.
	 * Must be 8-20 characters.
	 * Must contain 1 lowercase and 1 uppercase letter.
	 * Must contain 1 number.
	 *
	 * @param password the string to test.
	 * @return true if valid.
	 */
	public static boolean isValidPassword(char[] password) {
		return PASSWORD_REGEX.matcher(new String(password)).matches();
	}

	/**
	 * Gets the operating system which this device is currently running.
	 *
	 * @return the current operating system. May be null.
	 */
	@Nullable
	public static OperatingSystem getOperatingSystem() {
		return OperatingSystem.getFrom(System.getProperty("os.name"));
	}

	/**
	 * Saves a JSON object async to the given path.
	 *
	 * @param json the json to save.
	 * @param file the file to save it too.
	 */
	public static CompletableFuture<Void> saveJsonTo(JSON json, File file) {
		return CompletableFuture.runAsync(() -> {
			try (FileWriter writer = new FileWriter(file)) {
				writer.write(json.toPrettyString());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}

}
