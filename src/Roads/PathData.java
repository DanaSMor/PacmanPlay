package Roads;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Geom.Point3D;
/**
 * 
 * @author Dana Mor & or avital
 *this class represent the meta data of each point in the path
 */
public class PathData {

	private Point3D point;
	private int id;
	private double time;
	private Color color;
	
	/**
	 * 
	 * @param p- point
	 * @param time - time
	 */
	public PathData(Point3D p, double time, int id)
	{
		this.point=p;
		this.time=time;
		this.color = new Color((int)(Math.random() * 0x1000000));

		this.id=id;

		this.id = id;

	}

	public int getId() {
		return id;
	}

	public Point3D getPoint() {
		return point;
	}
	public double getTime() {
		return time;
	}

	/**
	 * 
	 * @return time of the format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	public String getTimeFormat() {

		String timeF="";
		String dateF="";
		//***set the date of today****//
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		dateF = LocalDate.now().toString()+"T";		
		//***set the time format****//
		int seconds = (int)this.time; 
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
		else if(p2<10)
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
		return dateF+timeF+"Z";
	}

	public Color getColor() {
		return color;
	}


}
