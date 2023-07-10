package com.ankoki.joyonghan.auth;

import com.ankoki.sakura.JSONSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Account extends JSONSerializable {

	/**
	 * Required method by Sakura. Used to deserialize a payment calculator object.
	 *
	 * @param map the serialized object.
	 * @return the deserialized object.
	 */
	@Nullable
	@SuppressWarnings("unused") // Sakura uses reflection to access this method, it will appear unused.
	public static Account deserialize(Map<String, Object> map) {
		if (map.isEmpty() || map.get("username") != null)
			return null;
		String username = (String) map.get("username");
		UUID uuid = UUID.fromString(String.valueOf(map.get("uuid")));
		String email = (String) map.get("email");
		String passwordHash = (String) map.get("password-hash");
		return new Account(username, uuid, email, passwordHash);
	}

	private final String username;
	private final UUID uuid;
	private final String email;
	// We store the hash locally in the data.json to keep people logged in over restart, incase passwords have been changed.
	private final String passwordHash;

	public Account(String username, UUID uuid, String email, String passwordHash) {
		this.username = username;
		this.uuid = uuid;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the username of this account.
	 *
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the UUID of this account.
	 *
	 * @return the uuid.
	 */
	public UUID getUUID() {
		return uuid;
	}

	/**
	 * Gets the email of this account.
	 *
	 * @return the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Checks if the current information is still valid.
	 *
	 * @return true if valid.
	 */
	public boolean isValid() {
		return AuthAssistant.attemptLogin(this.email, this.passwordHash);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("uuid", uuid);
		map.put("email", email);
		map.put("password-hash", passwordHash);
		return map;
	}

}
