package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import File_format.Csv2Elem;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

class GameTest {
	static Game game;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		game = new Game();
	}

	@Test
	void testAddFruit() {
		Point3D point = new Point3D(1, 2, 3);
		int weight =1, id=1;
		Fruit f = new Fruit(point, id, weight);
		try {
			game.addFruit(f);
		} catch (Exception e) {
			fail("should not fail!");
		}
	}

	@Test
	void testAddPacman() {
		
		try {
			Point3D point = new Point3D(1, 2, 3);
			int radius = 1, weight =1, id=1;
			Pacman pacman= new Pacman(point, 2, 2, radius);
			game.addPacman(pacman);
		} catch (Exception e) {
			fail("should not fail!");
		}
	}

	@Test
	void testNumOfpacmans() {
		assertEquals(game.numOfpacmans(), 0);
	}



}
