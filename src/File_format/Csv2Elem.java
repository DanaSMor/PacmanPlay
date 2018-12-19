package File_format;

import java.io.File;
import java.util.ArrayList;

import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;


/**
 * 
 * This class convert CSV data to java objects 
 *
 */
public class Csv2Elem {
	private int rows;
	private Game game;
	private ArrayList<ArrayList<String>> arr;

	public Csv2Elem(File entry) {
		CsvReader  r = new CsvReader(entry.toString()); // Call to CsvReader class with correct File Path 
		rows = r.get_rows();
		arr = r.get_Array(); // Save arr
		game = new Game();
	}

	/**
	 * Running on the String array that we got from CsvReader class and 
	 * finally add it to the layer array
	 * 
	 * @return
	 */
	public Game MakeElements() {
		for(int i=0; i<rows-1; i++) {
			// Check if it's a Pacman or Fruit
			// and call correct method
			if(arr.get(i).get(0).toUpperCase().contains("P")) { 
				Pacman pc = MakePacman(arr.get(i));
				game.addPacman(pc); // finally add it
			}
			else {
				Fruit f = MakeFruit(arr.get(i));
				game.addFruit(f);  // finally add it
			}
		}
		return game;
	}

	/**
	 * Putting in the appropriate places the data that we receive
	 * from the String array
	 * finally return element
	 * @param arr
	 * @return
	 */
	private Pacman MakePacman(ArrayList<String> arr) {
		// Put in the correct places accord the csv format game
		int id = convert2Int(arr.get(1));
		double x = convert2Double(arr.get(2));
		double y = convert2Double(arr.get(3));
		double z = convert2Double(arr.get(4));
		double speed = convert2Double(arr.get(5));
		double radius = convert2Double(arr.get(6));

		Point3D p = new Point3D(x,y,z);

		Pacman pc = new Pacman(p, id, speed, radius);

		return pc;
	}

	private Fruit MakeFruit(ArrayList<String> arr) {
		// Same as MakePacman
		int id = convert2Int(arr.get(1));
		double x = convert2Double(arr.get(2));
		double y = convert2Double(arr.get(3));
		double z = convert2Double(arr.get(4));
		double weight = convert2Double(arr.get(5));

		Point3D p = new Point3D(x,y,z);
		Fruit f = new Fruit(p, id,weight);
		
		return f;
	}

	/**
	 * Parse to int
	 * @param s
	 * @return
	 */
	private int convert2Int(String s) {
		int x = Integer.parseInt(s);
		return x;
	}

	/**
	 * Parse to double
	 * @param s
	 * @return
	 */
	private double convert2Double(String s) {
		double x = Double.parseDouble(s);
		return x;
	}


}
