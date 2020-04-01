package engine.render;

import engine.MemoryManager;
import engine.model.FontModel;
import engine.model.IDrawable;
import engine.shader.FontShader;
import engine.shader.UseShader;
import lombok.Data;

import java.util.List;

@Data
@UseShader(FontShader.class)
public class FontRender implements IRender {

    private final String name = "FontRender";

    private FontShader shaderProgram = MemoryManager.getShaderProgramByClass(FontShader.class);

    public FontRender() {
        register();
    }

    @Override
    public void render() {
        List<IDrawable> models = MemoryManager.getModels().get(FontModel.class);
        if(models != null && !models.isEmpty()) {
            models.forEach(model -> {
                shaderProgram.applyTransformation(model);
                model.draw();
            });
        }
    }
}
