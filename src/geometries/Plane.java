package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Class representing a Plane
 * 
 * @author elana
 * @author elish
 */
public class Plane extends Geometry {
	protected Point q0;
	protected Vector normal;

	@Override
	public Vector getNormal(Point point) {
		return this.normal;
	}

	/**
	 * Constructor that takes 3 points
	 * 
	 * @param p1 point 1
	 * @param p2 point 2
	 * @param p3 point 3
	 */
	public Plane(Point p1, Point p2, Point p3) {
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

	/**
	 * Constructor that takes a point and a normal
	 * 
	 * @param p1 point
	 * @param v1 normal vector
	 */
	public Plane(Point p1, Vector v1) {
		v1.normalize();
		this.normal = v1;
		this.q0 = p1;
	}
	/**
	 * method calculates a list of geoPoints that a ray from the light source to the object intersects 
	 * @param a ray
	 * @param max distance - the distance between the object in question and the light source
	 * @return returns a list of geoPoints btwn the geometry and the light source
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		// P0: Ray origin
		// V: Ray direction
		// Q0: Point on the plane
		// N: Plane normal

		// If q0 == p0, then return null
		if (q0.equals(ray.p0)) {
			return null;
		}

		// Distance from the ray origin to the plane (N dot (P0-Q0))/(N dot V)
		double t = normal.dotProduct(q0.subtract(ray.p0)) / normal.dotProduct(ray.dir);

		// Only add the intersection point if it's in front of the ray,
		// and the distance is not infinite (not parallel)
		// and max distance is greater than the distance from the ray origin to the plane
		if (alignZero(t) > 0 && !Double.isInfinite(t) && alignZero(maxDistance - t) > 0) {
			GeoPoint p = new GeoPoint(this, ray.getPoint(t));
			return List.of(p);
		}

		return null;
	}

	/**
	 * Getter for the plane's normal
	 * 
	 * @return the plane's normal
	 */
	public Vector getNormal() {
		return this.normal;
	}
}
