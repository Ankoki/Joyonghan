package com.ankoki.joyonghan;

import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.JSON.MalformedJsonException;

import java.io.IOException;

// 조용한 ~ TRANQUIL
public class Joyonghan {

	private static Joyonghan current;
	private final AppData data;
	private final Frontend frontend;

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

	public static void main(String[] args) {
		System.out.println("Joyonghan is enabling.");
		try {
			Joyonghan.current = new Joyonghan(new AppData(Misc.getOperatingSystem()), new Frontend());
		} catch (IOException | IllegalAccessException | MalformedJsonException ex) {
			ex.printStackTrace();
		}
	}

}
