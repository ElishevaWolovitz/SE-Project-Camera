package renderer;

import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

import primitives.*;
import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera {

    /**
     * Choices for the supersampling type
     */
    public enum SUPERSAMPLING_TYPE {
        NONE, REGULAR, ADAPTIVE
    }

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double viewPlaneWidth;
    private double viewPlaneHeight;
    private double viewPlaneDistance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    // attributes added for multi-threading
    private boolean multiThreading;
    private double printInterval;
    private double threadsCount;

    // These values are not hard-coded since they can be overriden by calling the
    // respective setter methods
    private int supersamplingGridSize = 9; // grid size for regular supersampling (eg. 9 for 9x9 grid)
    private SUPERSAMPLING_TYPE supersamplingType = SUPERSAMPLING_TYPE.ADAPTIVE; // type of the supersampling (eg. NONE,
                                                                                // RANDOM, GRID)
    private int adaptiveSupersamplingMaxRecursionDepth = 3; // constant max depth for adaptive supersampling

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
     * Set the supersampling to NONE, REGULAR or ADAPTIVE
     *
     * @param SUPERSAMPLING_TYPE type of the supersampling (NONE, REGULAR, ADAPTIVE)
     * @return the current camera
     */
    public Camera setSupersampling(SUPERSAMPLING_TYPE type) {
        this.supersamplingType = type;
        return this;
    }

    /**
     * Set the grid size of the supersampling
     * 
     * @param gridSize grid size of the supersampling (eg. 9 for 9x9 grid)
     * @return the current camera
     */
    public Camera setSupersamplingGridSize(int gridSize) {
        this.supersamplingGridSize = gridSize;
        return this;
    }

    /**
     * Set the max recursion depth for the adaptive supersampling
     * 
     * @param maxRecursionDepth max recursion depth for the adaptive supersampling
     * @return the current camera
     */
    public Camera setAdaptiveSupersamplingMaxRecursionDepth(int maxRecursionDepth) {
        this.adaptiveSupersamplingMaxRecursionDepth = maxRecursionDepth;
        return this;
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
        this.viewPlaneWidth = width;
        this.viewPlaneHeight = height;
        return this;
    }

    /**
     * Set distance from the camera to the view plane
     * 
     * @param distance The distance from the camera to the view plane
     *
     * @return Camera
     */
    public Camera setViewPlaneDistance(double distance) {
        this.viewPlaneDistance = distance;
        return this;
    }

    /**
     * set ray tracer base
     * 
     * @param r
     * @return camera
     */
    public Camera setRayTracer(RayTracerBase r) {
        rayTracer = r;
        return this;
    }

    /**
     * set image writer
     * 
     * @param iw
     * @return camera
     */
    public Camera setImageWriter(ImageWriter iw) {
        imageWriter = iw;
        return this;
    }

    /**
     * set height
     * 
     * @param d
     * @return camera
     */
    public Camera setHeight(double d) {
        viewPlaneHeight = d;
        return this;
    }

    /**
     * set width
     * 
     * @param d
     * @return camera
     */
    public Camera setWidth(double d) {
        viewPlaneWidth = d;
        return this;
    }

    /**
     * set vRight
     * 
     * @param v
     * @return camera
     */
    public Camera setvRight(Vector v) {
        vRight = v;
        return this;
    }

    /**
     * set vTo
     * 
     * @param v
     * @return camrea
     */
    public Camera setvTo(Vector v) {
        vTo = v;
        return this;
    }

    /**
     * set vUp
     * 
     * @param v
     * @return camera
     */
    public Camera setvUp(Vector v) {
        vUp = v;
        return this;
    }

    /**
     * set p0
     * 
     * @param p
     * @return camera
     */
    public Camera setp0(Point p) {
        p0 = p;
        return this;
    }

    /**
     * setter for whether you want to do multi threading
     * on image(x set and not zero) or not(x set to zero)
     * 
     * @param threads number of threads
     * @return camera
     */
    public Camera setMultithreading(double threads) {
        multiThreading = threads != 0;
        threadsCount = threads;
        return this;
    }

    /**
     * setter for printInterval
     * 
     * @param interval The interval between prints in seconds
     * @return camera
     */
    public Camera setDebugPrint(double interval) {
        printInterval = interval;
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
        Point pc = p0.add(vTo.scale(viewPlaneDistance)); // center point of the view plane
        double pixelWidth = viewPlaneWidth / nX; // width of a pixel
        double pixelHeight = viewPlaneHeight / nY; // height of a pixel
        double pcX = (nX - 1) / 2.0; // center pixel value in x direction
        double pcY = (nY - 1) / 2.0; // center pixel value in y direction
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
        return new Ray(p0, pij.subtract(p0));
    }

    /**
     * Constructs a ray through a pixel from the camera and write its color to the
     * image
     * 
     * @param numColumns The number of pixels in the x direction
     * @param numRows    The number of pixels in the y direction
     * @param col        The pixel's x coordinate
     * @param row        The pixel's y coordinate
     */
    public void castRay(int numColumns, int numRows, int col, int row) {
        Color color;
        // height and width of the pixel
        double pixelWidth = viewPlaneWidth / numColumns;
        double pixelHeight = viewPlaneHeight / numRows;
        Ray ray = constructRayThroughPixel(numColumns, numRows, col, row);
        if (supersamplingType == SUPERSAMPLING_TYPE.ADAPTIVE) {
            color = calcAdaptiveSupersamplingColor(ray, pixelWidth, pixelHeight,
                    adaptiveSupersamplingMaxRecursionDepth);
        } else if (supersamplingType == SUPERSAMPLING_TYPE.REGULAR) {
            color = calcSupersamplingColor(ray, pixelWidth, pixelHeight);
        } else {
            color = rayTracer.traceRay(ray);
        }
        imageWriter.writePixel(col, row, color);
    }

    /**
     * checks if any of the fields are null
     * 
     * @throws MissingResourceException
     */
    public Camera renderImage() throws MissingResourceException {
        if (p0 == null || vUp == null || vRight == null || viewPlaneWidth == 0 || viewPlaneHeight == 0
                || viewPlaneDistance == 0
                || imageWriter == null || rayTracer == null) {
            throw new MissingResourceException(null, null, null);
        }
        int numRows = imageWriter.getNy();
        int numColumns = imageWriter.getNx();
        if (multiThreading) {
            Pixel.initialize(numRows, numColumns, printInterval);
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                        castRay(numColumns, numRows, pixel.col, pixel.row);
                    }
                }).start();
            }
            Pixel.waitToFinish();
        }
        // no multi-threading
        else {
            Pixel.initialize(numRows, numColumns, printInterval);
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numColumns; col++) {
                    castRay(numColumns, numRows, col, row);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                }
            }
        }
        return this;
    }

    /**
     * Construct a grid of rays from a center point, a width and height, and a grid
     * size
     * 
     * @param topLeftRayPoint      The top left point of the grid
     * @param raySpacingHorizontal The horizontal spacing between ray points
     * @param raySpacingVertical   The vertical spacing between ray points
     * @param gridSize             the size of the grid
     */
    private List<Ray> constructGridOfRays(Point topLeftRayPoint, double raySpacingHorizontal, double raySpacingVertical,
            int gridSize) {
        Ray ray;
        List<Ray> rays = new ArrayList<>();
        // create the grid of rays
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Point rayPoint = topLeftRayPoint;
                // move the ray down
                if (row > 0) {
                    rayPoint = rayPoint.add(vUp.scale(-row * raySpacingVertical));
                }
                // move the ray right
                if (col > 0) {
                    rayPoint = rayPoint.add(vRight.scale(col * raySpacingHorizontal));
                }
                // create the ray
                ray = new Ray(p0, rayPoint.subtract(p0));
                // add the ray to the list
                rays.add(ray);
            }
        }
        return rays;
    }

    /**
     * Calculates the color of a pixel using supersampling
     * 
     * @param mainRay     the main ray to trace around
     * @param pixelWidth  the width of the pixel
     * @param pixelHeight the height of the pixel
     * @return color of the pixel
     */
    private Color calcSupersamplingColor(Ray mainRay, double pixelWidth, double pixelHeight) {
        // locate the point of the top left ray to start constructing the grid from
        Point centerOfPixel = mainRay.getPoint(viewPlaneDistance);
        // amount to move to get from one supersampling ray location to the next
        double raySpacingVertical = pixelHeight / (supersamplingGridSize + 1);
        double raySpacingHorizontal = pixelWidth / (supersamplingGridSize + 1);
        Point topLeftRayPoint = centerOfPixel //
                .add(vRight.scale(-pixelWidth / 2 - raySpacingHorizontal)) //
                .add(vUp.scale(pixelHeight / 2 - raySpacingVertical));
        List<Ray> rays = constructGridOfRays(topLeftRayPoint, raySpacingHorizontal, raySpacingVertical,
                supersamplingGridSize);
        Color result = Color.BLACK;
        for (Ray ray : rays) {
            result = result.add(rayTracer.traceRay(ray));
        }
        result = result.add(rayTracer.traceRay(mainRay));
        // divide the color by the number of rays to get the average color
        double numRays = (double) supersamplingGridSize * supersamplingGridSize;
        return result.reduce(numRays + 1);
    }

    /**
     * Calculates the color of a pixel using adaptive supersampling
     * 
     * @param centerRay   the ray to trace around
     * @param pixelWidth  the width of the pixel
     * @param pixelHeight the height of the pixel
     * @param level       the level of the adaptive supersampling (if level is 0,
     *                    return)
     * @return color of the pixel
     */
    private Color calcAdaptiveSupersamplingColor(Ray centerRay, double pixelWidth, double pixelHeight, int level) {
        // spacing between rays vertically
        double halfPixelHeight = pixelHeight / 2;
        // spacing between rays horizontally
        double halfPixelWidth = pixelWidth / 2;
        // move 1/4 left and 1/4 up from the center ray
        Point topLeftRayPoint = centerRay.getPoint(viewPlaneDistance) //
                .add(vRight.scale(-(halfPixelWidth / 2))) //
                .add(vUp.scale(halfPixelHeight / 2));
        // gridSize is always 2 since the grid is always a 2x2 grid of rays in
        // adaptive supersampling
        List<Ray> rays = constructGridOfRays(topLeftRayPoint, halfPixelWidth, halfPixelHeight, 2);

        // get the colors of the rays
        List<Color> colors = rays.stream().map(ray -> rayTracer.traceRay(ray)).collect(Collectors.toList());

        // if recursion level is 1, return the average color of the rays
        if (level == 1) {
            return colors.get(0).add(colors.get(1), colors.get(2), colors.get(3)).reduce(4);
        }

        // check if the colors are all the similar enough to be considered the same
        if (colors.get(0).similar(colors.get(1)) //
                && colors.get(0).similar(colors.get(2)) //
                && colors.get(0).similar(colors.get(3))) {
            // if they are, return any one of them
            return colors.get(0);
        }

        // otherwise average the colors of the four parts of the pixel
        return calcAdaptiveSupersamplingColor(rays.get(0), halfPixelWidth, halfPixelHeight, level - 1) //
                .add(calcAdaptiveSupersamplingColor(rays.get(1), halfPixelWidth, halfPixelHeight, level - 1), //
                        calcAdaptiveSupersamplingColor(rays.get(2), halfPixelWidth, halfPixelHeight, level - 1), //
                        calcAdaptiveSupersamplingColor(rays.get(3), halfPixelWidth, halfPixelHeight, level - 1)) //
                .reduce(4);
    }

    /**
     * Print a grid of lines to the image
     * 
     * @param interval The interval between lines
     * @param color    The color of the lines
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(null, null, null);

        // for each pixel, write the color
        for (int row = 0; row < imageWriter.getNy(); row++) {
            for (int col = 0; col < imageWriter.getNx(); col++) {
                if (row % interval == 0 || col % interval == 0) {
                    imageWriter.writePixel(col, row, color);
                }
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Produces unoptimized png file of the image according to pixel color matrix in
     * the directory of the project
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(null, null, null);
        imageWriter.writeToImage();
    }
}
