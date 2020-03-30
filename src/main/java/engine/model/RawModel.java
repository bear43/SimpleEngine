package engine.model;

import engine.MemoryManager;
import engine.buffer.VertexArrayObject;
import engine.math.ITransformable;
import lombok.Data;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

@Data
public class RawModel implements ITransformable {
    protected String name;
    protected VertexArrayObject vertexArrayObject;
    protected int verticesCount;
    protected int indicesCount;
    protected Matrix4f transformation;

    public RawModel(String name, VertexArrayObject vertexArrayObject, int verticesCount, int indicesCount) {
        this.name = name;
        this.vertexArrayObject = vertexArrayObject;
        this.verticesCount = verticesCount;
        this.indicesCount = indicesCount;
        this.transformation = new Matrix4f();
        MemoryManager.getModels().add(this);
    }

    public void draw() {
        vertexArrayObject.bind();
        glEnableVertexAttribArray(0);
        if (indicesCount > 0) {
            glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, verticesCount);
        }
        glDisableVertexAttribArray(0);
        vertexArrayObject.unbind();
    }

}
