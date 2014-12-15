package memory;

import java.util.ArrayList;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class TileBuilder
{
	private ModelData data = OBJFileLoader.loadOBJ("MemoryTile");
	private RawModel tileModel;
	private Loader loader;
	private ArrayList<TexturedModel> models = new ArrayList<TexturedModel>();
	
	public TileBuilder(Loader loader)
	{
		this.loader = loader;
		this.tileModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		loadTiles();
	}
	
	public ArrayList<TexturedModel> allAsArray()
	{
		return models;
	}

	public void loadTiles()
	{
		models.add(new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("Tile01"))));
		models.add(new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("Tile02"))));
		models.add(new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("Tile03"))));
	}
}
