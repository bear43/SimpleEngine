package engine.texture.source;

import engine.texture.source.ITextureSource;
import lombok.Data;

import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class TextureSourceBufferedImage implements ITextureSource {
    private ByteBuffer data;
    private int width;
    private int height;

    public TextureSourceBufferedImage(BufferedImage bufferedImage) {
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            byte[] pixelData = ((DataBufferByte) dataBuffer).getData();
            data = ByteBuffer.allocateDirect(pixelData.length);
            data = ByteBuffer.wrap(pixelData);
        }
/*        else if (dataBuffer instanceof DataBufferUShort) {
            short[] pixelData = ((DataBufferUShort) dataBuffer).getData();
            data = ByteBuffer.allocateDirect(pixelData.length * 2);
            data.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        }
        else if (dataBuffer instanceof DataBufferShort) {
            short[] pixelData = ((DataBufferShort) dataBuffer).getData();
            data = ByteBuffer.allocateDirect(pixelData.length * 2);
            data.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        }*/
        else if (dataBuffer instanceof DataBufferInt) {
            int[] pixelData = ((DataBufferInt) dataBuffer).getData();
            data = ByteBuffer.allocateDirect(pixelData.length*4);
            byte[] byteData = new byte[pixelData.length*4];
            int index = 0;
            for(int pixel : pixelData) {
                byteData[index] = (byte)((pixel >> 16) & 0xFF);
                byteData[index+1] = (byte)((pixel >> 8) & 0xFF);
                byteData[index+2] = (byte)(pixel & 0xFF);
                byteData[index+3] = (byte)((pixel >> 24) & 0xFF);
                index += 4;
            }
            data.put(byteData);
            data.flip();
        }
        else {
            throw new IllegalArgumentException("Not implemented for data buffer type: " + dataBuffer.getClass());
        }
    }
}
