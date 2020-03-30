package engine.text;

import engine.Render;
import engine.buffer.Loader;
import engine.model.FontModel;
import engine.texture.Texture;
import engine.texture.source.TextureSourceBufferedImage;
import lombok.Data;
import org.joml.Vector2f;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
        for (int i = 32; i < 1270; i++) {
            if (i > 127 && i < 1000) {
                continue;
            }
            char character = (char) i;
            BufferedImage ch = createCharImage(character);
            if(ch != null) {
                imageWidth += ch.getWidth();
                imageHeight = Math.max(imageHeight, ch.getHeight());
            }
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
        if(charWidth == 0 || charHeight == 0) {
            return null;
        }
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
        for (int i = 32; i < 1270; i++) {
            if (i > 127 && i < 1000) {
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(c);

            if(charImage != null) {
                int charWidth = charImage.getWidth();
                int charHeight = charImage.getHeight();

                Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight);
                g.drawImage(charImage, x, 0, null);
                x += ch.getWidth();
                glyphs.put(c, ch);
            }
        }
/*        try {
            ImageIO.write(image, "png", Files.newOutputStream(Path.of("./textures/now.png")));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }*/
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

    FontModel createCharacterModel(Character character, float x, float y, Vector2f offset) {
        var glyph = glyphs.get(character);
        if(glyph == null) {
            if(character == '\n') {
                offset.y = offset.y + fontHeight;
                offset.x = 0f;
                return null;
            } else {
                throw new RuntimeException("Cannot recognize this symbol: " + character);
            }
        } else {
            offset.x = offset.x + glyph.getWidth();
            Vector2f displayCoordinate = Render.mainCamera.convertAbsoluteDisplayPositionToDisplayCoordinate(x, y);
            x = displayCoordinate.x;
            y = displayCoordinate.y;
            Vector2f normalizedLeftTop = Render.mainCamera.normalizedDisplayCoordinateToWidthAndHeight(x, y);
            Vector2f normalizedGlyphWidthAndHeight = Render.mainCamera.normalizedDisplayCoordinateToWidthAndHeight(glyph.getWidth(), glyph.getHeight());
            Vector2f normalizedRightBottom = new Vector2f(normalizedLeftTop.x + normalizedGlyphWidthAndHeight.x, normalizedLeftTop.y + normalizedGlyphWidthAndHeight.y);
            Vector2f normalizedTexCoords1 = normalizedRelativeToWidthAndHeight(glyph.getX(), glyph.getY());
            Vector2f normalizedTexCoords2 = normalizedRelativeToWidthAndHeight(glyph.getWidth(), glyph.getHeight());
            Vector2f
                    a = new Vector2f(normalizedLeftTop.x, normalizedLeftTop.y),
                    b = new Vector2f(normalizedRightBottom.x-normalizedGlyphWidthAndHeight.x, normalizedRightBottom.y),
                    c = new Vector2f(normalizedRightBottom.x, normalizedRightBottom.y),
                    d = new Vector2f(normalizedRightBottom.x, normalizedRightBottom.y - normalizedGlyphWidthAndHeight.y);
            Vector2f
                    texA = new Vector2f(normalizedTexCoords1.x, normalizedTexCoords1.y+normalizedTexCoords2.y),
                    texB = new Vector2f(normalizedTexCoords1.x, normalizedTexCoords1.y),
                    texC = new Vector2f(normalizedTexCoords1.x+normalizedTexCoords2.x, normalizedTexCoords1.y),
                    texD = new Vector2f(normalizedTexCoords1.x+normalizedTexCoords2.x, normalizedTexCoords1.y+normalizedTexCoords2.y);
            return  Loader.createFontModel(String.format("text ch %c", character), new float[] {
                    a.x, a.y,
                    b.x, b.y,
                    c.x, c.y,
                    d.x, d.y
            }, 4, new int[] {
                    0, 1, 2,
                    2, 3, 0
            }, new float[] {
                    texA.x, texA.y,
                    texB.x, texB.y,
                    texC.x, texC.y,
                    texD.x, texD.y
            }, atlasTexture);
        }
    }

    java.util.List<FontModel> createModels(String string, float x, float y) {
        Vector2f offset = new Vector2f();
        java.util.List<FontModel> models = new ArrayList<>();
        FontModel model;
        for(Character character : string.toCharArray()) {
            model = createCharacterModel(character, x + offset.x, y + offset.y, offset);
            if(model != null) {
                models.add(model);
            }
        }
        return models;
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
