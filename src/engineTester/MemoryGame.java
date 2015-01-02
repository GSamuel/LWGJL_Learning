package engineTester;

import java.util.ArrayList;

import memory.TileBuilder;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import entities.Entity;
import entities.EntityCamera;
import entities.Light;
import entities.Node;

public class MemoryGame
{
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TileBuilder tileBuilder = new TileBuilder(loader);
		
		ArrayList<TexturedModel> models = tileBuilder.allAsArray();
		Entity[] entities = new Entity[models.size()];

		for (int i = 0; i < models.size(); i ++)
			entities[i] = new Entity(models.get(i), new Vector3f(2.5f*i,0,0), 90, 0, 0, 1);
		
		
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Node node = new Node(new Vector3f(2.5f,0,0), 90, 0, 0);
		EntityCamera camera = new EntityCamera(node);
		
		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested())
		{
			camera.move();
			for(Entity en: entities)
			{
				renderer.processEntity(en);
				//en.setRotZ(en.getRotZ()+1);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}
}
