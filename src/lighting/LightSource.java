package lighting;

import primitives.Color;
import primitives.*;

/**
 * Interface for light sources
 * 
 * @author elana
 * @author elish
 */
public interface LightSource {
    /**
     * Get the intensity of the light for the given point.
     * 
     * @param p The point to get the intensity for.
     *
     * @return the intensity of the light.
     */
    public Color getIntensity(Point p);

    /**
     * Get the direction of the light for the given point.
     * 
     * @param p The point to get the direction for.
     * 
     * @return The direction of the light.
     */
    public Vector getL(Point p);
}
