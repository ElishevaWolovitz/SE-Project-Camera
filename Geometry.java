package geometries;
import primitives.Vector;
import primitives.Point;

public interface Geometry {
	
	public default Vector getNormal(Point p1)
	{
		return null; 
	}

}
