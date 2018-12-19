package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import File_format.Game2kml;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

class Game2kmlTest {

	@Test
	void testGame2kml() {
		//create new game
		Game g = new Game();
		Point3D point = new Point3D(1, 2, 3);
		int weight =1,radius = 1;
		
		Fruit f = new Fruit(point, 2, weight);
		Pacman pacman= new Pacman(point, 2, 2, radius);
		
		g.addFruit(f);
		g.addPacman(pacman);
		//Game2csv g2 = new Game2csv(g,"C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\game");
		Game2kml g2k = new Game2kml(g,"C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\gameK");
		
		File file = new File("C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\gameK.kml");
		// Check file exported
		assertTrue(file.exists());
	}

}
