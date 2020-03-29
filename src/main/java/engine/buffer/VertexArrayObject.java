package engine.buffer;

import engine.MemoryManager;
import lombok.Data;

import java.util.Objects;

import static org.lwjgl.opengl.GL40.*;

@Data
public class VertexArrayObject implements ICleanable {
    private int id;

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

    public void setIndexToVBO(int index, boolean normalized, VertexBufferObject vertexBufferObject) {
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

    @Override
    public void clean() {
        glDeleteVertexArrays(id);
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
