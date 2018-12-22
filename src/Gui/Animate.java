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
	private long sprint;

	public Animate(MyFrame frame, Pacman pacman ,AliveThread AT, long sprint) {
		this.frame = frame;
		this.pacman = pacman;
		this.AT = AT;
		this.sprint = sprint;
	}
	
	@Override
	public void run() {
		Point3D originalPoint, targetPoint, StartPoint;
		Map map = Map.map(); // For calculate
		
		double angle;
		
		Iterator<PathData> it = pacman.getPath().getIterator();

		PathData data = it.next(); // Get pacman data
		Point3D current = originalPoint = data.getPoint(); // Save original point to back when process finish

		// Variable such that timeStart - since begging of game
		// Seconds - past since current time until target time
		// Xp & Yp - final point to x & y
		// EndTime - of targetpoint
		double timeStart = 0, seconds, TPrecent, xP, yP , EndTime; 
		
		while(it.hasNext() && AT.clear) { // Move all the path
			data = it.next();
			targetPoint = data.getPoint();
			EndTime = data.getTime();
			seconds = 0;
			StartPoint = current;
			
			angle = map.anglePoints(StartPoint, targetPoint, frame.getWidth(), frame.getHeight()); // Calculate pacman angle orientation
			pacman.setOrien(angle-90);

			while(timeStart+seconds < EndTime && AT.clear) {
				
				TPrecent = map.normalize(timeStart+seconds, EndTime, timeStart); // Caclulate time ratio

				//calculate the new move point
				xP = map.convert(TPrecent, targetPoint.x(), StartPoint.x()); 
				yP = map.convert(TPrecent, targetPoint.y(), StartPoint.y());
				
				current = new Point3D(xP,yP);
				pacman.setPoint(current); // Set the new point
				seconds+=1/10.0; // Draw every 1/10 second

				frame.update(); // Draw it on gui
				try {
					Thread.sleep(sprint); // For normal moving
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			timeStart = EndTime;
			current = targetPoint;
			AT.removeFruit(data.getId()); // Remove the fruit we've been through
			frame.update();
		}
		pacman.setPoint(originalPoint); // Return to original point
		pacman.setOrien(0);
		
		frame.update(); 
		AT.Alive(); // Mark as finish
	}

}
