package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;

//import java.util.LinkedList;
import java.util.List;

import primitives.*;
import geometries.*;



/**
 * Test method for {@link geometries.Geometries#findIntersections()}.
 * @author elana
 *
 */
public class GeometriesTest {
    
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(0, 3, -7), new Point(1, 3, 3), new Point(2, 3, 4));
        Sphere sphere = new Sphere(new Point(0, 0, 2), 2);
        Sphere sphere2 = new Sphere(new Point(100, 100, 100), 2);
        Ray ray1 = new Ray(new Point(0, 0, -5), new Vector(0, -1, 0));
        Ray ray2 = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        Ray ray3 = new Ray(new Point(0, -5, 2), new Vector(0, 1, 0));
        Geometries geometries1 = new Geometries();
        Geometries geometries2 = new Geometries(plane, sphere);
        Geometries geometries3 = new Geometries(plane, sphere, sphere2);

        // ======================== BVA =============================
        // an empty collection
        assertEquals("an empty collection", null, geometries1.findIntersections(ray1));

        // no shape intersects with a body
        assertEquals("no shape intersects with a body", null, geometries2.findIntersections(ray1));

        // only one shape intersects
        List<Point> expected1 = List.of(new Point(0, 3, -5));
        List<Point> actual1 = geometries2.findIntersections(ray2);
        assertEquals("only one shape intersects", expected1, actual1);

        // all shapes intersects
        List<Point> expected2 = List.of(new Point(0, 3, 2), new Point(0, 2, 2), new Point(0, -2, 2));
        List<Point> actual2 = geometries2.findIntersections(ray3);
        assertEquals("all shapes intersects",expected2, actual2);

        // ======================== EP =============================
        // some shapes but not all intersect
        List<Point> actual3 = geometries3.findIntersections(ray3);
        assertEquals("only one shape intersects", expected2, actual3);

        // test max distance
        List<Point> expected3 = List.of(new Point(0, -2, 2));
        List<Point> actual4 = geometries3.findIntersections(ray3, 5);
        assertEquals("only one shape intersects at max distance", expected3, actual4);
    }
}


