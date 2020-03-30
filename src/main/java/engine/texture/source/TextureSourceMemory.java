package engine.texture.source;

import engine.texture.source.ITextureSource;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class TextureSourceMemory implements ITextureSource {
    private ByteBuffer data;
    private int width;
    private int height;

    public TextureSourceMemory(ByteBuffer data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }
}
