package renderer;

import java.util.List;

import primitives.*; 
import scene.*; 
import geometries.Intersectable.GeoPoint;
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
   	 List<GeoPoint> lstGeoPoints; 
   	 GeoPoint p; 
   	 Color c; 
	 lstGeoPoints = s.geometries.findIntersectionsHelper(r); 
	 if(lstGeoPoints == null)
		 return s.background; 
	 p = r.findClosestGeoPoint(lstGeoPoints); 
	 c = calColor(p); 
	 return c; 
	}
	
	public Color calColor(GeoPoint p)
	{
		return s.ambientLight.intensity.add(p.geometry.getEmission());
	}
	

}
