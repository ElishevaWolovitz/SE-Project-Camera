package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Color;
import primitives.Material;

/**
 * Geometry is the abstract class that all geometries inherit from.
 * 
 * @author elana
 * @author elish
 */
public abstract class Geometry extends Intersectable {

	/**
	 * Emission color of the geometry
	 */
	protected Color emission = Color.BLACK;

	/**
	 * Material of the geometry
	 */
	private Material material = new Material();

	/**
	 * Get the normal vector of the geometry at the point
	 * 
	 * @param point point to get the normal vector at
	 * @return normal vector at the point
	 */
	public abstract Vector getNormal(Point point);

	/**
	 * Getter for emission color
	 * 
	 * @return emission color
	 */
	public Color getEmission() {
		return this.emission;
	}

	/**
	 * Setter for emission color
	 * 
	 * @param emission emission color
	 * @return this geometry
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Getter for material
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Setter for material
	 * 
	 * @param m
	 * @return object
	 */
	public Geometry setMaterial(Material m) {
		material = m;
		return this;
	}

}
