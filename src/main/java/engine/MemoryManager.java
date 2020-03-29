package engine;

import engine.buffer.VertexArrayObject;
import engine.buffer.VertexBufferObject;
import engine.shader.ShaderProgram;
import engine.shader.Shader;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemoryManager {
    private static List<VertexArrayObject> vertexArrayObjects = new ArrayList<>();
    private static List<VertexBufferObject> vertexBufferObjects = new ArrayList<>();
    private static List<Shader> shaders = new ArrayList<>();
    private static List<ShaderProgram> shaderPrograms = new ArrayList<>();

    public static List<VertexArrayObject> getVertexArrayObjects() {
        return vertexArrayObjects;
    }

    public static List<VertexBufferObject> getVertexBufferObjects() {
        return vertexBufferObjects;
    }

    public static void clean() {
        vertexArrayObjects.forEach(VertexArrayObject::clean);
        vertexBufferObjects.forEach(VertexBufferObject::clean);
        shaderPrograms.forEach(ShaderProgram::clean);
        shaders.forEach(Shader::clean);
    }
}
