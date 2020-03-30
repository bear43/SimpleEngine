package engine;

import engine.buffer.VertexArrayObject;
import engine.buffer.VertexBufferObject;
import engine.model.FontModel;
import engine.model.RawModel;
import engine.shader.ShaderProgram;
import engine.shader.Shader;
import engine.text.Text;
import engine.texture.Texture;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemoryManager {
    private static List<VertexArrayObject> vertexArrayObjects = new ArrayList<>();
    private static List<VertexBufferObject> vertexBufferObjects = new ArrayList<>();
    private static List<Shader> shaders = new ArrayList<>();
    private static List<ShaderProgram> shaderPrograms = new ArrayList<>();
    private static List<RawModel> models = new ArrayList<>();
    private static List<Texture> textures = new ArrayList<>();
    private static List<Text> texts = new ArrayList<>();
    private static List<FontModel> fontModels = new ArrayList<>();

    public static List<VertexArrayObject> getVertexArrayObjects() {
        return vertexArrayObjects;
    }

    public static List<VertexBufferObject> getVertexBufferObjects() {
        return vertexBufferObjects;
    }

    public static List<Shader> getShaders() {
        return shaders;
    }

    public static List<ShaderProgram> getShaderPrograms() {
        return shaderPrograms;
    }

    public static List<RawModel> getModels() {
        return models;
    }

    public static List<Texture> getTextures() {
        return textures;
    }

    public static List<Text> getTexts() {
        return texts;
    }

    public static List<FontModel> getFontModels() {
        return fontModels;
    }

    public static void clean() {
        vertexArrayObjects.forEach(VertexArrayObject::clean);
        vertexBufferObjects.forEach(VertexBufferObject::clean);
        shaderPrograms.forEach(ShaderProgram::clean);
        shaders.forEach(Shader::clean);
        textures.forEach(Texture::clean);
    }
}
