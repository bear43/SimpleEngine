package engine.buffer;

import engine.model.RawModel;
import engine.model.TexturedModel;

import static org.lwjgl.opengl.GL40.*;

public class Loader {

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

    public static VertexArrayObject createVAO(VertexBufferObject vbo, VertexBufferObject ebo, VertexBufferObject texCoords) {
        VertexArrayObject vertexArrayObject = new VertexArrayObject();
        vertexArrayObject.setIndexToVBO(0, true, vbo);
        vertexArrayObject.setIndexToVBO(1, true, texCoords);
        vertexArrayObject.bind();
        ebo.bind();
        vertexArrayObject.unbind();
        ebo.unbind();
        return vertexArrayObject;
    }

    public static RawModel createModel(String title, float[] vertices, int verticesCount, int[] indices, float[] texCoords) {
        return new TexturedModel(title, createVAO(createVBO(vertices, vertices.length/verticesCount), createEBO(indices),createVBO(texCoords, 2)), verticesCount, indices.length, "test.png");
    }
}
