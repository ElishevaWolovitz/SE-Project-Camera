package lighting;

import primitives.*;

/**
 * Class representing a point light source
 * 
 * @author elana
 * @author elish
 */
public class PointLight extends Light implements LightSource {
	private Point position;
	double kC = 1;
	double kL = 0;
	double kQ = 0;

	/**
	 * constructor
	 * 
	 * @param c
	 * @param p
	 */
	public PointLight(Color c, Point p) {
		super(c);
		position = p;
	}

	/**
	 * setter for kC
	 * 
	 * @param a double
	 * @return object
	 */
	public PointLight setKC(double newKC) {
		kC = newKC;
		return this;
	}

	/**
	 * setter for kL
	 * 
	 * @param a double
	 * @return object
	 */
	public PointLight setKL(double newKL) {
		kL = newKL;
		return this;
	}

	/**
	 * setter for kQ
	 * 
	 * @param a double
	 * @return object
	 */
	public PointLight setKQ(double newKQ) {
		kQ = newKQ;
		return this;
	}

	/**
	 * Get the intensity of the light for the given point.
	 * 
	 * @param p The point to get the intensity for.
	 *
	 * @return the intensity of the light.
	 */
	@Override
	public Color getIntensity(Point p) {
		Color intensity = super.getIntensity();
		double distanceSquared = position.distanceSquared(p);
		double distance = Math.sqrt(distanceSquared);
		return intensity.reduce(kC + kL * distance + kQ * distanceSquared);
	}

	/**
	 * Get the direction of the light for the given point.
	 * 
	 * @param p The point to get the direction for.
	 * 
	 * @return The direction of the light.
	 */
	@Override
	public Vector getL(Point p) {
		if (p.equals(position)) {
			return null;
		}
		return p.subtract(position).normalize();
	}
}
