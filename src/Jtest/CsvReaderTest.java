package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import File_format.CsvReader;

class CsvReaderTest {
	
	CsvReader cR = new CsvReader("C:\\Users\\Dana Mor\\eclipse-workspace\\PacmanGame\\TestFiles\\simpleGame.csv");

	@Test
	void testCsvReader() {

		//System.out.println(cR.get_header());
	}

	@Test
	void testGet_header() {
		ArrayList<String> cRheader =cR.get_header();
		String [] header = {"Type","id", "Lat", "Lon", "Alt", "Speed/Weight", "Radius", "3", "12"};
		System.out.println(cR.get_header());
		for (int i = 0; i < header.length; i++) {
			assertEquals(cRheader.get(i), header[i]);
		}
	}


}
