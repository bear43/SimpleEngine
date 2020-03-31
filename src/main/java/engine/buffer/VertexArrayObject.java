package engine.buffer;

import engine.MemoryManager;
import lombok.Data;

import java.util.*;

import static org.lwjgl.opengl.GL40.*;

@Data
public class VertexArrayObject implements ICleanable {
    private int id;
    protected Set<VertexBufferObject> vertexBufferObjects = new HashSet<>();

    public VertexArrayObject() {
        id = glGenVertexArrays();
        MemoryManager.getVertexArrayObjects().add(this);
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void useVertexBufferObject(VertexBufferObject vertexBufferObject) {
        if(vertexBufferObject != null) {
            vertexBufferObjects.add(vertexBufferObject);
        } else {
            throw new RuntimeException("Cannot use null VBO");
        }
    }

    public void setIndexToVBO(int index, boolean normalized, VertexBufferObject vertexBufferObject) {
        useVertexBufferObject(vertexBufferObject);
        bind();
        vertexBufferObject.bind();
        glVertexAttribPointer(
                index,
                vertexBufferObject.getDataSize(),
                vertexBufferObject.getDataType(),
                normalized,
                vertexBufferObject.getStride(),
                0
        );
        vertexBufferObject.unbind();
        unbind();
    }

    public void bindEBO(VertexBufferObject indices) {
        bind();
        useVertexBufferObject(indices);
        indices.bind();
        unbind();
        indices.unbind();
    }

    @Override
    public void clean() {
        glDeleteVertexArrays(id);
        MemoryManager.getVertexArrayObjects().remove(this);
        vertexBufferObjects.forEach(ICleanable::clean);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexArrayObject that = (VertexArrayObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
