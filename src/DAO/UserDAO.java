package DAO;

import java.sql.*;

import de.fhluebeck.group3.DAO.BaseDAO;
import de.fhluebeck.group3.DAO.RecipeDAO;
import model.User;
import util.EncryptUtil;

/**
 * UserDAO is major responsible for Data Access in User table, functions like
 * validate user are provided.
 * 
 * @author Jiaxiang Shan on 07/09/2020.
 */
public final class UserDAO {

	/**
	 * Validate the input credentials and return user object if exists.
	 * 
	 * @param username
	 *            user name
	 * @param password
	 *            password
	 * 
	 * @return User: entity object if user exists or null if user not exists.
	 */
	public static User validatePassword(String username, String password) {
		User user = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		if (username.trim().equals("") || password.trim().equals("")) {
			return user;
		}

		try {
			connection = BaseDAO.getConnection();
			password = EncryptUtil.MD5(password);
			String preparedSql = "SELECT * FROM user WHERE `username` = ? AND `password` = ? AND `status` = 1";
			pstmt = connection.prepareStatement(preparedSql);
			Object[] parameters = { username, password };
			resultSet = BaseDAO.executeQuery(pstmt, parameters);
			if (resultSet != null && resultSet.isBeforeFirst()) { // ensure that there are some data in result set.
				while (resultSet.next()) {
					user = new User();
					user.setUserId((Integer.valueOf(resultSet.getString("user_id"))));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setStatus(Integer.valueOf(resultSet.getString("status")));
				}
				
				// following function not required
				// also we have to fill its favorite recipes and ownRecipes Lists to make a full
				// user.
				// user.setFavoriteRecipes(RecipeDAO.getFavoritedRecipes(user.getUserId()));
			} else {
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // finally close and release resources.
			try {
				BaseDAO.closeAll(null, pstmt, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * Validate the input credentials and return user object if exists.
	 * 
	 * @param userId
	 *            user id.
	 * 
	 * @return whether the function is succeeded or not.
	 */
	public static boolean deleteUser(Integer userId) {
		boolean flag = false;
		try {
			String preparedSql = "UPDATE `user` SET `status` = 0 WHERE `user_id` = ?";
			Object[] parameters = { userId };
			flag = BaseDAO.executeSql(preparedSql, parameters);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * Get User by the input id.
	 * 
	 * @param userId
	 *            id of the user.
	 * 
	 * @return User object or null.
	 */
	public static User getUserById(Integer userId) {
		User user = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			connection = BaseDAO.getConnection();
			String preparedSql = "SELECT DISTINCT * FROM `user` WHERE `user_id` = ? AND `status` = 1";
			Object[] parameters = { userId };
			pstmt = connection.prepareStatement(preparedSql);
			resultSet = BaseDAO.executeQuery(pstmt, parameters);
			if (resultSet != null && resultSet.isBeforeFirst()) { // ensure that there are some data in result set.
				while (resultSet.next()) {
					user = new User();
					user.setUserId((Integer.valueOf(resultSet.getString("user_id"))));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setStatus(Integer.valueOf(resultSet.getString("status")));
				}
				// also we have to fill its favorite recipes and ownRecipes Lists to make a full
				// user.
			} else {
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * Validate the input credentials and return user object if exists.
	 * 
	 * @param user
	 *            the user information we want to insert.
	 * 
	 * @return User: whether the function is succeeded or not.
	 */
	public static boolean addUser(User user) {
		boolean flag = false;

		String passwordString = EncryptUtil.MD5(user.getPassword());

		// If user has already existed.
		if (user.getUserId() != null) {
			return false;
		}
		try {
			String preparedSql = "INSERT INTO `user` (`username`, `password`, `status`) VALUES (?, ?, ?)";
			Object[] parameters = { user.getUsername(), passwordString, user.getStatus() };
			flag = BaseDAO.executeSql(preparedSql, parameters);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * Unit test for UserDAO
	 * 
	 * @param args:
	 *            string from console input.
	 */
	public static void main(String[] args) {

		// User user = UserDAO.validatePassword("admin", "1234");
		// user = UserDAO.validatePassword("admin", "123");
		// System.out.println(user == null);
		// System.out.println(user); //success

		// deleteUser(2); //success

		// addUser(user);
		// addUser(new User("test5", "456"));

		// System.out.println(getUserById(3).getUsername());

	}
}
