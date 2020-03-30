package engine.text;

import engine.MemoryManager;
import engine.Render;
import engine.buffer.Loader;
import engine.camera.Camera;
import engine.model.FontModel;
import engine.model.TexturedModel;
import engine.texture.Texture;
import engine.texture.source.ITextureSource;
import engine.texture.source.TextureSourceBufferedImage;
import lombok.Data;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class Font {

    private java.awt.Font font;
    private int fontHeight;
    private boolean antiAlias;
    private Color color;

    private int imageWidth;
    private int imageHeight;

    private String fontTitle;
    private int style;
    private int size;

    private Texture atlasTexture;

    private Map<Character, Glyph> glyphs = new HashMap<>();

    public Font(String font, int style, int size, boolean antiAlias, Color color) {
        this.fontTitle = font;
        this.style = style;
        this.size = size;
        this.font = new java.awt.Font(font, style, size);
        this.antiAlias = antiAlias;
        this.color = color;
        evaluateFontAtlasWidthAndHeight();
        fillGlyphs();
    }

    private void evaluateFontAtlasWidthAndHeight() {
        for (int i = 32; i < 127; i++) {
            if (i == 127) {
                continue;
            }
            char character = (char) i;
            BufferedImage ch = createCharImage(character);

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;
    }

    private BufferedImage createCharImage(char character) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();
        int charWidth = metrics.charWidth(character);
        int charHeight = metrics.getHeight();
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(color);
        g.drawString(String.valueOf(character), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    private void fillGlyphs() {
        int x = 0;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        for (int i = 32; i < 127; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(c);

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight);
            g.drawImage(charImage, x, 0, null);
            x += ch.getWidth();
            glyphs.put(c, ch);
        }
        atlasTexture = new Texture(new TextureSourceBufferedImage(image));
    }

    boolean mayService(String fontTitle, int style, int size, boolean antiAlias, Color color) {
        return
                this.fontTitle.equals(fontTitle) &&
                this.style == style &&
                this.size == size &&
                this.antiAlias == antiAlias &&
                this.color.equals(color);
    }

    FontModel createCharacterModel(Character character, float x, float y, float x2, float y2) {
        var glyph = glyphs.get(character);
        if(glyph == null) {
            throw new RuntimeException("Cannot recognize this symbol: " + character);
        } else {
            Vector2f normalizedLeftTop = Render.mainCamera.normalizedRelativeToWidthAndHeight(x, y);
            Vector2f normalizedRightBottom = Render.mainCamera.normalizedRelativeToWidthAndHeight(x2, y2);
            Vector2f normalizedGlyphWidthAndHeight = Render.mainCamera.normalizedRelativeToWidthAndHeight(glyph.getWidth(), glyph.getHeight());
            /*
                -------
                |     |
                |     |
                |     |
                -------
                x, y
                x, y+height
                x+width, y+height,
                x+width, y

            */
            Vector2f normalizedTexCoords1 = normalizedRelativeToWidthAndHeight(glyph.getX(), glyph.getY());
            Vector2f normalizedTexCoords2 = normalizedRelativeToWidthAndHeight(glyph.getWidth(), glyph.getHeight());
            return  Loader.createFontModel(String.format("text ch %c", character), new float[] {
                    normalizedLeftTop.x, normalizedLeftTop.y, 0.0f,
                    normalizedRightBottom.x-normalizedGlyphWidthAndHeight.x, normalizedRightBottom.y, 0.0f,
                    normalizedRightBottom.x, normalizedRightBottom.y, 0.0f,
                    normalizedRightBottom.x, normalizedRightBottom.y - normalizedGlyphWidthAndHeight.y, 0.0f
            }, 4, new int[] {
                    0, 1, 2,
                    2, 3, 0
            }, new float[] {
                    normalizedTexCoords1.x, normalizedTexCoords1.y,
                    normalizedTexCoords1.x, normalizedTexCoords1.y+normalizedTexCoords2.y,
                    normalizedTexCoords1.x+normalizedTexCoords2.x, normalizedTexCoords1.y+normalizedTexCoords2.y,
                    normalizedTexCoords1.x+normalizedTexCoords2.x, normalizedTexCoords1.y
            }, atlasTexture);
        }
    }

    private Vector2f normalizedRelativeToWidthAndHeight(float x, float y) {
        return new Vector2f(x/imageWidth, y/imageHeight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Font font1 = (Font) o;
        return fontHeight == font1.fontHeight &&
                antiAlias == font1.antiAlias &&
                imageWidth == font1.imageWidth &&
                imageHeight == font1.imageHeight &&
                font.equals(font1.font) &&
                color.equals(font1.color) &&
                glyphs.equals(font1.glyphs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, fontHeight, antiAlias, color, imageWidth, imageHeight, glyphs);
    }
}
