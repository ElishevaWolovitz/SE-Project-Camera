package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Interface for intersectable objects
 * 
 * @author Elana Weiss
 */
public abstract class Intersectable {

    /**
     * Static class for intersections with geometries
     */
    public static class GeoPoint {
        /**
         * Geometry intersected
         */
        public Geometry geometry;

        /**
         * Point of intersection
         */
        public Point point;

        /**
         * GeoPoint constructor
         * 
         * @param g geometry
         * @param p point
         */
        public GeoPoint(Geometry g, Point p) {
            geometry = g;
            point = p;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof GeoPoint)) {
                return false;
            }
            GeoPoint g = (GeoPoint) o;
            return g.geometry.equals(geometry) && g.point.equals(point);
        }
        /**
         * @author elish
         * @apiNote returns GeoPoint as a string
         * @return returns a string of the geopoint
         */
        @Override
        public String toString() {
        	return point.toString() + ", " + geometry.toString(); 
        }
    }

    /**
     * Method to find the closest intersection point of a ray with the geometry
     * 
     * @param ray The ray to find the intersection point with
     * 
     * @return List of intersection points with the geometries they intersect with
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                               : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    protected abstract List<GeoPoint> findIntersectionsHelper(Ray ray);

    
}
