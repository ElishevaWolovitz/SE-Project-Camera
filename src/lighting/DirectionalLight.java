package lighting;

import primitives.*;

/**
 * Class representing a directional light source
 * 
 * @author elana
 * @author elish
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * constructor
     * 
     * @param c - color
     * @param d - direction
     */
    public DirectionalLight(Color c, Vector d) {
        super(c);
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
        return super.getIntensity();
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
        return direction;
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
        return Double.POSITIVE_INFINITY;
    }
}
