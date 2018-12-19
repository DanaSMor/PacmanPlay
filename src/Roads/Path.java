package Roads;

import java.util.ArrayList;
import java.util.Iterator;

import Gameboard.Fruit;
import Geom.Point3D;
import Coords.MyCoords;
/**
 * 
 * @author Dana Mor & Or avital
 *	this class represent path of a pacman
 */
public class Path {

	private ArrayList<PathData> path;
	private double lengthOfPath;
	private double weightOfFruits;
	private double totalTime;

	/**
	 * 
	 * @param p - start point of the path
	 */
	public Path(Point3D p, int fruitId) 
	{
		path = new ArrayList<PathData>();
		PathData pd = new PathData(p, 0, fruitId);
		path.add(pd);	
		lengthOfPath=0;
		weightOfFruits=0;
		totalTime=0;		
	}	
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	/**
	 * 
	 * @param fu - fruit
	 * @param time - time when the fruit was eaten
	 */
	public void add (Fruit fu, double time)
	{
		totalTime=totalTime+time;
		Fruit f = new Fruit(fu);
		path.add(new PathData(f.getPoint(), totalTime,f.getId()));	
		MyCoords mc = MyCoords.myCoords();
		if(path.size()>1)
		{
			lengthOfPath=lengthOfPath+mc.distance3d(path.get(path.size()-2).getPoint(), path.get(path.size()-1).getPoint());
		}
		weightOfFruits=weightOfFruits+f.getAttribute();

	}

	public ArrayList<PathData> getRoad() {
		return path;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public String getTotalTimeFormat() {
		String timeF="";		
		//***set the time format****//
		int seconds = (int)this.totalTime; 
		int p1 = seconds % 60;
		int p2 = seconds / 60;
		int p3 = p2 % 60;
		p2 = p2 / 60;

		String sec;
		String min;
		String hour;

		if(p2==0)
			hour="00:";
		else if(p2<10)
			hour ="0"+p2+":";
		else
			hour=""+p2+":";

		if(p3==0)
			min="00:";
		else if(p3<10)
			min ="0"+p3+":";
		else
			min=""+p3+":";

		if(p1==0)
			sec="00";
		else if(p1<10)
			sec ="0"+p1;
		else
			sec=""+p1;

		timeF=hour+min+sec;
		return timeF;

	}

	public double getLengthOfPath() {
		return lengthOfPath;
	}

	public double getWeightOfFruits() {
		return weightOfFruits;
	}

	public Iterator<PathData> getIterator()
	{
		return path.iterator();
	}
}
