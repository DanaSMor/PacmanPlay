package Gui;

import java.util.Iterator;

import Coords.Map;
import Gameboard.Pacman;
import Geom.Point3D;
import Roads.PathData;

/**
 * Responsible to update points of a single Pacman
 * accord path direction that solve by ShortestPathAlgo
 * Class extends thread so can work simultaneously
 *
 */
public class Animate extends Thread  {
	private MyFrame frame; // Use Gui frame for repaint the frame
	private Pacman pacman;
	private AliveThread AT;

	public Animate(MyFrame frame, Pacman pacman ,AliveThread AT) {
		this.frame = frame;
		this.pacman = pacman;
		this.AT = AT;
	}
	
	@Override
	public void run() {
		Point3D originalPoint; // Save origin point of pacman
		Map map = Map.map(); // For calculate
		Iterator<PathData> it = pacman.getPath().getIterator();

		PathData data = it.next(); // Get pacman data
		Point3D current = originalPoint = data.getPoint();

		// Variable such that timeStart - since begging of game
		// Seconds - past since current time until target time
		// Xp & Yp - final point to x & y
		// EndTime - of targetpoint
		double timeStart = 0, seconds, TPrecent, xP, yP , EndTime; 
		
		while(it.hasNext()) { // Move all the path
			data = it.next();

			Point3D targetPoint = data.getPoint();
			EndTime = data.getTime();
			seconds = 0;
			Point3D StartPoint = current;
			while(timeStart+seconds < EndTime) {

				TPrecent = map.normalize(timeStart+seconds, EndTime, timeStart); // Caclulate time ratio

				//calculate the new move point
				xP = map.convert(TPrecent, targetPoint.x(), StartPoint.x()); 
				yP = map.convert(TPrecent, targetPoint.y(), StartPoint.y());
				
				current = new Point3D(xP,yP);
				pacman.setPoint(current); // Set the new point
				seconds+=1.0; // Draw each 1 second

				frame.update(); // Draw it on gui
				try {
					Thread.sleep(6); // For normal moving
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			timeStart = EndTime;
			current = targetPoint;
			AT.removeFruit(data.getId()); // Remove the Fruit that we past
			frame.update();
		}

		pacman.setPoint(originalPoint); // Return to original point
		frame.update(); 
		AT.Alive(); // Mark as finish
	}

}
