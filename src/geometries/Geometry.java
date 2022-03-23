package geometries;
import primitives.Vector;
import primitives.Point;

public interface Geometry extends Intersectable {
	
	public default Vector getNormal(Point p1)
	{
		return null; 
	}

}
