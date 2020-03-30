package engine.model;

import engine.MemoryManager;
import engine.buffer.VertexArrayObject;
import engine.texture.Texture;

public class FontModel extends TexturedModel {
    public FontModel(String name, VertexArrayObject vao, int verticesCount, int indicesCount, Texture texture) {
        super(name, vao, verticesCount, indicesCount, texture);
        MemoryManager.getModels().remove(this);
        MemoryManager.getFontModels().add(this);
    }
}
