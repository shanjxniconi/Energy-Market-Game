package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GameResult;

/**
 * GameResultDAO is major responsible for Data Access in GameResult table, functions
 * like addGameResult, getGameResult are provided.
 * 
 * @author Jiaxiang Shan on 07/09/2020.
 */
public final class GameResultDAO {

	/**
	 * This method is called after a new gameResult is inserted into the DB to retrieve
	 * the ID of the gameResult;
	 * 
	 * @param gameResult
	 *            specific gameResult.
	 * 
	 * @return the ID of the gameResult.
	 * 
	 */
	public static Integer getGameResultID(GameResult gameResult) {
		Integer gameResultID = 0;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = BaseDAO.getConnection();
			// SHOW RECIPE
			String preparedSql = "SELECT id FROM `gameresult` WHERE `username` = ? AND `score` = ?";
			pstmt = connection.prepareStatement(preparedSql);
			Object[] parameters = { gameResult.getUserName(), gameResult.getScore()};
			resultSet = BaseDAO.executeQuery(pstmt, parameters);
			if (resultSet != null && resultSet.isBeforeFirst()) { // ensure that there are some data in result set.
				while (resultSet.next()) {

					gameResultID = Integer.valueOf(resultSet.getString("id"));

				}

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

		return gameResultID;
	}
}
