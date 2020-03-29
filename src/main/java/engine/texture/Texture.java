package engine.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import engine.buffer.ICleanable;
import lombok.Data;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL45.glCreateTextures;

@Data
public class Texture implements ICleanable {

    private static final String DEFAULT_TEXTURE_DIRECTORY = "./textures/";

    private int id;
    private String file;

    public Texture(String file) {
        this.file = file;
        this.id = glGenTextures();
    }

    public void load() {
        try {
            PNGDecoder pngDecoder = new PNGDecoder(Files.newInputStream(Path.of(DEFAULT_TEXTURE_DIRECTORY + file)));
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*pngDecoder.getWidth()*pngDecoder.getHeight());
            pngDecoder.decode(byteBuffer, pngDecoder.getWidth()*4, PNGDecoder.Format.RGBA);
            byteBuffer.flip();
            bind();
            glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
            glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
            glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
            glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, pngDecoder.getWidth(), pngDecoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
            glGenerateMipmap(GL_TEXTURE_2D);
            unbind();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
    }
}
