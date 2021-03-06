package engine.model;

import engine.MemoryManager;
import engine.buffer.ICleanable;
import engine.buffer.VertexArrayObject;
import lombok.Data;
import org.joml.Matrix4f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

@Data
public class RawModel implements IModel, ICleanable {
    protected String name;
    protected VertexArrayObject vertexArrayObject;
    protected int verticesCount;
    protected int indicesCount;
    protected Matrix4f transformation;

    public RawModel(String name, VertexArrayObject vertexArrayObject, int verticesCount, int indicesCount, boolean register) {
        this.name = name;
        this.vertexArrayObject = vertexArrayObject;
        this.verticesCount = verticesCount;
        this.indicesCount = indicesCount;
        this.transformation = new Matrix4f();
        if(register) {
            MemoryManager.getModels().putIfAbsent(this.getClass(), new ArrayList<>());
            MemoryManager.getModels().get(this.getClass()).add(this);
        }
    }

    public RawModel(String name, VertexArrayObject vertexArrayObject, int verticesCount, int indicesCount) {
        this(name, vertexArrayObject, verticesCount, indicesCount, true);
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

    @Override
    public void clean() {
        vertexArrayObject.clean();
        MemoryManager.removeModelInstance(this);
    }
}
