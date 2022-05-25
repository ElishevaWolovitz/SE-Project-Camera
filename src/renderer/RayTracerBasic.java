package renderer;

import java.util.List;

import primitives.*;
import scene.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {
	
	private static final double DELTA = 0.1;
	/**
	 * RayTracerBasic constructor
	 * 
	 * @param s - a scene
	 */
	public RayTracerBasic(Scene s) {
		super(s);
	}

	/**
	 * Find the color of the closest object hit by the ray.
	 *  
	 * @param ray The ray to test
	 * 
	 * @return The color of the closest object hit by the ray.
	 */
	@Override
	public Color traceRay(Ray ray) {
		List<GeoPoint> lstGeoPoints = scene.geometries.findGeoIntersectionsHelper(ray);
		if (lstGeoPoints == null) {
			return scene.background;
		}
		GeoPoint p = ray.findClosestGeoPoint(lstGeoPoints);
		return calcColor(p, ray);
	}
	/**
	 * creates a list of any objects (if there are any) btw light and point 
	 * (note not included in list are any points that are behind light) 
	 * @param l		- a vector from the light to the object 
	 * @param n 	- the normal 
	 * @param gp 	- geoPoint of object 
	 * @return 		- a boolean value - true if there is no object btw point and light and false if there is
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp)
	{
		Vector point2light = l.scale(-1); // to change direction of vector to be from point to light
		Ray lightRay = new Ray(gp.point, point2light ); 
		List<GeoPoint> intersects = scene.geometries.findGeoIntersections(lightRay); 
		double dl = l.distance(gp.point); // distance from point to light
		double dOO; // distance from Other Object to point
		for (GeoPoint i: intersects)
		{
			dOO = l.distance(i.point); 
			if (dOO > dl)
			{
				intersects.remove(i); 
			}
		}
		return intersects.isEmpty(); 
		
	}

	/**
	 * Calculate the color of a geometry intersection point
	 */
	public Color calcColor(GeoPoint p, Ray ray) {
		return scene.ambientLight.getIntensity().add(p.geometry.getEmission()).add(calcLocalEffects(p, ray));
	}

	/**
	 * Calculate the local lighting effects of a geometry intersection point
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		// viewing direction
		Vector v = ray.dir;
		// normal vector
		Vector n = intersection.geometry.getNormal(intersection.point);
		// dot product of v and n
		double nv = alignZero(n.dotProduct(v));
		if (isZero(nv)) {
			return Color.BLACK;
		}
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		double kd = material.kD.d1;
		double ks = material.kS.d1;

		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(calcDiffusive(kd, l, n, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));
			}
		}
		return color;
	}

	/**
	 * Calculate the diffuse lighting effect of a geometry intersection point
	 * 
	 * @param kd             - the diffuse coefficient
	 * @param l              - the light vector
	 * @param n              - the normal vector
	 * @param lightIntensity - the light intensity
	 * 
	 * @return the diffuse lighting effect
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		// amount of projection of l on n
		double nl = Math.abs(alignZero(n.dotProduct(l)));
		// diffuse lighting effect
		return lightIntensity.scale(kd * nl);
	}

	/**
	 * Calculate the specular lighting effect of a geometry intersection point
	 * 
	 * @param ks             - the specular coefficient
	 * @param l              - the light vector
	 * @param n              - the normal vector
	 * @param v              - the viewing vector
	 * @param nShininess     - the shininess coefficient
	 * @param lightIntensity - the light intensity
	 * 
	 * @return the specular lighting effect
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		// amount of projection of l on n
		double nl = alignZero(n.dotProduct(l));
		Vector r = l.subtract(n.scale(2 * nl));
		double rv = alignZero(r.dotProduct(v));
		return lightIntensity.scale(Math.abs((ks * Math.pow(-rv, nShininess))));
	}
}
