package engine.render;

import engine.MemoryManager;
import engine.Render;
import engine.math.ITransformable;
import engine.model.FontModel;
import engine.model.IModel;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
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
        List<IModel> models = new ArrayList<>();
        try {
            models.addAll(MemoryManager.getModels().get(TexturedModel.class));
            models.addAll(MemoryManager.getModels().get(RawModel.class));
        } catch (Exception ignored) {}
        if(!models.isEmpty()) {
            models.forEach(model -> {
                shaderProgram.applyTransformation(model);
                Render.mainCamera.applyViewAndProjectionMatrices(shaderProgram);
                model.draw();
            });
        }
    }
}
