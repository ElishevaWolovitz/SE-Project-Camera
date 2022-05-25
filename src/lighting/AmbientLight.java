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
     * 
     * @param color the Color
     * @param ka the Double3
     */
    public AmbientLight(Color color, Double3 ka) {
        super(color.scale(ka));
    }
    
    /**
     * Ambient Light constructor
     * 
     * @param color the Color
     * @param ka the double
     */
    public AmbientLight(Color color, double ka) {
        super(color.scale(ka));
    }
}
