package unittests.renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import renderer.Camera.SUPERSAMPLING_TYPE;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setViewPlaneDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setNShininess(100).setKT(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKL(0.0004).setKQ(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.NONE) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setViewPlaneDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20).setKT(0.5)),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setNShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKL(0.00001).setKQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.NONE) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setNShininess(30).setKT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKL(4E-5).setKQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.NONE) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * produces out custom picture made up of a triangle and 3 spheres and with a
	 * spot light
	 */
	@Test
	public void testCustomSceneReflectionRefractionAndShadows() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setViewPlaneDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				// bottom triangle
				new Triangle(new Point(-150, -150, -200), new Point(150, -150, -200), new Point(0, 75, -150)) //
						.setEmission(new Color(30, 220, 80)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20).setKR(0.95)), //
				// sphere above
				new Sphere(new Point(0, -75, -125), 50).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30).setKT(0.5)), //
				// sphere inside sphere on top
				new Sphere(new Point(0, -52, -125), 20).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				// sphere inside sphere on bottom
				new Sphere(new Point(0, -98, -125), 20).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
		);

		scene.lights.add(new SpotLight(new Color(400, 300, 200), new Point(-175, -100, -50), new Vector(175, 25, -75)) //
				.setKL(0.00001).setKQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("customSceneReflectionRefractionAndShadows", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.NONE) //
				.renderImage() //
				.writeToImage();

	}

	public Scene getCustomSceneMiniProject1() {
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				// bottom triangle
				new Triangle(new Point(-150, -150, -200), new Point(150, -150, -200), new Point(0, 75, -150)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20).setKR(0.95)), //
				// sphere above
				new Sphere(new Point(0, -75, -125), 50).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30).setKT(0.5)), //

				// sphere inside sphere on top
				new Sphere(new Point(0, -52, -125), 20).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				// sphere inside sphere on bottom
				new Sphere(new Point(0, -98, -125), 20).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.MAGENTA)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
				// bottom tirangle on left side
				new Triangle(new Point(-150, 80, -180), new Point(-150, 150, -180), new Point(-80, 80, -180)) //
						.setEmission(new Color(java.awt.Color.GRAY)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20)), //
				// top triangle on left side
				new Triangle(new Point(-150, 151, -180), new Point(-80, 81, -180), new Point(-80, 151, -180)) //
						.setEmission(new Color(java.awt.Color.GRAY)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20)), //
				// bottom tirangle on right side
				new Triangle(new Point(80, 150, -180), new Point(80, 80, -180), new Point(150, 80, -180)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20)), //
				// top triangle on the right
				new Triangle(new Point(80, 150, -180), new Point(150, 80, -180), new Point(150, 150, -180)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.55).setKS(0.4).setNShininess(20)), //
				// yellow small sphere on left
				new Sphere(new Point(-125, 100, -130), 15).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.YELLOW)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.1).setNShininess(30).setKT(0.6)), //
				// yellow small sphere on the right
				new Sphere(new Point(125, 100, -170), 15).setEmission(new Color(30, 220, 80)) //
						.setEmission(new Color(java.awt.Color.YELLOW)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.1).setNShininess(30)));
		scene.lights.add(new PointLight(new Color(400, 300, 200), new Point(0, 0, 1100)) //
				.setKL(0.00001).setKQ(0.000005));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-175, -100, -50), new Vector(175, 25, -75))//
				.setKL(0.00001).setKQ(0.000005)); //
		scene.lights.add(new DirectionalLight(new Color(200, 50, 50), new Vector(-300, -300, -150)));
		scene.lights.add(new PointLight(new Color(300, 300, 400), new Point(0, 0, -400)) //
				.setKL(0.00001).setKQ(0.000005));
		scene.lights.add(new SpotLight(new Color(200,300, 300), new Point(-500,74,-50), new Vector(442, -149, -75))//
				.setKL(0.00002).setKQ(0.000005)); //

		return scene;
	}

	/**
	 * produces out custom picture made up of 5 triangles (one big one and 4 small
	 * ones making up squares)
	 * and 5 spheres and with a point light, spot light an directional light
	 */
	@Test
	public void testCustomSceneReflectionRefractionAndShadowsMiniProj1NoSupersampling() {
		// start a timer
		long startTime = System.currentTimeMillis();

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000);

		scene = getCustomSceneMiniProject1();

		ImageWriter imageWriter = new ImageWriter("customSceneReflectionRefractionAndShadowsMiniProj1woSS", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.NONE) //
				.renderImage() //
				.writeToImage();

		// end timer
		long endTime = System.currentTimeMillis();
		System.out.println("Time - with no SS and no MT: " + (endTime - startTime) / 1000.0 + " seconds");
	}

	/**
	 * produces out custom picture made up of 5 triangles (one big one and 4 small
	 * ones making up squares)
	 * and 5 spheres and with a point light, spot light an directional light
	 */
	@Test
	public void testCustomSceneReflectionRefractionAndShadowsMiniProj1() {
		// start a timer
		long startTime = System.currentTimeMillis();

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000);

		scene = getCustomSceneMiniProject1();

		ImageWriter imageWriter = new ImageWriter("customSceneReflectionRefractionAndShadowsMiniProj1withSS", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.REGULAR) //
				.setSupersamplingGridSize(9) //
				.renderImage() //
				.writeToImage();

		// end timer
		long endTime = System.currentTimeMillis();
		System.out.println("Time - with regular SS and no MT: " + (endTime - startTime) / 1000.0 + " seconds");
	}

	/**
	 * produces out custom picture made up of 5 triangles (one big one and 4 small
	 * ones making up squares)
	 * and 5 spheres and with a point light, spot light an directional light
	 */
	@Test
	public void testCustomSceneReflectionRefractionAndShadowsMiniProj2() {
		// start a timer
		long startTime = System.currentTimeMillis();

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000);

		scene = getCustomSceneMiniProject1();

		ImageWriter imageWriter = new ImageWriter("customSceneReflectionRefractionAndShadowsMiniProj1withAdaptive", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.ADAPTIVE) //
				.setAdaptiveSupersamplingMaxRecursionDepth(3) //
				.renderImage() //
				.writeToImage();

		// end timer
		long endTime = System.currentTimeMillis();
		System.out.println("Time - with adaptive SS and no MT: " + (endTime - startTime) / 1000.0 + " seconds");
	}
	
	/**
	 * produces out custom picture made up of 5 triangles (one big one and 4 small
	 * ones making up squares)
	 * and 5 spheres and with a point light, spot light an directional light
	 * with multi threading
	 */
	@Test
	public void testCustomSceneReflectionRefractionAndShadowsMiniProj2withMT() {
		// start a timer
		long startTime = System.currentTimeMillis();

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(300, 300).setViewPlaneDistance(1000).setSupersampling(SUPERSAMPLING_TYPE.NONE);

		scene = getCustomSceneMiniProject1();

		ImageWriter imageWriter = new ImageWriter("customSceneReflectionRefractionAndShadowsMiniProj1withAdaptiveWithMT", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.setSupersampling(SUPERSAMPLING_TYPE.ADAPTIVE) //
				.setAdaptiveSupersamplingMaxRecursionDepth(4) //
				.setMultithreading(3) //
				.setDebugPrint(0.1) //
				.renderImage() //
				.writeToImage();

		// end timer
		long endTime = System.currentTimeMillis();
		System.out.println("Time - with adaptive SS and with MT: " + (endTime - startTime) / 1000.0 + " seconds");
	}
}
