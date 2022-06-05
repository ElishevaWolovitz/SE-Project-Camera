package renderer;

import scene.*;
import primitives.*;

/**
 * Abstract class for ray tracers
 * 
 * @author elish
 * @author elana
 */
public abstract class RayTracerBase {

	protected Scene scene;

	/**
	 * RayTracerBase constructor
	 * 
	 * @param s
	 */
	protected RayTracerBase(Scene s) {
		this.scene = s;
	}

	/**
	 * Find the color of the closest object hit by the ray.
	 * 
	 * @param ray The ray to test
	 * 
	 * @return The color of the closest object hit by the ray.
	 */
	public abstract Color traceRay(Ray ray);
}
