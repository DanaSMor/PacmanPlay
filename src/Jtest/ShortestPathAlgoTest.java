package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import File_format.Csv2Elem;
import Gameboard.Game;
import Roads.ShortestPathAlgo;

class ShortestPathAlgoTest {
	static Game game;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File f = new File("C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\simpleGame.csv");
		Csv2Elem cs = new Csv2Elem(f);
		game = cs.MakeElements();
	}

	@Test
	void testShortestPathAlgo() {
		try
		{
			ShortestPathAlgo s = new ShortestPathAlgo(game);
		}
		catch (Exception e) {
			fail("Should not get Exception!");
		}
	}

	@Test
	void testGetTotalWeight() {
		ShortestPathAlgo s = new ShortestPathAlgo(game);
		assertEquals(s.getTotalWeight(), 3);
	}


	@Test
	void testGetRunningTimeS() {
		ShortestPathAlgo s = new ShortestPathAlgo(game);
		assertEquals(s.getRunningTimeS(), "00:03:32");
	}


}
