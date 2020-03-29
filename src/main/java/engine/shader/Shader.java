package engine.shader;

import engine.buffer.ICleanable;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.lwjgl.opengl.GL40.*;

@Data
public class Shader implements ICleanable {
    private static final String DEFAULT_SHADER_DIRECTORY = "./shader/";

    private int id;
    private String file;
    private int type;

    public Shader(String file, int type) {
        this.file = file;
        this.id = glCreateShader(type);
    }

    private String loadFileAsString() throws IOException {
        return Files.readString(Path.of(DEFAULT_SHADER_DIRECTORY + file));
    }

    public void load() {
        try {
            String shaderText = loadFileAsString();
            glShaderSource(id, shaderText);
            glCompileShader(id);
            if(glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                throw new RuntimeException("Cannot compile shader " + file);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void clean() {
        glDeleteShader(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shader shader = (Shader) o;
        return id == shader.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
