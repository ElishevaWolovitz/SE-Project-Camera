

package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point; 


public class Tube {
	protected Ray axisRay;
	protected double radius;

	public Tube(Ray ray, double r1)
	{
		axisRay = ray; 
		radius=r1;
	}

	public Vector getNormal(Point point)
	{
		double scalar = axisRay.dir.dotProduct(point.subtract(axisRay.p0));
		// find where the point ray projects onto the axis ray
		Point closest = axisRay.p0.add(axisRay.dir.scale(scalar));
		return point.subtract(closest).normalize();
	}
}



/*package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point; 

public class Tube {
protected Ray axisRay;
protected double radius;

public Vector getNormal(Point p1)
{
	Vector v1,v2, v3, v4; 
	double t; 
	Point p2; 
	
	v1 = p1.subtract(axisRay.p0); 
	t = v1.dotProduct(axisRay.dir); 
	v2 = axisRay.dir.scale(t);
	p2= axisRay.p0.add(v2);
	v3 = p1.subtract(p2); 
	v4 = v3.normalize(); 
	return v4; 
}

public Tube(Ray ray, double r1)
{
	axisRay = ray; 
	radius=r1;
}
}*/
