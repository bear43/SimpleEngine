package engine.render;

import engine.shader.FontShader;
import engine.shader.UseShader;
import engine.text.Text;
import lombok.Data;
import lombok.NonNull;

import java.awt.*;

@Data
@UseShader(FontShader.class)
public class FPSRender implements IRender {

    private final String name = "FPSRender";

    private int counter;

    private long lastFrameTime;

    private Text fpsText;

    public FPSRender() {
        register();
        fpsText = new Text(Font.MONOSPACED, Font.PLAIN, 20, true, Color.RED, "");
    }

    @Override
    public void render() {
        if(System.currentTimeMillis() - lastFrameTime >= 1000) {
            fpsText.setText("FPS: " + counter);
            fpsText.hide();
            fpsText.draw(10f, 30f);
            counter = 0;
        }
        if(counter == 0) {
            lastFrameTime = System.currentTimeMillis();
        }
        counter++;
    }
}
