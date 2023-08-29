package com.ankoki.joyonghan.misc;

import com.ankoki.sakura.JSON;
import org.jetbrains.annotations.Nullable;

// TODO convert classes not using these utility methods.
/**
 * You should do your own key contains check on any of these methods, otherwise they will return null.
 */
public class JSONMisc {

	/**
	 * Gets a string from a JSON map.
	 * Will also convert any other entry type to a string.
	 *
	 * @param json the json to look in.
	 * @param key the key to use.
	 *
	 * @return the string value if present, else null.
	 */
	@Nullable
	public static String getString(JSON json, String key) {
		if (!json.containsKey(key))
			return null;
		Object value = json.get(key);
		if (value instanceof String string)
			return string;
		else
			return String.valueOf(value);
	}

	/**
	 * Gets a number from a JSON map.
	 *
	 * @param json the json to look in.
	 * @param key the key to use.
	 *
	 * @return the number value if present, else null.
	 */
	@Nullable
	public static Number getNumber(JSON json, String key) {
		if (!json.containsKey(key))
			return null;
		Object value = json.get(key);
		return value instanceof Number number ? number : null;
	}

}
