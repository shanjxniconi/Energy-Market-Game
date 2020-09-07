package DAO;

import java.sql.*;

import de.fhluebeck.group3.DAO.BaseDAO;

/**
 * BaseDAO as the Factory Model. This class is mainly responsible for MYSQL
 * database connection, which sets the connection parameters, load the driver at
 * the beginning of the system and provides functions for acquiring and closing
 * DB connections.
 * 
 * @author Jiaxiang Shan on 07/09/2020.
 */
public final class BaseDAO {

	private static Connection conn = null;
	
	/**
	 * Basic attributes for database.
	 * 
	 * CREATE USER 'user'@'localhost' IDENTIFIED WITH mysql_native_password BY
	 * '123'; grant all privileges on energygame.* to 'user'@'localhost';
	 * select * from mysql.user;
	 */
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/energygame?characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "energyGameUser";
	private static final String PASSWORD = "123";
	
	/**
	 * Load the driver for database connection at the beginning of the system.
	 */
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Provide the external classes with the valid DB connections.
	 * 
	 * @return connection valid database connection.
	 * @throws SQLException
	 *             when connection is not available.
	 */
	public static Connection getConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			return conn;
		} else {
			// Try to make the Connection as an singleton.
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return conn;
		}
	}
	
	/**
	 * Provide the external classes with the close function of DB connections
	 * resources.
	 * 
	 * @param conn
	 *            DB Connection.
	 * @param stmt
	 *            Statement.
	 * @param rs
	 *            ResultSet.
	 * 
	 * @throws SQLException
	 *             when SQLException occurs.
	 */
	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (stmt != null && !stmt.isClosed()) {
			stmt.close();
		}
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	/**
	 * Fill in the wild-cards with attribute in the Object[] and Execute SQL query,
	 * returns the result set. Note that you do have to close the resources by
	 * calling our closeAll function.
	 * 
	 * @param preparedSql:
	 *            raw SQL statements with wild-cards.
	 * @param param:
	 *            parameters to fill in the wild-cards.
	 * 
	 * @return ResultSet: SQL querying results.
	 * 
	 * @throws ClassNotFoundException:
	 *             when the driver class is not found.
	 */
	public static ResultSet executeQuery(PreparedStatement preparedSql, Object[] param) throws ClassNotFoundException {

		ResultSet resultSet = null;

		/* deal with and execute SQL */
		try {
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					preparedSql.setObject(i + 1, param[i]); // set parameters for prepared statement
				}
			}
			resultSet = preparedSql.executeQuery(); // execute the SQL expression
		} catch (SQLException e) {
			e.printStackTrace(); // handle SQLException
		}

		return resultSet;
	}
	
	/**
	 * Fill in the wild-cards with attribute in the Object[] and Execute SQL query,
	 * returns a boolean attribute. Note that you do not have to close the resources
	 * since they are automatically closed by us.
	 * 
	 * @param preparedSql:
	 *            raw SQL statements with wild-cards.
	 * @param param:
	 *            parameters to fill in the wild-cards.
	 * 
	 * @return boolean - flag that shows whether sql is executed successfully.
	 * 
	 * @throws ClassNotFoundException:
	 *             when the driver class is not found.
	 */
	public static boolean executeSql(String preparedSql, Object[] param) throws ClassNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		/* deal with and execute SQL */
		try {
			conn = getConnection(); // get database link from this class
			pstmt = conn.prepareStatement(preparedSql); // get PreparedStatement object
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]); // set parameters for prepared statement
				}
			}
			pstmt.execute(); // execute the SQL expression
			flag = true;
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace(); // handle SQLException
		} finally { // finally close and release resources.
			try {
				BaseDAO.closeAll(null, pstmt, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

}
