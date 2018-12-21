package Coords;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * This class represent a Frames conversion with normalize
 * and holding a specific map
 * @author Or Avital & Dana Mor
 *
 */
public class Map {
	// final coords frame
	private final Point3D max = new Point3D(35.21236,32.10569);
	private final Point3D min = new Point3D(35.20238,32.1019);

	private BufferedImage myImage;

	/**
	 * a Private construtor map
	 * to create only one
	 */
	private Map() {
		
		try {
			myImage = ImageIO.read(new File("Icon\\Ariel1.png")); // Get map from path
		} catch (IOException e) {
			new IOException("Error unable read map!!! please try again");
		}

	}
	
	/**
	 * Static function create new Map class
	 * if necessary
	 * @return
	 */
	public static Map map() {
		if(singleInstance==null) { // Check if exist 
			singleInstance = new Map(); // Create new one if isn't
		}
		return singleInstance;
	}
	
	private static Map singleInstance=null; // Save Map class if opened

	
	/**
	 * Convert a pixel Points to gps Coords
	 * keep the value of ratio
	 * @param c
	 * @param maxX
	 * @param maxY
	 * @return
	 */
	public Point3D pixel2coord(Point3D c,int maxX, int maxY) {
		double xPrecent = normalize(c.x(),maxX,0); // Calculate normal for x
		double transX = convert(xPrecent,max.x(),min.x()); // Translate x to the new ranges

		double yPrecent = normalize(c.y(),maxY,0); // Calculate normal for y
		double 	transY = convert(yPrecent,max.y(),min.y()); // Translate y to the new ranges

		double gap = max.y()-transY;  // Get opposite point of y

		transY = min.y()+gap; // add it
		
		return new Point3D(transY,transX); // Return the Points in the new ranges with the same ratio ;
	}

	/**
	 * Convert a gps Coords to a pixles Points
	 * keep the value of ratio
	 * @param c
	 * @param maxX
	 * @param maxY
	 * @return
	 */
	public Point3D coord2pixel(Point3D c,int maxX, int maxY) {
		c = new Point3D(c.y(),c.x()); // Replace between x & y for convenience
		double xPrecent = normalize(c.x(),max.x(),min.x()); // Calculate normal for x
		double yPrecent = normalize(c.y(),max.y(),min.y()); // Calculate normal for y
		
		double transX = convert(xPrecent,maxX,0); // Translate x to the new ranges
		double transY = convert(yPrecent,maxY,0); // Translate y to the new ranges

		
		if(yPrecent>=0.5) { // Check if the points is above the axis
			double gap = maxY-transY; 
			transY = gap; // Calculate with the new Gap
		}
		else { // Else under axis
			double gap = transY;
			transY = maxY-gap; // Calculate with the new Gap
		}

		return new Point3D(transX, transY);  // return the new Points
	}
	
	/**
	 * Normalizes values in ranges
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	public double normalize(double x,double max, double min) {
		return Math.abs((x-min)/(max-min)); //  return number between 0-1;
	}
	
	/**
	 * Covert to the new value in range by get Max & Min ranges
	 * @param precent
	 * @param max
	 * @param min
	 * @return
	 */
	public double convert(double precent, double max, double min) {
		return precent*(max-min)+min;
	}

	/**
	 * Getting a Pixles Points and Calculate the gps distance
	 * @param a
	 * @param b
	 * @param maxX
	 * @param maxY
	 * @return
	 */
	public double distanceGpsPixles(Point3D a, Point3D b,int maxX, int maxY) {
		a = pixel2coord(a,maxX,maxY); // Convert to gps Coords
		b = pixel2coord(b,maxX,maxY);

		MyCoords calc = MyCoords.myCoords(); 
		double result[] = calc.azimuth_elevation_dist(a, b); // Save the result
		return result[2];
	}
	
	/**
	 * Return a angle between 2 points
	 * @param a
	 * @param b
	 * @param maxX
	 * @param maxY
	 * @return
	 */
	public double anglePoints(Point3D a, Point3D b,int maxX, int maxY) {
		MyCoords calc = MyCoords.myCoords(); 
		double result[] = calc.azimuth_elevation_dist(a, b); // Save the result
		return result[0];
	}


	/**
	 * Return our image map
	 * @return
	 */
	public BufferedImage getMap() {
		return myImage;
	}
	
}
