/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Unit tests for primitives.Point class
 * 
 */
public class PointTests { 

    /**
     * Test method for {@link primitives.Point#Point(double, double, double)}.
     */
	 @Test
	    public void testSubtract() {
	        Point p1 = new Point(1, 2, 3);
	        Point p2 = new Point(3, 2, 1);
	        Vector expected = new Vector(-2, 0, 2);
	        Vector actual = p1.subtract(p2);
	        assertEquals(expected, actual,"Subtract is not correct");
	    }

	    /**
	     * Test method for {@link primitives.Point#add(primitives.Vector)}.
	     */
	    @Test
	    public void testAdd() {
	        Point p1 = new Point(1, 2, 3);
	        Vector v = new Vector(3, 2, 1);
	        Point expected = new Point(4, 4, 4);
	        Point actual = p1.add(v); 
	        assertEquals( expected, actual, "Add is not correct");
	    }

	    /**
	     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}
	     */
	    @Test
	    public void testDistanceSquared() {
	        Point p1 = new Point(1, 1, 1);
	        Point p2 = new Point(1, 2, 3);
	        double expected = 5;
	        double actual = p1.distanceSquared(p2);
	        assertTrue(isZero(expected - actual),"Distance squared failed");
	    }

	    /**
	     * Test method for {@link primitives.Point#distance(primitives.Point)}
	     */
	    @Test
	    public void testDistance() {
	        Point p1 = new Point(1, 1, 1);
	        Point p2 = new Point(1, 2, 3);
	        double expected = java.lang.Math.sqrt(5);
	        double actual = p1.distance(p2);
	        assertTrue(isZero(expected - actual), "Distance failed");
	    }
	}

   