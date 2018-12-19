package Jtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import File_format.Csv2Elem;
import Gameboard.Fruit;
import Gameboard.Game;
import Gameboard.Pacman;
import Geom.Point3D;

class Csv2ElemTest {

	@Test
	void testCsv2Elem() {
		try {
			Csv2Elem test = new Csv2Elem(new File("TestFiles\\simpleGame.csv"));
		}
		catch(Exception e) {
			fail("Should not get Exception!!");
		}

	}

	@Test
	void testMakeElements() {
		// Test create elements from good CSV
		File f = new File("TestFiles\\simpleGame.csv");
		Game game = null;
		Csv2Elem c = new Csv2Elem(f);
		try {
			game = c.MakeElements();
		}
		catch(Exception e) {
			fail("Should not get exception!");
		}

		// Test correct data
		assertEquals(3, game.numOfFruits());
		assertEquals(1, game.numOfpacmans());
		Point3D check[] = {new Point3D(32.1045513,35.2035022),new Point3D(32.10462702,35.20573393), 
				new Point3D(32.10478793 ,35.20498036),new Point3D(32.10458916	,35.20411086)};

		ArrayList<Pacman> pa = game.getPacmanArray();
		ArrayList<Fruit> fr = game.getFruitArray();
		
		Point3D P = pa.get(0).getPoint();
		assertTrue(P.x()==check[0].x() && P.y()==check[0].y());
		
		for(int i=1; i<check.length; i++) {
			Point3D F = fr.get(i-1).getPoint();
			assertTrue(F.x()==check[i].x() && F.y()==check[i].y());
		}

}
	
}
