package engine.texture.source;

import java.nio.ByteBuffer;

public interface ITextureSource {
    ByteBuffer getData();
    int getWidth();
    int getHeight();
}
