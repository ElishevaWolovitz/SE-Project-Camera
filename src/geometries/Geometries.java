package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Double3;
import primitives.Ray;

/**
 * Class representing a collection of Geometries
 * 
 * @author elana
 * @author elish
 */
public class Geometries extends Intersectable {

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

    /**
     * takes a ray from a point and finds any
     * geometries that intersect with the ray 
     * that are not further then the max distance away
     * @param ray 
     * @param maxdistance 
     * @return a list of geoPoints that intersect with ray passed
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (geometryList.isEmpty()) {
            return null;
        }
        // initialize the list of intersection points to null
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : geometryList) {
            // get the intersection points of the current geometry
            List<GeoPoint> tempIntersections = geometry.findGeoIntersections(ray, maxDistance);
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
