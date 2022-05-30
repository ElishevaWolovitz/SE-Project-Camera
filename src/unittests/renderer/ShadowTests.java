package unittests.renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
public class ShadowTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200).setVPDistance(1000) //
			.setRayTracer(new RayTracerBasic(scene));

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKL(1E-5).setKQ(1.5E-7));
		camera.setImageWriter(new ImageWriter("sphereTriangleInitial", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * 
	 * Produce a picture of a sphere and triangle with point light and shade ---
	 * Test movement of the triangle 1
	 */
	@Test
	public void sphereTriangleMoveTriangle1() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				new Triangle(new Point(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKL(1E-5).setKQ(1.5E-7));
		camera.setImageWriter(new ImageWriter("sphereTriangleMoveTriangle1", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade ---
	 * Test movement of the triangle 2
	 */
	@Test
	public void sphereTriangleMoveTriangle2() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				new Triangle(new Point(-50, -20, 0), new Point(-20, -50, 0), new Point(-48, -48, -4)) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
						.setKL(1E-5).setKQ(1.5E-7));
		camera.setImageWriter(new ImageWriter("sphereTriangleMoveTriangle2", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * 
	 * Produce a picture of a sphere and triangle with point light and shade ---
	 * Test movement of the light 1
	 */
	@Test
	public void sphereTriangleMoveLight1() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-85, -85, 100), new Vector(1, 1, -3)) //
						.setKL(1E-5).setKQ(1.5E-7));
		camera.setImageWriter(new ImageWriter("sphereTriangleMoveLight1", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere and triangle with point light and shade ---
	 * Test movement of the light 2
	 */
	@Test
	public void sphereTriangleMoveLight2() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -200), 60) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-75, -75, 65), new Vector(1, 1, -3)) //
						.setKL(1E-5).setKQ(1.5E-7));
		camera.setImageWriter(new ImageWriter("sphereTriangleMoveLight2", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setNShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setNShininess(60)), //
				new Sphere(new Point(0, 0, -115), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
						.setKL(4E-4).setKQ(2E-5));
		camera.setImageWriter(new ImageWriter("trianglesSphere", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}

}
