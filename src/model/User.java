package model;

import java.util.List;

import de.fhluebeck.group3.model.Recipe;

/**
 * The User is mapped with User table in DataBase.
 * 
 * @author Jiaxiang Shan on 02/09/2020.
 */
public final class User {
	
	/**
	 * user ID as primary key in DB.
	 */
	private Integer userId;

	/**
	 * userName -> unique in database
	 */
	private String username;

	/**
	 * encrypted password.
	 */
	private String password;
	
	/**
	 * Status of user: 1 for valid 0 for deleted.
	 */
	private Integer status;

	/** ==============Constructors============== */
	/**
	 * Default constructor.
	 */
	public User() {
		super();
	}
	
	/**
	 * Constructor with necessary(create) attributes, with password automatically
	 * encrypted.
	 * 
	 * @param username
	 *            userName of the user.
	 * 
	 * @param password
	 *            password of the user.
	 * 
	 */
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.status = 1;
	}
	
	/**
	 * Constructor with all attributes, with password automatically encrypted.
	 * 
	 * @param userId
	 *            ID of the user.
	 * 
	 * @param username
	 *            userName of the user.
	 * 
	 * @param password
	 *            password of the user.
	 * 
	 * @param status
	 *            status of the user.
	 * 
	 */
	public User(Integer userId, String username, String password, Integer status) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.status = status;
	}
	
	/** ==============Getters and setters.============== */

	/**
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            id of the user.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            name of the user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            password of the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            status of the user.
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Override toString method, print basic information of a user.
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", status=" + status
				+  "]";
	}
}
