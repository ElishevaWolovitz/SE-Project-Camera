package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Class to represent a scene
 * @author elana
 */
public class Scene {

    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     * Constructor
     */
    public Scene(String name) {
        this.name = name;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
    }

    /**
     * Set the background color
     */
    public Scene setBackground(Color color) {
        background = color;
        return this;
    }

    /**
     * Set the ambient light
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the geometries
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}