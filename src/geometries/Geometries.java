package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {

    /**
     * List of geometries
     */
    List<Intersectable> geometryList;

    /**
     * Constructor
     * 
     * @param geometries List of geometries
     */
    public Geometries(Intersectable... geometries) {
        geometryList = new LinkedList<>();
        this.add(geometries);
    }

    /**
     * Add geometries to the list
     * 
     * @param geometries Geometries to add
     */
    public void add(Intersectable... geometries) {
        geometryList.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (geometryList.isEmpty()) {
            return null;
        }
        // initialize the list of intersection points to null
        List<Point> intersections = null;
        for (Intersectable geometry : geometryList) {
            // get the intersection points of the current geometry
            List<Point> tempIntersections = geometry.findIntersections(ray);
            // if there are no intersection points, continue
            // otherwise, add the intersection points to the list of intersection points
            if (tempIntersections != null) {
                // if the list of intersection points is null, initialize it
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                // add the points to the list of intersection points
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

}
