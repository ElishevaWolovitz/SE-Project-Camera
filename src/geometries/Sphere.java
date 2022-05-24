package geometries;

import java.util.List;
import java.util.LinkedList;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.isZero;
import static primitives.Util.alignZero;

public class Sphere extends Geometry {
	protected Point center;
	protected double radius;

	@Override
	public Vector getNormal(Point point) {
		return point.subtract(center).normalize();
	}

	/**
	 * Constructor
	 * 
	 * @param p1 center
	 * @param r1 radius
	 */
	public Sphere(Point p1, double r1) {
		center = p1;
		radius = r1;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		double tm, uLenSqrd, d, tn, t1, t2;
		Point p1, p2;

		// if the center of the sphere
		// and the origin of the ray are the same
		// return the point that's the distance 'radius' from the origin
		if (center.equals(ray.p0)) {
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
		}

		// vector from the ray origin to the center of the sphere
		Vector u = center.subtract(ray.p0);
		// projection of u onto the ray's direction
		// gives you the distance to the point
		// between the two points of intersection
		tm = ray.dir.dotProduct(u);

		// find the distance from the center of the sphere to the midpoint
		// of the solutions
		uLenSqrd = u.lengthSquared();
		d = Math.sqrt(uLenSqrd - (tm * tm));

		if (d > radius) {

			return null;
		}
		// tn is the distance from the midpoint to
		// either solution
		tn = Math.sqrt((radius * radius) - (d * d));
		// if the distance is zero, it hit a tangent of the sphere
		if (isZero(tn)) {
			return null;
		}
		// starting with an empty list with no solutions
		List<GeoPoint> intersections = null;
		t1 = tm + tn;
		t2 = tm - tn;
		// if t1 is in front of the ray
		if (alignZero(t1) > 0) {
			p1 = ray.getPoint(t1);
			intersections = new LinkedList<>(List.of(new GeoPoint(this, p1)));
		}

		// if t2 is in front of the ray
		if (alignZero(t2) > 0) {
			p2 = ray.getPoint(t2);
			if (intersections == null) {
				intersections = List.of(new GeoPoint(this, p2));
			} else {
				intersections.add(new GeoPoint(this, p2));
			}
		}
		return intersections;
	}
}