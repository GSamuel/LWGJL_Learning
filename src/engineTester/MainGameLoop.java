package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop
{

	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader(null, null);
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,//V0
				-0.5f, -0.5f, 0f,//V1
				0.5f, -0.5f, 0f,//V2
				0.5f, 0.5f, 0f,//V3			
		};
		
		int[] indices={
				0,1,3,
				3,1,2
		};
		
		float[] textureCoords = {
				0,0,//V0
				0,1,//V1
				1,1,//V2
				1,0 //V3
		};
		
		RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Penguin"));
		TexturedModel textureModel = new TexturedModel(model, texture);
		
		while (!Display.isCloseRequested())
		{
			renderer.prepare();
			shader.start();
			renderer.render(textureModel);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}

}
