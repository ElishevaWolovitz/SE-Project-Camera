package elements;

import primitives.Color;

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
    public AmbientLight(Color color, double ka) {
        intensity = color.scale(ka);
    }

    /**
     * Get the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
