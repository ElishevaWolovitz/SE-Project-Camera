/**
 * 
 */
package unittests;
import geometries.Tube;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*; 
import geometries.*; 

/**
 * @author elish
 *
 */
/**
 * Unit tests for geometries.Tube class
 * 
 */
public class TubeTests {
     /** 
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
    	Ray r= new Ray(Point.ZERO, new Vector(1,0,0));
        Tube tube = new Tube(r, 5.0);

        // ============ Boundary Value Tests ==============
        Vector expected = new Vector(0,1,0);
        Vector actual = tube.getNormal(new Point(0,5,0));
        assertEquals(actual, expected, "Tube normal failed orthogonal point");

        // ============ Equivalence Partitions Tests ==============
        Vector expected2 = new Vector(0,1,0);
        Vector actual2 = tube.getNormal(new Point(5,5,0));
        assertEquals(actual2, expected2, "Tube normal failed normal case");
    }
}