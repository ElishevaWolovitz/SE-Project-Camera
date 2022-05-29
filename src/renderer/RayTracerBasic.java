package renderer;

import java.util.List;

import primitives.*;
import scene.*;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {

	/**
	 * Amount to move the ray's head away from the geometry when making shadow rays
	 */
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

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
		List<GeoPoint> lstGeoPoints = scene.geometries.findGeoIntersections(ray);
		if (lstGeoPoints == null) {
			return scene.background;
		}
		GeoPoint p = ray.findClosestGeoPoint(lstGeoPoints);
		return calcColor(p, ray);
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
			if (unshaded(l, n, intersection, lightSource) && nl * nv > 0) { // sign(nl) == sign(nv)
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

	/**
	 * Check if there are no objects between the intersection point and the light source
	 * 
	 * @param l  - a vector from the light to the object
	 * @param n  - the normal
	 * @param gp - geoPoint of object
	 * @return - a boolean value - true if there is no object between point and light
	 *         and false if there is
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
		Vector directionToLight = l.scale(-1); // to change direction of vector to be from point to light
		Ray rayPointToLight = new Ray(gp.point, directionToLight);
		double lightDistance = lightSource.getDistance(gp.point);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(rayPointToLight, lightDistance);
		// if there is no object between point and light, it is unshaded
		return intersections == null;
	}
	
	/**
	 * method to construct a refracted ray of another ray to a point
	 * @param p 	- Point of the object 
	 * @param inRay	- ray that hits points and is refracted 
	 * @return refractedRay - the array that is refracted from the original inRay
	 */
	private Ray constructRefractedRay(Point p, Ray inRay)
	{
		Ray refractedRay;  
		//move original point along normal so be new starting poin for reftactedRay
		Point newP = inRay.p0.add(inRay.dir.scale(DELTA)); 
		refractedRay = new Ray(newP, inRay.dir); 
		return refractedRay; 
	}
	
	/**
	 * method to construct a reflexive ray of another ray to a point
	 * @param p 	- Point of the object 
	 * @param inRay	- ray that hits points and is reflected 
	 * @return reflectedRay - the array that is reflected from the original inRay
	 */
	private Ray constructReflectedRay(Vector n,Point p, Ray inRay)
	{
		Ray reflectedRay; 
		//move original point along normal so be new starting poin for reftactedRay
		Point newP = inRay.p0.add(v.scale(DELTA)); 
		//calculate direction of reflectedRay
		double d1 = inRay.dir.dotProduct(n); 
		Vector v1 = n.scale(d1*2); 
		Vector newV = inRay.dir.subtract(v1); 
		//put together to get reflectedRay
		reflectedRay = new Ray(newP, newV); 
		return reflectedRay; 
	}
}
