package Gameboard;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author Dana Mor and Or Avital
 * this class represent a game - a collections of Pacmans and fruits.
 */
public class Game {
	private ArrayList<Fruit> fruitArray;
	private ArrayList<Pacman> pacmanArray;
	private int maxIdPacman, maxIdFruit;

	private PacmanComperator compare = new PacmanComperator();

	public Game()
	{
		fruitArray = new ArrayList<Fruit>();
		pacmanArray = new ArrayList<Pacman>();
		maxIdPacman=maxIdFruit=0;
	}

	/**
	 * 
	 * @param f  -fruit
	 */
	public void addFruit(Fruit f)
	{
		if(f.getId()>maxIdFruit) maxIdFruit = f.getId();
		fruitArray.add(f);
	}
/**
 * 
 * @param p - pacman
 */
	public void addPacman(Pacman p)
	{
		if(p.getId()>maxIdPacman) maxIdPacman = p.getId();
		pacmanArray.add(p);
		pacmanArray.sort(compare);
	}
/**
 * 
 * @return  number Of Fruits
 */
	public int numOfFruits()
	{
		return fruitArray.size();
	}
/**
 * 
 * @return number Of Pacman
 */
	public int numOfpacmans()
	{
		return pacmanArray.size();
	}
/**
 * 
 * @return pacmanArray iterator
 */
	public Iterator<Pacman> getPacmanIterator()
	{
		return pacmanArray.iterator();
	}
/**
 * 
 * @return fruitArray iterator
 */
	public Iterator<Fruit> getFruitIterator()
	{
		return fruitArray.iterator();
	}
/**
 * 
 * @return the array of the fruits
 */
	public ArrayList<Fruit> getFruitArray() {
		return fruitArray;
	}
/**
 * 
 * @return the array of the pacmans
 */
	public ArrayList<Pacman> getPacmanArray() {
		return pacmanArray;
	}
/**
 * 
 * @return the id of the pacman
 */
	public int getMaxIdPacman() {
		return maxIdPacman;
	}
	/**
	 * 
	 * @return the id of the fruit
	 */	
	public int getMaxIdFruit() {
		return maxIdFruit;
	}
	
}
