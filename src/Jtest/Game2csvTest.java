package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import File_format.Game2csv;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

class Game2csvTest {

	@Test
	void testGame2csv() {
		
		//create new game
		Game g = new Game();
		Point3D point = new Point3D(1, 2, 3);
		int weight =1,radius = 1;
		
		Fruit f = new Fruit(point, 2, weight);
		Pacman pacman= new Pacman(point, 2, 2, radius);
		
		g.addFruit(f);
		g.addPacman(pacman);
		Game2csv g2 = new Game2csv(g,"C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\gameC");
		
		File file = new File("C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\gameC.csv");
		// Check file exported
		assertTrue(file.exists());
	}

}
