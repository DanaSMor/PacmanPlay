package Roads;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import File_format.Csv2Elem;
import File_format.Game2csv;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

public class test {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Game g = new Game();
		Point3D p1 = new Point3D(1, 1, 3);
		Point3D p2 = new Point3D(2, 4, 3);
		Point3D p3 = new Point3D(3, 4, 3);
		Point3D p4 = new Point3D(4, 2, 3);
		
		Point3D point = new Point3D(1, 2, 3);
		int weight =1;
//		Fruit f1 = new Fruit(p1, 2, weight);
//		Fruit f2 = new Fruit(p2, 3, weight);
//		Fruit f3 = new Fruit(p3, 3, weight);
//		Fruit f4 = new Fruit(p4, 3, weight);
		int radius = 1;
		
		Point3D p5 = new Point3D(4, 4, 3);
		Point3D p6 = new Point3D(2, 1, 3);
		
		Pacman pacman1= new Pacman(p5, 2, 2, radius);
		Pacman pacman2= new Pacman(p6, 2, 2, radius);
		
//		g.addFruit(f1);
//		g.addFruit(f2);
//		g.addFruit(f3);
//		g.addFruit(f4);
		
		g.addPacman(pacman1);
		g.addPacman(pacman2);
//		Game2csv g2 = new Game2csv(g);
		
		Game game = new Game();
		
		Csv2Elem c = new Csv2Elem(new File ("Game.csv"));
		game = c.MakeElements();
//		ShortestPathAlgo s = new ShortestPathAlgo(g);
		ShortestPathAlgo s = new ShortestPathAlgo(game);
		ArrayList<Path> pa = s.getPath();
		System.out.println("size:"+pa.size());
		System.out.println(s.toString());

	}

}
