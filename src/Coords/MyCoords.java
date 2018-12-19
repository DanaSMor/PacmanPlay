package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter {

	//Earth’s radius, sphere
	private final int R=6371*1000;//6378137;
	
	private MyCoords() {
	}
	
	private static MyCoords singleInstance = null;
	
	public static MyCoords myCoords() {
		if(singleInstance==null) {
			singleInstance = new MyCoords();
		}
		return singleInstance;
	}
	
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

		//checks if the points are equal
		if(!isValid_GPS_Point(gps))
			throw new ArithmeticException("invalid GPS point");
		//Position, decimal degrees
		double plat = gps.x();
		double plon = gps.y();

		//vector coordinates in meters
		double vlat = local_vector_in_meter.x();
		double vlon = local_vector_in_meter.y();

		double lonNorm= Math.cos(plat*Math.PI/180)*R;

		vlat=vlat/R;		

		double x= Math.asin(vlat)*180/Math.PI+plat;
		double y= Math.asin((vlon/lonNorm))*(180/Math.PI)+plon;

		//prevent from exit the world
		if(y>180)
			y=-(180-y%180);
		
		Point3D P=new Point3D(x, y, gps.z()+local_vector_in_meter.z());//returns the new point
		
		if(!isValid_GPS_Point(P))
			throw new ArithmeticException("invalid GPS point");
		
		return P;
	}

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			throw new ArithmeticException("invalid GPS point");
		Point3D vec=vector3D(gps0, gps1);
		double x = vec.x();
		double y = vec.y();
		double z = vec.z();
		double dis = Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2);
		return(Math.pow(dis, 0.5));

	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			throw new ArithmeticException("invalid GPS point");
		double latx0 = gps0.x();
		double latx1 = gps1.x();

		double lonx0 = gps0.y();
		double lonx1 = gps1.y();

		double altx0 = gps0.z();
		double altx1 = gps1.z();

		double diffRadlat = latx1-latx0;
		double diffRadlon = lonx1-lonx0;
		double diffRadalt = altx1-altx0;

		//radian convert
		diffRadlat=diffRadlat*Math.PI/180;
		diffRadlon=diffRadlon*Math.PI/180;

		//meter converter
		double x = Math.sin(diffRadlat)*R;
		double y = Math.sin(diffRadlon)*R* Math.cos(gps0.x()*Math.PI/180);
		Point3D vector=new Point3D(x, y, diffRadalt);//returns the new point
		return vector;

	}
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			throw new ArithmeticException("invalid GPS point");
		
		//Point3D vec=new Point3D(gps0.x(),gps0.y(),gps0.z());
		Point3D vec=vector3D(gps0, gps1);
		
		double azimut=Math.toDegrees(Math.atan(Math.abs(vec.y()/vec.x())));;
		if(vec.x()<0)
		{
			if(vec.y()>0)
				azimut=180-azimut;

			else
				azimut=180+azimut;
		}
		else
		{
			if(vec.y()<0)
				azimut=360-azimut;
		}

		double distance = distance3d(gps0,gps1);

		double eleveation = Math.toDegrees(Math.asin(vec.z()/distance));
		 
		double arr[] = {azimut,eleveation, distance};
		return arr;
	}

	@Override
	//check if a point is valid
	public boolean isValid_GPS_Point(Point3D p) {
		if(p.x()<=90 && p.x()>=-90 & p.y()<=180 && p.y()>=-180 && p.z()>=-450) return true; 
		return false;
	}

}
