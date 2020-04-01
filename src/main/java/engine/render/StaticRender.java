package engine.render;

import engine.MemoryManager;
import engine.Render;
import engine.model.CompositeModel;
import engine.model.IDrawable;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.shader.StaticShader;
import engine.shader.UseShader;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@UseShader
public class StaticRender implements IRender {
    private final String name = "StaticRender";

    private StaticShader shaderProgram = MemoryManager.getShaderProgramByClass(StaticShader.class);

    public StaticRender() {
        register();
    }

    @Override
    public void render() {
        List<IDrawable> models = new ArrayList<>();
        try {
            if(MemoryManager.getModels().containsKey(TexturedModel.class)) {
                models.addAll(MemoryManager.getModels().get(TexturedModel.class));
            }
            if(MemoryManager.getModels().containsKey(RawModel.class)) {
                models.addAll(MemoryManager.getModels().get(RawModel.class));
            }
            if(MemoryManager.getModels().containsKey(CompositeModel.class)) {
                models.addAll(MemoryManager.getModels().get(CompositeModel.class));
            }
        } catch (Exception ignored) {}
        if(!models.isEmpty()) {
            models.forEach(model -> {
                if(model != null) {
                    shaderProgram.applyTransformation(model);
                    Render.mainCamera.applyViewAndProjectionMatrices(shaderProgram);
                    model.draw();
                }
            });
        }
    }
}
