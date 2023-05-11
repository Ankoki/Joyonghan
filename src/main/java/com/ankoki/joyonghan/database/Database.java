package com.ankoki.joyonghan.database;

import com.ankoki.joyonghan.sensitive.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Database extends MySQL {

	public boolean execute(String sql) throws SQLException {
		return this.getConnection().prepareStatement(sql).execute();
	}

	public int executeUpdate(String sql) throws SQLException {
		return this.getConnection().prepareStatement(sql).executeUpdate();
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return this.getConnection().prepareStatement(sql).executeQuery();
	}

}
