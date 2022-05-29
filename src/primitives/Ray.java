package primitives;

import java.util.List;
import java.util.stream.Collectors;

import geometries.Intersectable.GeoPoint;

/**
 * Class representing a ray
 * 
 * @author elana
 * @author elish
 */
public class Ray {

	public Point p0;
	public Vector dir;

	/**
	 * Amount to move the ray's head away from the geometry when making shadow rays
	 */
	private static final double DELTA = 0.1;

	/**
	 * constructor for ray
	 * 
	 * @param p1
	 * @param v1
	 */
	public Ray(Point p1, Vector v1) {
		dir = v1.normalize();
		p0 = p1;
	}

	/**
	 * constructor for ray:
	 * initializes fields
	 * and moves starting point delta above given starting point
	 * 
	 * @param point     - head of ray
	 * @param direction - direction of ray
	 * @param normal    - normal direction of geometry for adding delta
	 */
	public Ray(Point point, Vector direction, Vector normal) {
		dir = direction.normalize();
		// If projection of normal onto the ray is negative, we don't want it to
		// intersect the light, so we move the ray's head delta into the geometry.
		// If projection of normal onto the ray is positive, we want it to intersect the
		// light, so we move the ray's head delta out of the geometry.
		Vector deltaV = normal.dotProduct(dir) >= 0 ? normal.scale(DELTA) : normal.scale(-DELTA);
		p0 = point.add(deltaV);
	}

	/**
	 * methos that returns true if object and test object are equal
	 * 
	 * @param obj - of type Object
	 * @return a boolean value
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return p0.equals(other.p0) && dir.equals(other.dir);
	}

	public String toString() {
		return p0.toString() + dir.toString();
	}

	public Point getPoint(double t) {
		return p0.add(dir.scale(t));
	}

	/**
	 * Find closest point to the ray's origin
	 * 
	 * @param lst - a list of points
	 * @return a point that is the point closest to the rays head
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(
						points.stream().map(p -> new GeoPoint(null, p)).collect(Collectors.toList())).point;
	}

	/**
	 * Find closest geo intersection point to the ray's origin
	 * 
	 * @param lst - a list of geo points
	 * @return a geo point that is the closest to the ray
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
		// if the list is null, return null
		if (lst == null) {
			return null;
		}

		// closest point to the ray's origin found so far
		GeoPoint closestPoint = null;
		// distance squared to the closest point found so far
		double closestDistanceSquared = Double.MAX_VALUE;

		// for each GeoPoint, p, in the list
		for (GeoPoint p : lst) {
			double distanceSquared = p.point.distanceSquared(p0);
			if (distanceSquared < closestDistanceSquared) {
				closestPoint = p;
				closestDistanceSquared = distanceSquared;
			}
		}

		return closestPoint;
	}
}
