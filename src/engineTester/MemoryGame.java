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
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MemoryGame
{
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		ModelData data = OBJFileLoader.loadOBJ("MemoryTile");
		RawModel tileModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel texturedTileModel = new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("MemoryTile")));

		Entity tile = new Entity(texturedTileModel, new Vector3f(0,0,0), 90, 0, 0, 1);
		Entity tile2 = new Entity(texturedTileModel, new Vector3f(2.5f,0,0), 90, 0, 0, 1);
		
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		
		Camera camera = new Camera(tile);
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			camera.move();
			renderer.processEntity(tile);
			tile.setRotZ(tile.getRotZ()+1);
			renderer.processEntity(tile2);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}
}
