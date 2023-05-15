package com.ankoki.joyonghan.misc;

import org.jetbrains.annotations.Nullable;

public enum OperatingSystem {
	MAC,
	LINUX,
	WINDOWS;

	@Nullable
	public static OperatingSystem getFrom(String operatingSystem) {
		String first = operatingSystem.split(" ")[0];
		return switch (first.toUpperCase()) {
			case "MAC" -> MAC;
			case "LINUX" -> LINUX;
			case "WINDOWS" -> WINDOWS;
			default -> null;
		};
	}

}