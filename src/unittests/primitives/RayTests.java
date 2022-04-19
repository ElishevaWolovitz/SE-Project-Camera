/**
 * 
 */
package unittests.primitives;
import primitives.*; 

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author elish
 *
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	void testFindClosestPoint() {
		// ============ Equivalence Partitions Tests ==============
			//TC01: the closest point to the ray’s head is found somewhere in the middle of the list
		Point p, p1, p2, p3, result; 
		Vector v; 
		Ray r; 
		p = new Point(-2,1,0);  
		v = new Vector(1,0,0); 
		r = new Ray(p, v); 
		
		p1 = new Point(1,1,0);
		p2 = new Point(2,1,0);
		p3 = new Point(3,1,0);
		result = r.findClosestPoint(List.of(p2,p1,p3)); 
		assertEquals(p1, result, "TC01: Wrong closest point"); 
		// ============ Boundary Tests ==============
			//TC11: an empty list 
		result = r.findClosestPoint(List.of()); 
		assertNull(result, "TC11: not an empty list"); 
			//TC12: a list where the closest point is the first point in the list
		result = r.findClosestPoint(List.of(p1, p2, p3)); 
		assertEquals(p1, result, "TC12: Wrong closest point"); 
			//TC13: a list where the closest point is the last point in the list
		result = r.findClosestPoint(List.of(p2, p3, p1)); 
		assertEquals(p1, result, "TC13: Wrong closest point");
	}

}
