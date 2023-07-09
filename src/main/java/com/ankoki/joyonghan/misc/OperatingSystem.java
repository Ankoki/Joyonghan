package com.ankoki.joyonghan.misc;

import org.jetbrains.annotations.Nullable;

public enum OperatingSystem {
	MAC,
	LINUX,
	WINDOWS;

	/**
	 * Gets the operating system from the given name.
	 *
	 * @param operatingSystem the os.name property.
	 * @return the current operating system, or null if not recognised.
	 */
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