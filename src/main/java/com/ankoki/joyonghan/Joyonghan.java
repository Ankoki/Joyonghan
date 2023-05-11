package com.ankoki.joyonghan;

import com.ankoki.joyonghan.accounts.PasswordHasher;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.JSON.MalformedJsonException;

import java.io.IOException;

// 조용한 ~ TRANQUIL
public class Joyonghan {

	private static Joyonghan current;
	private final AppData data;
	private final Frontend frontend;
	private final PasswordHasher hasher = new PasswordHasher();

	/**
	 * Creates a new Joyonghan instance.
	 *
	 * @param data the data to represent it.
	 * @param frontend the frontend to be shown.
	 */
	public Joyonghan(AppData data, Frontend frontend) {
		this.data = data;
		this.frontend = frontend;
	}

	/**
	 * Gets the app data which is used in this instance.
	 *
	 * @return the app data.
	 */
	public AppData getData() {
		return data;
	}

	/**
	 * Gets the frontend which is used in this instance.
	 *
	 * @return the frontend.
	 */
	public Frontend getFrontend() {
		return frontend;
	}

	/**
	 * Gets the password hasher which is used in this instance.
	 *
	 * @return the hasher.
	 */
	public PasswordHasher getHasher() {
		return hasher;
	}

	public static void main(String[] args) {
		System.out.println("Joyonghan is enabling.");
		try {
			Joyonghan.current = new Joyonghan(new AppData(Misc.getOperatingSystem()), new Frontend());
		} catch (IOException | IllegalAccessException | MalformedJsonException ex) {
			ex.printStackTrace();
		}
	}

}
