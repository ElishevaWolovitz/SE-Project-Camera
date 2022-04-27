package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import renderer.Camera;
import primitives.*;

/**
 * Testing Integration of Camera Rays and View Plane
 */
public class ViewPlaneIntegrationTests {

    /**
     * Count the number of intersections between a ray and a geometries
     * through the view plane
     *
     * @param camera        The camera
     * @param intersectable The intersectable (or geometries) to construct rays
     *                      through
     * @param expected      The number of intersections
     */
    private void assertCountIntersections(Camera camera, Intersectable intersectable, int expected) {

        int count = 0;
        List<Point> intersections = null;

        camera.setViewPlaneSize(3, 3); // size of the view plane
        camera.setVPDistance(1); // distance from the camera to the view plane
        int nX = 3; // number of pixels in x direction
        int nY = 3; // number of pixels in y direction
        // view plane 3X3 (WxH 3X3, nX,nY = 3 => Rx,Ry = 1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                // overwrite intersections to the list of intersections of the new ray with the
                // intersectable
                intersections = intersectable.findIntersections(camera.constructRayThroughPixel(nX, nY, j, i));
                // add the number of intersections to the count
                count += intersections == null ? 0 : intersections.size();
            }
        }
        assertEquals(expected, count, "Wrong amount of intersections");
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Small Sphere 2 points
        assertCountIntersections(cam1, new Sphere(new Point(0, 0, -3), 1), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2.5), 2.5), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2), 2), 10);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -1), 4), 9);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(cam1, new Sphere(new Point(0, 0, 1), 0.5), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Plane against camera 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

        // TC04: Plane is behind camera 0 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, 5), new Vector(0, 0, 1)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle 1 point
        assertCountIntersections(cam,
                new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(cam,
                new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2)), 2);
    }

}
