package Gui;

import java.util.ArrayList;

import Gameboard.Fruit;
import Gameboard.Game;

/**
 * This class is responsible for 
 * checking that the all Threads are finished
 * and popup a result score message to gui
 *
 */
public class AliveThread {

	private int Alives;
	private String TotalTime, totalWeight, TotalResult;
	private MyFrame frame;
	private ArrayList<Fruit> fruitArr;
	public boolean clear;

	public AliveThread(MyFrame frame) {
		this.frame = frame;
		clear = true;
	}

	/**
	 * Every Thread inside in when finish 
	 * and update our alives threds
	 * synchronized method so only thread can update in one time
	 */
	public synchronized void Alive() {
		Alives--; // Thread finish so minus 1
		if(Alives!=0) return;

		if(clear) // If player press 'clean' in gui, don't print result score
			frame.Result(TotalTime,totalWeight,TotalResult); // Send a result score message

		for(Fruit fruit : fruitArr) {
			fruit.destroyed = true;
		}
		
		frame.update();
	}

	/**
	 * When pass over the point(fruit) so remove it and don't print it
	 * synchronized method so only thread can update in one time
	 * @param P
	 */
	public synchronized void removeFruit(int id) {
		for(Fruit fruit : fruitArr) {
			if(fruit.getId()==id) {
				fruit.destroyed = false;
				return;
			}
		}
	}

	/**
	 * set a new session of game
	 * @param game
	 * @param TotalTime
	 * @param totalWeight
	 * @param TotalResult
	 */
	public void setNewThreads(Game game,String TotalTime,String totalWeight,String TotalResult) {
		fruitArr = game.getFruitArray();
		Alives = game.getPacmanArray().size();

		this.TotalTime = TotalTime;
		this.totalWeight = totalWeight;
		this.TotalResult = TotalResult;
		clear=true;
	}



}
