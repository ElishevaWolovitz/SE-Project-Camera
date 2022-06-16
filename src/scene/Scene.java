package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

/**
 * Class to represent a scene
 * 
 * @author elish
 * @author elana
 */
public class Scene {

    /**
     * The name of the scene
     */
    public String name1;

    /**
     * The background color of the scene
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light of the scene
     */
    public AmbientLight ambientLight;

    /**
     * The list of geometries in the scene
     */
    public Geometries geometries;

    /**
     * The list of light sources in the scene
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor
     */
    public Scene(String name) {
        this.name1 = name;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
    }

    /**
     * setter for lights list
     */
    public Scene setLights(List<LightSource> l) {
        lights = l;
        return this;
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