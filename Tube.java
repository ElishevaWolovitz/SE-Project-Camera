package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point; 

public class Tube {
protected Ray axisRay;
protected double radius;

public Vector getNormal(Point p1)
{
	return null;
}

public Tube(Ray ray, double r1)
{
	axisRay = ray; 
	radius=r1;
}
}
