package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Triangle extends Polygon {

	public Triangle(Point p1, Point p2, Point p3)
	{
		super(p1,p2,p3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> listIntersec = new LinkedList<Point>(); 
		if ((this.plane.findIntersections(ray)) == null)
				return null; 
		//if the distance between the point and 
		//all 3 vertices is less then the distance between the 3 vertices 
		//then the point lies in the triangle
		double PA, PB, PC, AB, BC, CA; 
		Point a, b, c; 
		a = vertices.get(0); 
		b = vertices.get(1); 
		c = vertices.get(2); 
		PA = ray.p0.distance(a); 
		PB = ray.p0.distance(b); 
		PC = ray.p0.distance(c); 
		AB = a.distance(b); 
		BC = b.distance(c); 
		CA = c.distance(a); 
		if((PA + PB + PC) < (AB+BC+CA))
		{
			listIntersec.add(ray.p0); 
			return listIntersec; 
		}
		else 
			return null; 
	}
}
