package File_format;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import Gameboard.Fruit;
/**
 * @author Dana Mor and Or avital
 * this class convert game to csv
 */
import Gameboard.Game;
import Gameboard.Pacman;

public class Game2csv {

	private Game game;
	private String name;
	
	public Game2csv(Game game,String name)
	{
		this.game = game;
		this.name = name;
		csvBuilder();
	}

	private void csvBuilder() {
		StringBuilder sb= new StringBuilder();
		sb.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+game.numOfFruits()+","+game.numOfpacmans()+"\n");
		//***********add pacmans************//
		Iterator<Pacman> itP =game.getPacmanIterator();
		while(itP.hasNext()) 
		{
			Pacman current = itP.next(); // For each element convert it to KML tags
			sb.append("P,");
			sb.append(current.getId()+",");
			sb.append(current.getPoint().x()+",");
			sb.append(current.getPoint().y()+",");
			sb.append(current.getPoint().z()+",");
			sb.append(current.getAttribute()+",");
			sb.append(current.getRadius()+"\n");

		}
		//***********add fruits************//
		Iterator<Fruit> itF =game.getFruitIterator();
		while(itF.hasNext()) 
		{
			Fruit current = itF.next(); // For each element convert it to KML tags
			sb.append("F,");
			sb.append(current.getId()+",");
			sb.append(current.getPoint().x()+",");
			sb.append(current.getPoint().y()+",");
			sb.append(current.getPoint().z()+",");
			sb.append(current.getAttribute()+"\n");
		}
		
		//***********write the file************//
		try {
			PrintWriter pw = new PrintWriter(new File(name+".csv"));
			pw.write(sb.toString());
			pw.close(); // Export the file
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}


}
