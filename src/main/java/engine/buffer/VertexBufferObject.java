package engine.buffer;

import engine.MemoryManager;
import lombok.Data;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL40.*;

@Data
public class VertexBufferObject implements ICleanable {
    private int id;
    private int targetType;
    private int dataSize;
    private int dataType;
    private int stride;
    private int drawType;

    public VertexBufferObject(int targetType, int dataType, int dataSize, int stride, int drawType) {
        this.targetType = targetType;
        this.dataSize = dataSize;
        this.dataType = dataType;
        this.stride = stride;
        this.drawType = drawType;
        this.id = glGenBuffers();
        MemoryManager.getVertexBufferObjects().add(this);
    }

    public VertexBufferObject(int targetType, int dataType, int dataSize, int stride) {
        this(targetType, dataType, dataSize, stride, GL_STATIC_DRAW);
    }

    public VertexBufferObject(int targetType, int dataType, int dataSize) {
        this(targetType, dataType, dataSize, 0);
    }

    public VertexBufferObject(int targetType, int dataType) {
        this(targetType, dataType, 3);
    }

    public static void unbind(int target) {
        glBindBuffer(target, 0);
    }

    public void unbind() {
        unbind(targetType);
    }

    public void bind(int target) {
        glBindBuffer(target, id);
    }

    public void bind() {
        bind(targetType);
    }

    public void fill(int[] data, int drawType) {
        bind();
        glBufferData(targetType, data, drawType);
        unbind();
    }

    public void fill(float[] data, int drawType) {
        bind();
        glBufferData(targetType, data, drawType);
        unbind();
    }

    public void fill(FloatBuffer data, int drawType) {
        bind();
        glBufferData(targetType, data, drawType);
        unbind();
    }

    public void fill(IntBuffer data, int drawType) {
        bind();
        glBufferData(targetType, data, drawType);
        unbind();
    }

    public void fill(int[] data) {
        fill(data, drawType);
    }

    public void fill(float[] data) {
        fill(data, drawType);
    }

    public void fill(FloatBuffer data) {
        fill(data, drawType);
    }

    public void fill(IntBuffer data) {
        fill(data, drawType);
    }

    @Override
    public void clean() {
        glDeleteBuffers(id);
        MemoryManager.getVertexBufferObjects().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexBufferObject that = (VertexBufferObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
