package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import primitives.*;
import geometries.*;

public class PlaneTests {
	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testPlane() {
		// =============== Boundary Values Tests ==================

		assertThrows("First and second points are equal", IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(4, 5, 6)));

		assertThrows("All points on same line", IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 4), new Point(1, 2, 5)));
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		double oneOverSqrt3 = Math.sqrt(1d / 3);
		Vector expected = new Vector(oneOverSqrt3, oneOverSqrt3, oneOverSqrt3);
		Vector actual = plane.getNormal(new Point(1, 0, 0));
		assertEquals("Incorrect plane normal", actual, expected);
	}

	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point(0, 3, -7), new Point(1, 3, 3), new Point(2, 3, 4));
		Ray ray;
		List<Point> expected;
		List<Point> actual;
		// =================== BVA ==================
		// Ray parallel, ray in the plane
		ray = new Ray(new Point(0,3,0), new Vector(0,0,1));
		actual = plane.findIntersections(ray);
		assertEquals("Ray parallel, ray in the plane", null, actual);
		
		// Ray parallel, ray not in the plane
		ray = new Ray(new Point(0,3,0), new Vector(0,0,1));
		actual = plane.findIntersections(ray);
		assertEquals("Ray parallel, ray not in the plane", null, actual);
		
		// Ray orthogonal, before plane
		ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
		actual = plane.findIntersections(ray);
		expected = List.of(new Point(0, 3, -5));
		assertEquals("Ray orthogonal, after plane", expected, actual);
		
		// Ray orthogonal, after plane
		ray = new Ray(new Point(0, 0, -5), new Vector(0, -1, 0));
		actual = plane.findIntersections(ray);
		assertEquals("Ray orthogonal, after plane", null, actual);

		// Ray orthogonal, ray in the plane
		ray = new Ray(new Point(0, 3, -5), new Vector(0, -1, 0));
		actual = plane.findIntersections(ray);
		assertEquals("Ray orthogonal, ray in the plane", null, actual);

		// Ray not orthogonal or parallel, begins at the plane
		ray = new Ray(new Point(0, 3, -5), new Vector(0, 1, 1));
		actual = plane.findIntersections(ray);
		assertEquals("Ray not orthogonal or parallel, begins at the plane", null, actual);

		// Ray not orthogonal or parallel, begins at Q0
		ray = new Ray(new Point(0, 3, -7), new Vector(0, 1, 1));
		actual = plane.findIntersections(ray);
		assertEquals("Ray not orthgonal or parallel, begins at Q0", null, actual);

		// =================== EP ====================
		// Ray intersects plane
		ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 1));
		actual = plane.findIntersections(ray);
		expected = List.of(new Point(0,3,-2));
		assertEquals("Ray not orthogonal or parallel, begins at the plane", expected, actual);

		// Ray does not intersect plane
		ray = new Ray(new Point(0, 0, -5), new Vector(0, -1, 1));
		actual = plane.findIntersections(ray);
		assertEquals("Ray does not intersect plane", null, actual);
	}
}
