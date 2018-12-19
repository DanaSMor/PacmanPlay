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
		Point3D pointF = new Point3D(32.1047478937381, 35.2036289192263, 3);
		Point3D pointP = new Point3D(32.104309203036, 35.2050594994311, 3);
		int weight =1,radius = 1, id=1, speed=3;
		
		Fruit f = new Fruit(pointF,id , weight);
		Pacman pacman= new Pacman(pointP, id, speed, radius);
		
		g.addFruit(f);
		g.addPacman(pacman);
		Game2kml g2k = new Game2kml(g,"C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanPlay\\TestFiles\\gameK");
		
		File file = new File("C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanPlay\\TestFiles\\gameK.kml");
		// Check file exported
		assertTrue(file.exists());
	}

}
