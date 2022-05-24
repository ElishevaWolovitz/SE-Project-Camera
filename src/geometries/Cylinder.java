package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;

/**
 * Class representing a Cylinder
 * 
 * @author elana
 * @author elish
 */
public class Cylinder extends Tube {

	protected double height;

	/**
	 * Constructor
	 * 
	 * @param rad radius
	 * @param ray ray
	 * @param h   height
	 */
	public Cylinder(double rad, Ray ray, double h) {
		super(ray, rad);
		height = h;
	}

	@Override
	public Vector getNormal(Point point) {
		return null;
	}
}
