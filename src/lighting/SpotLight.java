package lighting;

import primitives.*;

/**
 * Class representing a spot light source
 * 
 * @author elana
 * @author elish
 */
public class SpotLight extends PointLight {
	private Vector direction;
	private double narrowBeam = 1;

	/**
	 * constructor
	 * 
	 * @param c - color
	 * @param p - position
	 * @param d - direction
	 */
	public SpotLight(Color c, Point p, Vector d) {
		super(c, p);
		direction = d.normalize();
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
		// intensity from PointLight
		Color pointLightIntensity = super.getIntensity(p);
		// direction from light to point
		Vector l = getL(p);
		double projection = direction.dotProduct(l);
		double factor = Math.max(0, projection);
		// how much should narrow the beam by
		// change how much spotlight effect it has based on some power
		if (narrowBeam != 1) {
			factor = Math.pow(factor, narrowBeam);
		}
		return pointLightIntensity.scale(factor);
	}

	/**
	 * Set the narrow beam factor
	 * 
	 * @param nb
	 *
	 * @return the intensity of the light.
	 */
	public PointLight setNarrowBeam(int nb) {
		narrowBeam = nb;
		return this;
	}
	
	/**
	 * Get distance to a point
	 * 
	 * @param p The point to get the distance to.
	 * 
	 * @return The distance to the point.
	 */
	@Override
	public double getDistance(Point p) {
		double distance = super.getDistance(p); 
		return distance; 
	}
}
