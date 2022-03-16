package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*; 
import geometries.*; 

public class SphereTests {
	     /**
	     * Test method for GetNormal
	     */
	    @Test
	    public void testGetNormal() {
	        // ============ Equivalence Partitions Tests ==============
	        Sphere Sphere = new Sphere(Point.ZERO, 5.0);
	        Vector expected = new Vector(1,0,0);
	        Vector actual = Sphere.getNormal(new Point(5,0,0));
	        assertEquals(actual, expected, "Sphere normal failed");
	    }

}
