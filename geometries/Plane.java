package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
	protected Point q0;
	protected Vector normal;
	
	public Vector getNormal()
	{
		return null;
	}
	
	public Plane(Point p1, Point p2, Point p3)
	{
		this.normal= getNormal(p1); 
		this.q0 = p1; 
	}
	
	public Plane(Point p1, Vector v1)
	{
		this.normal = v1; 
		this.normal.normalize();
		this.q0 = p1; 
	}
}
