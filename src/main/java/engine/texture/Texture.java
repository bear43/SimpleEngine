package engine.texture;

import engine.MemoryManager;
import engine.buffer.ICleanable;
import engine.texture.source.ITextureSource;
import lombok.Data;

import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL45.glCreateTextures;

@Data
public class Texture implements ICleanable {

    private static final String DEFAULT_TEXTURE_DIRECTORY = "./textures/";

    private int id;
    private ITextureSource textureSource;

    public Texture() {
        this.id = glGenTextures();
    }

    public Texture(ITextureSource textureSource) {
        this();
        this.textureSource = textureSource;
        MemoryManager.getTextures().add(this);
    }

    public void freeTextureSource() {
        textureSource = null;
    }

    public void load() {
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureSource.getWidth(), textureSource.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureSource.getData());
        glGenerateMipmap(GL_TEXTURE_2D);
        unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void clean() {
        glDeleteTextures(id);
        freeTextureSource();
        MemoryManager.getTextures().remove(this);
    }
}
