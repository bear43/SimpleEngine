package engine.render;

import engine.shader.FontShader;
import engine.shader.UseShader;
import engine.gui.text.Text;
import lombok.Data;

import java.awt.*;

@Data
@UseShader(FontShader.class)
public class FPSRender implements IRender {

    private final String name = "FPSRender";

    private int counter;

    private int stableCounter;

    private long lastFrameTime;

    private Text fpsText;

    public FPSRender() {
        register();
        fpsText = new Text(Font.MONOSPACED, Font.PLAIN, 20, true, Color.RED, "");
    }

    @Override
    public void render() {
        if(System.currentTimeMillis() - lastFrameTime >= 333) {
            stableCounter = counter*3;
            counter = 0;
            lastFrameTime = System.currentTimeMillis();
        }
        fpsText.setText("FPS: " + stableCounter);
        fpsText.hide();
        fpsText.draw(10f, 30f);
        counter++;
    }
}
