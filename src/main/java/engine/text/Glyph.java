package engine.text;

import lombok.Data;

@Data
public class Glyph {
    private final int width;
    private final int height;
    private final int x;
    private final int y;

    public Glyph(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
}
