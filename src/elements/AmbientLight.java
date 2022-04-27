package elements;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light
 */
public class AmbientLight {
    public Color intensity;

    /**
     * Default constructor
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    /**
     * Ambient Light constructor
     */
    public AmbientLight(Color color, Double3 ka) {
        intensity = color.scale(ka);
    }

    /**
     * Get the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
