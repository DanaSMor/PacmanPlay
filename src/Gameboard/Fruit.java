package Gameboard;

import Geom.Point3D;

/**
 * This class represents a Fruit Character
 * with Geographic point,id and weight
 *
 */
public class Fruit extends Character { 
	public boolean destroyed; // when the fruit is eaten

	public Fruit(Point3D point, int id, double weight) {
		super(point,id, weight);
		destroyed = true;
	}
	
	/**
	 * copy constructor
	 * @param f - fruit
	 */
	public Fruit(Fruit f)
	{
		super(f.getPoint(),f.getId(),f.getAttribute());
	}

}
