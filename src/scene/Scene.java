package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

/**
 * Class to represent a scene
 * @author elana
 */
public class Scene {

    public String name1;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<LightSource>();

    
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
    public Scene setLights(List<LightSource > l)
    {
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