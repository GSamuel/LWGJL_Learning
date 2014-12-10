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
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;

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
	
		Player player = new Player(staticFernModel, new Vector3f(2f,0,-15f),0,0,0,1);
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1,loader,texturePack,blendMap);
		Terrain terrain1 = new Terrain(-1,-1,loader,texturePack,blendMap);
		Terrain terrain2 = new Terrain(1,0,loader,texturePack,blendMap);
		Terrain terrain3 = new Terrain(-1,0,loader,texturePack,blendMap);
		
		
		
		Camera camera = new Camera(player);
		camera.setPosition(new Vector3f(0,2,0));
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			entity.increaseRotation(0,1,0);
			rosanne.increaseRotation(0,0.1f,0);
			camera.move();
			player.move();
			renderer.processEntity(player);
		
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);

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
