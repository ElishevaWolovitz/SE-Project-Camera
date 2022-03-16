/**
 * 
 */
package unittests;
import primitives.*;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

/**
 * @author elisheva wolovitz
 *
 */
class VectorTests {

	public boolean isZero(double d)
	{
		return d == 0.0;  
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(v1.add(new Vector(1, -2, -3)),new Vector(2, 0, 0),"ERROR: Vector + Vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(3, 6, 9);
		assertEquals(v1.scale(3), v2, "ERROR: scale() wrong answer"); 
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		assertTrue(isZero(v1.dotProduct(v3)),"ERROR: dotProduct() for orthogonal vectors is not zero");
		assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {	
		Vector v1 = new Vector(1, 2, 3);
		 assertTrue((isZero(v1.lengthSquared() - 14)), "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length)()}.
	 */
	@Test
	void testLength() {
		assertTrue(isZero(new Vector(0, 3, 4).length() - 5),"ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v = new Vector(0, 0, 5);
		Vector u = v.normalize();
		Vector expected= new Vector(0,0,1);
		assertEquals(u,expected, "ERROR:normalizing vector");
		assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
		assertThrows(IllegalArgumentException.class,()->v.crossProduct(u),"ERROR: the normalized vector is not parallel to the original one");
		assertTrue(v.dotProduct(u) > 0,"ERROR: the normalized vector is opposite to the original one");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	
	@Test
    public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		try 
		{ // test zero vector
			v1.crossProduct(v2);
		 	out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
		} 
		catch (Exception e) {
		}
		Vector vr = v1.crossProduct(v3);
		assertEquals(vr.length(),v1.length() * v3.length(),0.0001,"ERROR: crossProduct() wrong result length");
		assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),"ERROR: crossProduct() result is not orthogonal to its operands");
    }

	
}
