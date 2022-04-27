package renderer;

import java.util.List;

import primitives.*; 
import scene.*; 

public class RayTracerBasic extends RayTracerBase{
	/**
	 * parameter constructor 
	 * @param s - a scene 
	 */
	public RayTracerBasic(Scene s)
	{
		super(s); 
	}
	
	/**
	 * @param c - a color 
	 * @return a ray 
	 *  
	 */
	public Color traceRay(Ray r)
	{
   	 List<Point> lstGeos; 
   	 Point p; 
   	 Color c; 
	 lstGeos = s.geometries.findIntersections(r); 
	 if(lstGeos.isEmpty())
		 return s.background; 
	 p = r.findClosestPoint(lstGeos); 
	 c = calColor(p); 
	 return c; 
	}
	
	public Color calColor(Point p)
	{
		return s.ambientLight.intensity;
	}
	

}
