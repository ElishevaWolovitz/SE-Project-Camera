
package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;
import static primitives.Util.isZero;

public class Tube {
	protected Ray axisRay;
	protected double radius;

	public Tube(Ray ray, double r1) {
		axisRay = ray;
		radius = r1;
	}

	public Vector getNormal(Point point) {
		// dot product between axisRay direction and direction to the point,
		// resulting in distance to projection point from origin of axis ray
		double scalar = axisRay.dir.dotProduct(point.subtract(axisRay.p0));
		// if the origin is the closest point to Point, making Scalar equal zero
		if (isZero(scalar)) {
			return point.subtract(axisRay.p0).normalize();
		}
		// find where the point ray projects onto the axis ray
		Point closest = axisRay.p0.add(axisRay.dir.scale(scalar));
		return point.subtract(closest).normalize();
	}
}
