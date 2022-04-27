package renderer;
import scene.*; 
import primitives.*; 


/**
 * 
 * @author elish
 *
 */

public abstract class RayTracerBase {
	protected Scene s; 
	/**
	 * parameter constructor 
	 * @param s
	 */
	public RayTracerBase(Scene s)
	{
		this.s = s; 
	}
	/**
	 * abstract class 
	 * @param c which is a color
	 * @return a ray 
	 */
	public abstract Color traceRay(Ray r); 
}
