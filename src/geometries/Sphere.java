package geometries;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import primitives.*; 

public class Sphere implements Geometry {
	protected Point center;
	protected double radius;
	
	public Sphere(Point p1, double r1)
	{
		center = p1; 
		radius=r1;
	}
	
	@Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
	

	@Override
	public List<Point> findIntersections(Ray ray)
		{
			double tm, uLenSqrd, d, tn, t1, t2; 
			Point p1, p2; 
			//p = ray.p0.add(ray.dir); 
			Vector u = center.subtract(ray.p0); 
			tm = ray.dir.dotProduct(u); 
			uLenSqrd = u.lengthSquared(); 
			d = Math.sqrt(uLenSqrd - (tm*tm)); 
			if (d > radius)
			{
		        return null;
			}
			tn = Math.sqrt((radius*radius) - (d*d)); 
			t1 = tm + tn; 
			t2 = tm - tn; 
			p1 = ray.p0.add(ray.dir.scale(t1)); 
			p2 = ray.p0.add(ray.dir.scale(t2)); 
			if(t2 < 0d)
				return List.of(p1); 
			return List.of(p1, p2); 
		}
	}