package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Test method for {@link geometries.Triangle#findIntersections()}.
 */
public class TriangleTests {

	@Test
	public void testFindIntersections() {
		Triangle triangle = new Triangle(new Point(0, 5, 0), new Point(0, 10, 0), new Point(10, 5, 0));
		Ray ray;
		List<Point> expected, result;

		/**
		 * @author elana
		 */
		// ============ BVA ================
		// On edge of triangle
		ray = new Ray(new Point(5, 5, 10), new Vector(0, 0, -1));
		result = triangle.findIntersections(ray);
		assertEquals("On edge of triangle", null, result);

		// In vertex of triangle
		ray = new Ray(new Point(0, 5, 10), new Vector(0, 0, -1));
		result = triangle.findIntersections(ray);
		assertEquals("In vertex of triangle", null, result);

		// On continuation of edge
		ray = new Ray(new Point(-5, 5, 10), new Vector(0, 0, -1));
		result = triangle.findIntersections(ray);
		assertEquals("On continuation of edge", null, result);

		/**
		 * @author elish
		 *
		 */
		// ============ Equivalence Partitions Tests ==============
		Point p1, p2, p3;
		p1 = new Point(1, 0, 0);
		p2 = new Point(2, 0, 0);
		p3 = new Point(1, -2, 0);
		Vector v1;
		Triangle t1 = new Triangle(p1, p2, p3);

		// TC01: Point of intersection inside triangle
		p1 = new Point(1.5, -1, -2);
		p2 = new Point(1.5, -0.5, 0);
		v1 = new Vector(0, 1, 4);
		ray = new Ray(p1, v1);
		result = t1.findIntersections(ray);
		assertEquals("TC01: incorrect intersection", List.of(p2), result);

		// TC02: Point of intersection outside against edge
		p1 = new Point(0.5, -0.5, -2);
		v1 = new Vector(0, 0, 1);
		ray = new Ray(p1, v1);
		result = t1.findIntersections(ray);
		assertNull("TC02: there should be no intersections", result);

		// TC03: Point of intersection outside against vertex
		p1 = new Point(0.5, 0.5, -2);
		v1 = new Vector(0, 0, 1);
		ray = new Ray(p1, v1);
		result = t1.findIntersections(ray);
		assertNull("TC03: there should be no intersections", result);

		// Test max distance
		p1 = new Point(1.5, -1, -2);
		v1 = new Vector(0, 1, 4);
		ray = new Ray(p1, v1);
		result = t1.findIntersections(ray, 1);
		assertNull("Ray should not instersect if point is past the max distance", result);

	}
}
