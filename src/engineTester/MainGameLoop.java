package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop
{

	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.LoadObjModel("dragon", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("grass")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		RawModel rosModel = OBJLoader.LoadObjModel("Char", loader);
		TexturedModel staticRosModel = new TexturedModel(rosModel, new ModelTexture(loader.loadTexture("steve")));
		texture = staticRosModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity =  new Entity(staticModel, new Vector3f(0,0,-50f),0,0,0,1);
		Entity rosanne = new Entity(staticRosModel, new Vector3f(-2f,0,-15f),0,0,0,1);
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			entity.increaseRotation(0,1,0);
			rosanne.increaseRotation(0,1,0);
			camera.move();

			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			renderer.processEntity(rosanne);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}

}
