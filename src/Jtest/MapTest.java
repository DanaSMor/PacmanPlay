package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Coords.Map;
import Geom.Point3D;

class MapTest {
	Map map = Map.map();
	private final Point3D max = new Point3D(32.10569,35.21236);
	private final Point3D min = new Point3D(32.1019,35.20238);

	@Test
	void testMap() {
		try {
			Map.map();
		}
		catch(Exception e) {
			fail("Should not fail!!");
		}
	}

	@Test
	void testPixel2coord() {
		for(int i=0; i<100; i++) { // Let's check on frame 600*600
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);
			
			Point3D c = map.pixel2coord(new Point3D(x,y), 600, 600);
			assertTrue(c.x()>=min.x() && c.x()<=max.x() || c.y()>=min.y() && c.y()<=max.y());
		}
		Point3D c = map.pixel2coord(new Point3D(0,0), 600, 600); // Check min edge coords(0,0)
		assertTrue(max.x()==c.x() && min.y()==c.y());
		
		c = map.pixel2coord(new Point3D(600,600), 600, 600); // Check max edge coords(600,600)
		assertTrue(min.x()==c.x() && max.y()==c.y());
		
	}

	@Test
	void testCoord2pixel() {
		for(int i=0; i<100; i++) { 
			int x = (int)(Math.random()*600);
			int y = (int)(Math.random()*600);
			
			Point3D c = map.coord2pixel(new Point3D(x,y), 600, 600);
			assertTrue(c.x()>=min.x() && c.x()<=max.x() || c.y()>=min.y() && c.y()<=max.y());
		}
	}

	@Test
	void testNormalize() {
		fail("Not yet implemented");
	}

	@Test
	void testConvert() {
		fail("Not yet implemented");
	}

	@Test
	void testDistanceGpsPixles() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMap() {
		fail("Not yet implemented");
	}

}
