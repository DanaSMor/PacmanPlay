package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Coords.Map;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

class GameTest {
	static Game game;
	Map map = Map.map();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		game = new Game();
	}

	@Test
	void testAddFruit() {
		//Point3D point = new Point3D(1, 2, 3);
		int weight =1, id=1;
		for(int i=1; i<20; i++) { // Let's check on frame 600*600
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);

			Point3D point = map.pixel2coord(new Point3D(x,y), 600, 600);
			Fruit f = new Fruit(point, id, weight);
			try 
			{
				game.addFruit(f);
			} 
			catch (Exception e) 
			{
				fail("should not fail!");
			}
		}

	}

	@Test
	void testAddPacman() {
		int radius = 1, speed =1, id=1;
		for(int i=0; i<20; i++) { // Let's check on frame 600*600
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);

			Point3D point = map.pixel2coord(new Point3D(x,y), 600, 600);
			try {
				Pacman pacman= new Pacman(point, id, speed, radius);
				game.addPacman(pacman);
			} 
			catch (Exception e) 
			{
				fail("should not fail!");
			}
		}

	}

	@Test
	void testNumOfpacmans() {
		int radius = 1, speed =1, id=1;
		for(int i=1; i<20; i++) { // Let's check on frame 600*600
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);

			Point3D point = map.pixel2coord(new Point3D(x,y), 600, 600);
			Pacman pacman= new Pacman(point, id, speed, radius);
			game.addPacman(pacman);
			assertEquals(game.numOfpacmans(), i);
		}

	}

	@Test
	void testNumOffruits() {
		game = new Game();
		int weight =1, id=1;
		for(int i=1; i<20; i++) { // Let's check on frame 600*600
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);

			Point3D point = map.pixel2coord(new Point3D(x,y), 600, 600);
			Fruit f = new Fruit(point, id, weight);
			game.addFruit(f);
			assertEquals(game.numOfFruits(), i);
		}
	}




}
