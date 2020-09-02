package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The EnergyGame Model as an Application.
 * 
 * @author Jiaxiang Shan on 02/09/2020.
 */
public class EnergyGame {
	private List<GameResult> gameResults;
	
	private String name;
	
	/**
	 * Default Constructor.
	 */
	public EnergyGame() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the cook book.
	 */
	public EnergyGame(String name) {
		this.name = name;
		this.gameResults = new ArrayList<>();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the energy game.
	 * 
	 * @param recipes
	 *            all the recipes that the application contains.
	 */
	public EnergyGame(List<GameResult> gameResults, String name) {
		super();
		this.gameResults = gameResults;
		this.name = name;
	}
		
}
