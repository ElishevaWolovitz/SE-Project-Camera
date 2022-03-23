package geometries;

import java.util.List;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {
	protected Point center;
	protected double radius;

    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
	
	public Sphere(Point p1, double r1)
	{
		center = p1; 
		radius=r1;
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}