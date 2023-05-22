package com.ankoki.joyonghan.auth;

public enum AuthResult {

	/**
	 * Email and password were correct/valid.
	 */
	SUCCESS,
	/**
	 * The given email is in use.
	 */
	EMAIL_IN_USE,
	/**
	 * The given email is not the correct format.
	 */
	INVALID_EMAIL,
	/**
	 * The given username is in use.
	 */
	USERNAME_IN_USE,
	/**
	 * The given username does not match the Joyonghan requirements.
	 */
	INVALID_USERNAME,
	/**
	 * The given password doesn't meet the Joyonghan requirements.
	 */
	INVALID_PASSWORD,
	/**
	 * Email/username was not found in the database.
	 */
	EMAIL_NOT_FOUND,
	/**
	 * Password hash does not match.
	 */
	INCORRECT_PASSWORD,
	/**
	 * There is no internet on this device.
	 */
	NO_INTERNET,
	/**
	 * Only sent when there is an error.
	 */
	FAILURE;

}
