package geometries;

import primitives.Vector;
import primitives.Ray;

public class Cylinder extends Tube {

	protected double height;

	public Vector getNormal() {
		return null;
	}

	/**
	 * @brief sets values to fields in Cylinder
	 * @param d1
	 */
	public Cylinder(double rad, Ray ray, double d1) {
		super(ray, rad);
		height = d1;
	}
}
