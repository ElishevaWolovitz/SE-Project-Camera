package primitives;

import java.util.List;

public class Ray {

	public Point p0;
	public Vector dir;

	public Ray(Point p1, Vector v1) {
		p0 = p1;
		dir = v1.normalize();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return p0.equals(other.p0) && dir.equals(other.dir);
	}

	public String toString() {
		return p0.toString() + dir.toString();
	}

	public Point getPoint(double t) {
		return p0.add(dir.scale(t));
	}
	/**
	 * 
	 * @param lst - a list of points
	 * @return a point that is the point closest to the rays head
	 */
	public Point findClosestPoint(List<Point> lst)
	{
		return null; 
	}
}
