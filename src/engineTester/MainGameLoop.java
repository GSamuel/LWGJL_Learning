package engineTester;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

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
		
		ModelData data = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel staticFernModel = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
		staticFernModel.getTexture().setHasTransparency(true);
		staticFernModel.getTexture().setUseFakeLighting(true);
		
		
		RawModel model = OBJLoader.LoadObjModel("dragon", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("grass")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		RawModel rosModel = OBJLoader.LoadObjModel("bunny", loader);
		TexturedModel staticRosModel = new TexturedModel(rosModel, new ModelTexture(loader.loadTexture("blendMap")));
		texture = staticRosModel.getTexture();
		texture.setShineDamper(1);
		texture.setReflectivity(0.2f);
		texture.setHasTransparency(true);
		texture.setUseFakeLighting(true);
		
		Entity entity =  new Entity(staticModel, new Vector3f(0,0,-50f),0,0,0,1);
		Entity rosanne = new Entity(staticRosModel, new Vector3f(-5f,0,-35f),0,0,0,1);
		Entity fern = new Entity(staticFernModel, new Vector3f(2f,0,-15f),0,0,0,1);
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain( 0,0,loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain( 0,-1,loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain3 = new Terrain(-1, 0,loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain4 = new Terrain(-1,-1,loader, new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		camera.setPosition(new Vector3f(0,2,0));
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			entity.increaseRotation(0,1,0);
			rosanne.increaseRotation(0,0.1f,0);
			camera.move();

			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);
			renderer.processTerrain(terrain4);
			renderer.processEntity(entity);
			renderer.processEntity(rosanne);
			renderer.processEntity(fern);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}

}
