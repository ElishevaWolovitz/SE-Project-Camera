package geometries;
import primitives.Vector;
import primitives.Point;
import primitives.Color;

/**
 * Geometry is the abstract class that all geometries inherit from.
 */
public abstract class Geometry extends Intersectable {

	/**
	 * Emission color of the geometry
	 */
	protected Color emission = Color.BLACK;

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
	 * Get the normal vector of the geometry at the point p1
	 * 
	 * @param p1 point to get the normal vector at
	 * @return normal vector at the point p1
	 */
	public abstract Vector getNormal(Point p1);

}
