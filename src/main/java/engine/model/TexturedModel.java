package engine.model;

import engine.MemoryManager;
import engine.buffer.VertexArrayObject;
import engine.texture.source.ITextureSource;
import engine.texture.Texture;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

@EqualsAndHashCode(callSuper = true)
@Data
public class TexturedModel extends RawModel {
    private Texture texture;


    public TexturedModel(String name, VertexArrayObject vao, int verticesCount, int indicesCount, ITextureSource textureSource) {
        super(name, vao, verticesCount, indicesCount);
        texture = new Texture(textureSource);
        texture.load();
    }

    public TexturedModel(String name, VertexArrayObject vao, int verticesCount, int indicesCount, Texture texture) {
        super(name, vao, verticesCount, indicesCount);
        this.texture = texture;
        this.texture.load();
    }

    @Override
    public void draw() {
        vertexArrayObject.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        texture.bind();
        if(indicesCount > 0) {
            glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, verticesCount);
        }
        texture.unbind();
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        vertexArrayObject.unbind();
    }
}
