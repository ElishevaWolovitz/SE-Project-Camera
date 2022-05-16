package renderer;

import java.util.List;

import primitives.*;
import scene.*;
import geometries.Intersectable.GeoPoint;

public class RayTracerBasic extends RayTracerBase {
	/**
	 * RayTracerBasic constructor
	 * 
	 * @param s - a scene
	 */
	public RayTracerBasic(Scene s) {
		super(s);
	}

	@Override
	public Color traceRay(Ray r) {
		List<GeoPoint> lstGeoPoints;
		GeoPoint p;
		Color c;
		lstGeoPoints = scene.geometries.findGeoIntersectionsHelper(r);
		if (lstGeoPoints == null)
			return scene.background;
		p = r.findClosestGeoPoint(lstGeoPoints);
		c = calcColor(p);
		return c;
	}

	/**
	 * Calculate the color of a geometry intersection point
	 */
	public Color calcColor(GeoPoint p) {
		return scene.ambientLight.intensity.add(p.geometry.getEmission());
	}
}
