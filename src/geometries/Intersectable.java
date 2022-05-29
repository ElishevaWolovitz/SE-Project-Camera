package geometries;

import java.util.List;
import java.util.stream.Collectors;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Abstract class for intersectable objects
 * 
 * @author elana
 * @author elish
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
            return g.geometry == geometry && g.point == point;
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

        /**
         * Helper method to get the normal of the geometry at the point of intersection
         * 
         * @return normal
         */
        public Vector getNormal() {
            return geometry.getNormal(point);
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
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Method to find the closest intersection point of a ray with the geometry
     * 
     * @param ray         The ray to find the intersection point with
     * @param maxDistance The maximum distance to find the intersection point
     * 
     * @return List of intersection points with the geometries they intersect with
     */
    public List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * Finds the list of geopoints that intersect with the ray
     * 
     * @param ray The ray to find the intersection points with
     * 
     * @return List of geopoints that intersect with the ray
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the list of geopoints that intersect with the ray
     * 
     * @param ray         The ray to find the intersection points with
     * @param maxDistance The maximum distance to find the intersection points with
     * 
     * @return List of geopoints that intersect with the ray
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Finds the list of geopoints that intersect with the ray
     * 
     * @param ray The ray to find the intersection points with
     * 
     * @return List of geopoints that intersect with the ray
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
