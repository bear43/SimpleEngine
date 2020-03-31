package engine.buffer;

import engine.model.FontModel;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.texture.Texture;
import engine.texture.source.ITextureSource;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL40.*;

public class Loader {

    public static VertexBufferObject createVBO(FloatBuffer vertices, int pointsPerVertex) {
        VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER, GL_FLOAT, pointsPerVertex);
        vbo.fill(vertices);
        return vbo;
    }

    public static VertexBufferObject createVBO(float[] vertices, int pointsPerVertex) {
        VertexBufferObject vbo = new VertexBufferObject(GL_ARRAY_BUFFER, GL_FLOAT, pointsPerVertex);
        vbo.fill(vertices);
        return vbo;
    }

    public static VertexBufferObject createEBO(int[] indices) {
        VertexBufferObject vbo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER, GL_UNSIGNED_INT, 3);
        vbo.fill(indices);
        return vbo;
    }

    public static VertexBufferObject createEBO(IntBuffer indices) {
        VertexBufferObject vbo = new VertexBufferObject(GL_ELEMENT_ARRAY_BUFFER, GL_UNSIGNED_INT, 3);
        vbo.fill(indices);
        return vbo;
    }

    public static VertexArrayObject createVAO(VertexBufferObject vertices, VertexBufferObject indices, VertexBufferObject texCoords) {
        VertexArrayObject vertexArrayObject = new VertexArrayObject();
        vertexArrayObject.setIndexToVBO(0, true, vertices);
        vertexArrayObject.setIndexToVBO(1, true, texCoords);
        vertexArrayObject.bindEBO(indices);
        return vertexArrayObject;
    }

    public static RawModel createModel(String title, float[] vertices, int verticesCount, int[] indices, float[] texCoords, ITextureSource textureSource) {
        return new TexturedModel(title, createVAO(createVBO(vertices, vertices.length/verticesCount), createEBO(indices),createVBO(texCoords, 2)), verticesCount, indices.length, textureSource);
    }

    public static FontModel createFontModel(String title, float[] vertices, int verticesCount, int[] indices, float[] texCoords, Texture texture) {
        return new FontModel(title, createVAO(createVBO(vertices, vertices.length/verticesCount), createEBO(indices),createVBO(texCoords, 2)), verticesCount, indices.length, texture);
    }
}
