package engine.texture.source;

import de.matthiasmann.twl.utils.PNGDecoder;
import engine.texture.source.ITextureSource;
import lombok.Data;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
public class TextureSourcePNG implements ITextureSource {

    private static final String DEFAULT_TEXTURE_DIRECTORY = "./textures/";

    private ByteBuffer data;
    private int width;
    private int height;

    public TextureSourcePNG(String file) {
        try {
            PNGDecoder pngDecoder = new PNGDecoder(Files.newInputStream(Path.of(DEFAULT_TEXTURE_DIRECTORY + file)));
            data = ByteBuffer.allocateDirect(4 * pngDecoder.getWidth() * pngDecoder.getHeight());
            pngDecoder.decode(data, pngDecoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            data.flip();
            width = pngDecoder.getWidth();
            height = pngDecoder.getHeight();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
