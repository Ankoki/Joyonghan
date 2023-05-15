package com.ankoki.joyonghan.database;

import com.ankoki.joyonghan.sensitive.MySQL;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database extends MySQL {

	/**
	 * Executes an SQL statement.
	 *
	 * @param sql the statement to execute.
	 * @param placeholders the placeholders to input.
	 * @return true if successful.
	 * @throws SQLException thrown if there is an access error.
	 */
	public boolean execute(String sql, String... placeholders) throws SQLException {
		PreparedStatement statement = this.getConnection().prepareStatement(sql);
		int i = 0;
		for (String placeholder : placeholders) {
			i++;
			statement.setString(i, placeholder);
		}
		return statement.execute();
	}

	/**
	 * Executes an update from an SQL statement.
	 *
	 * @param sql the update to execute.
	 * @param placeholders the placeholders to input.
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
	 * @throws SQLException thrown if there is an access error.
	 */
	public int executeUpdate(String sql, String... placeholders) throws SQLException {
		PreparedStatement statement = this.getConnection().prepareStatement(sql);
		int i = 0;
		for (String placeholder : placeholders) {
			i++;
			statement.setString(i, placeholder);
		}
		return statement.executeUpdate();
	}

	/**
	 * Executes an SQL query.
	 *
	 * @param sql the query to execute.
	 * @param placeholders the placeholders to input.
	 * @return the result of the query.
	 * @throws SQLException thrown if there is an access error.
	 */
	@Nullable
	public ResultSet executeQuery(String sql, String... placeholders) throws SQLException {
		PreparedStatement statement = this.getConnection().prepareStatement(sql);
		int i = 0;
		for (String placeholder : placeholders) {
			i++;
			statement.setString(i, placeholder);
		}
		return statement.executeQuery();
	}

}
