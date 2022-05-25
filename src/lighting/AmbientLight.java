package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light
 * 
 * @author elana
 * @author elish
 */
public class AmbientLight extends Light {
    /**
     * Default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Ambient Light constructor
     */
    public AmbientLight(Color color, Double3 ka) {
        super(color.scale(ka));
    }
}
