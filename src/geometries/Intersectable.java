package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Interface for intersectable objects
 * 
 * @author Elana Weiss 
 */
public interface Intersectable {

    /**
     * Method to find the closest intersection point of a ray with the geometry
     * 
     * @param ray The ray to find the intersection point with
     * 
     * @return List of intersection points
     */
    public List<Point> findIntersections(Ray ray);
}
