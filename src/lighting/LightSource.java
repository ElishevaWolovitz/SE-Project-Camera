package lighting;

import primitives.Color;
import primitives.Point;

/**
 * Interface for light sources.
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
    public Point getL(Point p);
}
