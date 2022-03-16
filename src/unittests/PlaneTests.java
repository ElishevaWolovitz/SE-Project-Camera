package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*; 

class PlaneTests {
	    /**
	     * Test method for
	     * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	     */
	    @Test
	    public void testPlane() {
	        // =============== Boundary Values Tests ==================

	        assertThrows(IllegalArgumentException.class,
	            () -> new Plane(new Point(1,2,3), new Point(1,2,3), new Point(4,5,6)), "First and second points are equal");

	        assertThrows(IllegalArgumentException.class,
	            () -> new Plane(new Point(1,2,3), new Point(1,2,4), new Point(1,2,5)),"All points on same line");
	    }


	     /**
	     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	     */
	    @Test
	    public void testGetNormal() {
	        // ============ Equivalence Partitions Tests ==============
	        Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));
	        double oneOverSqrt3 = Math.sqrt(1d / 3);
	        Vector expected = new Vector(oneOverSqrt3, oneOverSqrt3, oneOverSqrt3);
	        Vector actual = plane.getNormal(new Point(1,0,0));
	        assertEquals(actual, expected, "Incorrect plane normal");
	    }
}


