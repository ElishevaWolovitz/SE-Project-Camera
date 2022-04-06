package renderer;

import primitives.*;
import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera {

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width; // width of the view plane in pixels
    private double height; // height of the view plane in pixels
    private double distance;  // distance from the camera to the view plane

    /**
     * Camera constructor
     * 
     * @param p0  Camera position
     * @param vTo Camera direction
     * @param vUp Camera up vector
     * 
     * @throws IllegalArgumentException if vTo and vUp are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        // verify that vUp and vTo are orthogonal
        if (vUp.dotProduct(vTo) != 0) {
            throw new IllegalArgumentException("vUp and vTo must be orthogonal");
        }
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Set view plane size
     * 
     * @param width  The width of the view plane in pixels
     * @param height The height of the view plane in pixels
     *
     * @return Camera
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set distance from the camera to the view plane
     * 
     * @param distance The distance from the camera to the view plane
     *
     * @return Camera
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray through a pixel from the camera
     * 
     * @param nX The number of pixels in the x direction
     * @param nY The number of pixels in the y direction
     * @param j  The pixel's x coordinate
     * @param i  The pixel's y coordinate
     * 
     * @return The ray through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance)); // center point of the view plane
        double pixelWidth = width / nX; // width of a pixel
        double pixelHeight = height / nY; // height of a pixel
        double pcX = (nX-1) / 2.0; // center pixel value in x direction
        double pcY = (nY-1)/ 2.0; // center pixel value in y direction
        double rightDistance = (j - pcX) * pixelWidth; // x offset of j from the center pixel
        double upDistance = -1 * (i - pcY) * pixelHeight; // y offset of i from the center pixel

        Point pij = pc; // start at the center of the view plane

        // we need to check if the distance is zero, because we can't scale a vector by
        // zero
        if (!isZero(rightDistance)) {
            // if the distance to move right is not zero, move right
            pij = pij.add(vRight.scale(rightDistance));
        }

        if (!isZero(upDistance)) {
            // if the distance to move up is not zero, move up
            pij = pij.add(vUp.scale(upDistance));
        }

        // construct a ray from the camera origin in the direction of the pixel at (j,i)
        return new Ray(p0, pij.subtract(p0).normalize());
    }
}