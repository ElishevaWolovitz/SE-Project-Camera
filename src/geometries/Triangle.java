package geometries;

import java.util.LinkedList;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Triangle extends Polygon {

	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> planeIntersections = this.plane.findGeoIntersectionsHelper(ray);

		// if ray does not intersect with the plane the triangle lies on
		// it does not intersect with the triangle either
		if (planeIntersections == null)
			return null;

		double d1, d2, d3;
		Vector v1, v2, v3, n1, n2, n3;
		v1 = vertices.get(0).subtract(ray.p0);
		v2 = vertices.get(1).subtract(ray.p0);
		v3 = vertices.get(2).subtract(ray.p0);
		n1 = v1.crossProduct(v2).normalize();
		n2 = v2.crossProduct(v3).normalize();
		n3 = v3.crossProduct(v1).normalize();
		// storing the intersection point in a variable
		Point intersectionPoint = planeIntersections.get(0).point;

		// vector from the ray's origin to the intersection point on the plane
		Vector toTriangle = intersectionPoint.subtract(ray.p0);
		d1 = toTriangle.dotProduct(n1);
		d2 = toTriangle.dotProduct(n2);
		d3 = toTriangle.dotProduct(n3);

		// need to check that d1,d2 and d3 are all positive or all negative
		if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)) {
			return List.of(new GeoPoint(this, intersectionPoint));
		}

		return null;

	}
}
