package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;
import Coords.MyCoords;
class MyCoordsTest {
	
	private MyCoords mC =MyCoords.myCoords();

	@Test
	void testAdd() {
		Point3D x0= new Point3D(32.103315,35.209039,670);
		Point3D x1= new Point3D(32.106352,35.205225,650);
		Point3D vec= new Point3D(337.699,-359.249,-20);
		assertTrue(Math.abs(mC.add(x0,vec).x()-x1.x())<0.001);
		assertTrue(Math.abs(mC.add(x0,vec).y()-x1.y())<0.001);
		assertTrue(Math.abs(mC.add(x0,vec).z()-x1.z())<0.001);
	}

	@Test
	void testDistance3d() {
		Point3D x0= new Point3D(32.103315,35.209039,670);
		Point3D x1= new Point3D(32.106352,35.205225,650);
		double expDis =  493.4578;
		assertTrue(Math.abs(mC.distance3d(x0, x1)-expDis)<0.001);
	}

	@Test
	void testVector3D() {
		Point3D x0= new Point3D(32.103315,35.209039,670);
		Point3D x1= new Point3D(32.106352,35.205225,650);
		Point3D vec= new Point3D(337.699,-359.249,-20);
		assertTrue(Math.abs(mC.vector3D(x0,x1).x()-vec.x())<0.001);
		assertTrue(Math.abs(mC.vector3D(x0,x1).y()-vec.y())<0.001);
		assertTrue(Math.abs(mC.vector3D(x0,x1).z()-vec.z())<0.001);
	}

	@Test
	void testAzimuth_elevation_dist() {
		Point3D x0= new Point3D(32.103315,35.209039,670);
		Point3D x1= new Point3D(32.106352,35.205225,650);
		
		double [] expectedAED= {313.2304, -2.3228, 493.4578};
		System.out.println((mC.azimuth_elevation_dist(x0, x1)[0]-expectedAED[0]));
		assertTrue(Math.abs(mC.azimuth_elevation_dist(x0, x1)[0]-expectedAED[0])<0.01);
		assertTrue(Math.abs(mC.azimuth_elevation_dist(x0, x1)[1]-expectedAED[1])<0.01);
		assertTrue(Math.abs(mC.azimuth_elevation_dist(x0, x1)[2]-expectedAED[2])<0.01);
	}

	@Test
	void testIsValid_GPS_Point() {
		//valid points
		Point3D x0= new Point3D(32.103315,35.209039,670);
		Point3D x1= new Point3D(32.106352,35.205225,650);
		
		//invalid points
		Point3D inv_x2= new Point3D(32.103315,181,670);
		Point3D inv_x3= new Point3D(32.103315,-181,670);
		Point3D inv_x4= new Point3D(91,35.209039,670);
		Point3D inv_x5= new Point3D(-91,650,35.209039);
		Point3D inv_x6= new Point3D(90,35.209039,-451);

		//checks valid points
		assertTrue(mC.isValid_GPS_Point(x0));
		assertTrue(mC.isValid_GPS_Point(x1));
		
		//checks invalid points
		assertFalse((mC.isValid_GPS_Point(inv_x2)));
		assertFalse((mC.isValid_GPS_Point(inv_x3)));
		assertFalse((mC.isValid_GPS_Point(inv_x4)));
		assertFalse((mC.isValid_GPS_Point(inv_x5)));
		assertFalse((mC.isValid_GPS_Point(inv_x6)));

	}

}
