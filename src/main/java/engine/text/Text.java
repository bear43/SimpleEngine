package engine.text;


import engine.model.FontModel;
import engine.model.TexturedModel;
import lombok.Data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Text {

    private Font font;
    private String text;
    private List<TexturedModel> models;


    private static Set<Font> fontCache = new HashSet<>();

    private static Font searchInCache(String font, int style, int size, boolean antiAlias, Color color) {
        for(Font cachedFont : fontCache) {
            if(cachedFont.mayService(font, style, size, antiAlias, color)) {
                return cachedFont;
            }
        }
        return null;
    }


    public Text(String font, int style, int size, boolean antiAlias, Color color, String text) {
        this.text = text;
        this.font = searchInCache(font, style, size, antiAlias, color);
        if(this.font == null) {
            this.font = new Font(font, style, size, antiAlias, color);
            fontCache.add(this.font);
        }
        this.models = new ArrayList<>();
    }

    public void generateModels() {
        for (char character : text.toCharArray()) {
            models.add(font.createCharacterModel(character, 480f, 300f, 620f, 500f));
        }
    }

    public void draw() {
        if(models.isEmpty()) {
            generateModels();
        }
    }

}
