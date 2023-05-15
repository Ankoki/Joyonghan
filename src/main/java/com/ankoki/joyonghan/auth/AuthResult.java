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
	 * The given username is in use.
	 */
	USERNAME_IN_USE,
	/**
	 * Email/username was not found in the database.
	 */
	EMAIL_NOT_FOUND,
	/**
	 * Password hash does not match.
	 */
	INCORRECT_PASSWORD,
	/**
	 * Only sent when there is an error.
	 */
	FAILURE;

}
