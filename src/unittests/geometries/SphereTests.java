package unittests.geometries;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.*;
import geometries.*;

public class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Sphere Sphere = new Sphere(Point.ZERO, 5.0);
		Vector expected = new Vector(1, 0, 0);
		Vector actual = Sphere.getNormal(new Point(5, 0, 0));
		assertEquals(actual, expected, "Sphere normal failed");
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Point p1, p2, p3;
		Vector v1;
		Ray r1;
		p1 = new Point(1, 0, 0);
		Sphere sphere = new Sphere(p1, 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		p1 = new Point(-2, 0, 0);
		v1 = new Vector(2, 2, 0);
		r1 = new Ray(p1, v1);
		assertNull(sphere.findIntersections(r1), "TC01: Incorrect, Ray lies out of sphere, so no intersection");

		// TC02: Ray starts before and crosses the sphere (2 points)
		p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
				new Vector(3, 1, 0)));
		assertEquals(2, result.size(), "Wrong number of points");
		if (result.get(0).xyz.d1 > result.get(1).xyz.d1)
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		// - checks if ray intersects sphere only once
		p1 = new Point(0.5, 0, 0);
		v1 = new Vector(1, 1, 0);
		r1 = new Ray(p1, v1);
		p2 = new Point(1.4114378277661475, 0.9114378277661476, 0.0);
		result = sphere.findIntersections(r1);
		assertEquals(1, result.size(), "TC03: Wrong number of points");

		// - checks that findIntersection gets correct point that ray intersects sphere
		assertEquals(List.of(p2), result, "TC03: Incorrect intersection point calculated");

		// TC04: Ray starts after the sphere (0 points)
		p1 = new Point(2, 2, 0);
		v1 = new Vector(1, 1, 0);
		r1 = new Ray(p1, v1);
		assertNull(sphere.findIntersections(r1), "TC04: Ray starts after sphere, so no intersections");

		// Test max distance (max distance between 2 points)
		p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		p2 = new Point(1.53484692283495, 0.844948974278318, 0);
		result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
				new Vector(3, 1, 0)), 1.5);
		assertEquals(List.of(p1), result, "Ray should intersect one point before max distance");

		// Test max distance (max distance before sphere)
		result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
				new Vector(3, 1, 0)), 0.5);
		assertEquals(null, result, "Ray should intersect no points before max distance");

		// Test max distance (max distance after sphere)
		result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
				new Vector(3, 1, 0)), 3);
		if (result.get(0).xyz.d1 > result.get(1).xyz.d1)
			result = List.of(result.get(1), result.get(0));
		assertEquals(List.of(p1, p2), result, "Ray should intersect both points before max distance");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		p1 = new Point(2, 0, 0);
		p2 = new Point(1, 1, 0);
		v1 = new Vector(-1, 1, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertEquals(1, result.size(), "TC11: Wrong number of points");
		assertEquals(result, List.of(p2), "TC11: Incorrect intersection point");

		// TC12: Ray starts at sphere and goes outside (0 points)
		p1 = new Point(2, 0, 0);
		v1 = new Vector(1, 1, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC12: There should be no itersection");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		p1 = new Point(3, 0, 0);
		p2 = new Point(2, 0, 0);
		p3 = new Point(0, 0, 0);
		v1 = new Vector(-1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertEquals(2, result.size(), "TC13: Wrong number of points");
		if (result.get(0).xyz.d2 > result.get(1).xyz.d2) {
			result = List.of(result.get(1), result.get(0));
		}
		assertEquals(result, List.of(p3, p2), "TC13: Incorrect intersection points");
		// TC14: Ray starts at sphere and goes inside (1 points)
		p1 = new Point(2, 0, 0);
		p2 = new Point(0, 0, 0);
		v1 = new Vector(-1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertEquals(1, result.size(), "TC14: Wrong number of points");
		assertEquals(result, List.of(p2), "TC14: Incorrect intersection points");
		// TC15: Ray starts inside (1 points)
		p1 = new Point(1.5, 0, 0);
		p2 = new Point(0, 0, 0);
		v1 = new Vector(-1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertEquals(1, result.size(), "TC15: Wrong number of points");
		assertEquals(result, List.of(p2), "TC15: Incorrect intersection points");
		// TC16: Ray starts at the center (1 points)
		p1 = new Point(1, 0, 0);
		p2 = new Point(2, 0, 0);
		v1 = new Vector(1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertEquals(1, result.size(), "TC16: Wrong number of points");
		assertEquals(result, List.of(p2), "TC16: Incorrect intersection points");
		// TC17: Ray starts at sphere and goes outside (0 points)
		p1 = new Point(2, 0, 0);
		v1 = new Vector(1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC17: Incorrect intersection points");
		// TC18: Ray starts after sphere (0 points)
		p1 = new Point(3, 0, 0);
		v1 = new Vector(1, 0, 0);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC18: Incorrect intersection points");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		p1 = new Point(2, 0, -1);
		v1 = new Vector(0, 0, 1);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC19: Incorrect intersection points");
		// TC20: Ray starts at the tangent point
		p1 = new Point(2, 0, 0);
		v1 = new Vector(0, 0, 1);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC20: Incorrect intersection points");
		// TC21: Ray starts after the tangent point
		p1 = new Point(2, 0, 1);
		v1 = new Vector(0, 0, 1);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "T21: Incorrect intersection points");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		p1 = new Point(-1, 0, 0);
		v1 = new Vector(0, 0, 1);
		r1 = new Ray(p1, v1);
		result = sphere.findIntersections(r1);
		assertNull(result, "TC22: Incorrect intersection points");

	}

}
