package Gameboard;

import Geom.Point3D;

public class Character {

	private int id;
	private double attribute; // Represent Speed for Pacman , Weight for fruit
	private Point3D point; // Geographic point
	
	/**
	 * This class represent a Characters of Pacman, Fruit
	 * and all their common qualities
	 * @param point
	 * @param id
	 * @param attribute
	 */
	public Character(Point3D point ,int id, double attribute) {
		this.id = id;
		this.attribute = attribute;
		this.point = point;
	}
	
	public Point3D getPoint() {
		return point;
	}
	public void setPoint(Point3D point) {
		this.point = point;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAttribute() {
		return attribute;
	}

	
}
