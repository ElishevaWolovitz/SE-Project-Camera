package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 *  Test method for {@link geometries.Triangle#findIntersections()}.
 */
public class TriangleTests {

	@Test
	public void testFindIntersections() {
		Triangle triangle = new Triangle(new Point(0,5,0), new Point(0,10,0), new Point(10,5,0));
		Ray ray;
		List<Point> expected, actual;

		/**
		 * @author elana
		 */
		// ============ BVA ================
		// On edge of triangle
		ray = new Ray(new Point(5,5,10), new Vector(0,0,-1));
		actual = triangle.findIntersections(ray);
		assertEquals("On edge of triangle", null, actual);

		// In vertex of triangle
		ray = new Ray(new Point(0,5,10), new Vector(0,0,-1));
		actual = triangle.findIntersections(ray);
		assertEquals("In vertex of triangle", null, actual);

		// On continuation of edge
		ray = new Ray(new Point(-5,5,10), new Vector(0,0,-1));
		actual = triangle.findIntersections(ray);
		assertEquals("On continuation of edge", null, actual);

		// ============ EP =================
		// Inside polygon/triangle

		// Outside against edge

		// Outside against vertex
	}
}







