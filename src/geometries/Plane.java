package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
	protected Point q0;
	protected Vector normal;
	
	public Vector getNormal()
	{
		return this.normal;
	}
	
    public Vector getNormal(Point point)
	{
		return this.normal;
	}

	public Plane(Point p1, Point p2, Point p3)
	{
        if (p1.equals(p2) || p2.equals(p3) || p1.equals(p3)) {
            throw new IllegalArgumentException("No two point can be equal");
        }
        if (p2.subtract(p1).normalize().equals(p3.subtract(p2).normalize())) {
            throw new IllegalArgumentException("Points can't be on the same line");
        }
        this.q0 = p1;
        // (p2 - p1) x (p3 - p1)
		this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
	}
	
	public Plane(Point p1, Vector v1)
	{
		v1.normalize(); 
		this.normal = v1; 
		this.q0 = p1; 
	}
    // public List<GeoPoint> findGeoIntersections(Ray ray)
	// {
	// 	return null;
	// }
}




