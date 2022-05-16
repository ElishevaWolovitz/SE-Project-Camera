package lighting;

import primitives.Color;

/**
 * Light abstract class for scene lighting.
 */
abstract class Light {
    /**
     * Intensity color of the light
     */
    private Color intensity;

    /**
     * Constructor
     */
    protected Light(Color i) {
        intensity = i;
    }

    /**
     * Get the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
