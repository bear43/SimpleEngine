package engine;

import engine.buffer.Loader;
import engine.camera.Camera;
import engine.display.StartHelper;
import engine.model.FontModel;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.render.*;
import engine.shader.*;
import engine.text.Text;
import engine.texture.source.TextureSourcePNG;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private static final float[] triangle = {
            -0.25f, 0.25f, 0.0f,
            -0.25f, -0.25f, 0.0f,
            0.25f, -0.25f, 0.0f,
            0.25f, 0.25f, 0.0f
    };

    private static final int[] indices = {
            0, 1, 2,
            2, 3, 0
    };

    private static final float[] texCoords = {
            0f, 0f,
            0f, 1f,
            1f, 1f,
            1f, 0f
    };

    private static RawModel triangleModel;

    private static ShaderProgram staticShader;

    private static ShaderProgram fontShader;

    public static Camera mainCamera = new Camera(
            45.0f,
            StartHelper.WIDTH,
            StartHelper.HEIGHT,
            0.001f,
            -1f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.1f, 0.0f
    );

    public static void init() {
        triangleModel = Loader.createModel("triangle", triangle, 4, indices, texCoords, new TextureSourcePNG("test.png"));
        staticShader = new StaticShader();
        staticShader.link();
        fontShader = new FontShader();
        fontShader.link();
        new FontRender();
        new StaticRender();
        new FPSRender();
    }

    public static void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        triangleModel.getTransformation().rotate(0.01f, 0.0f, 1.0f, 0.0f);
        staticShader.use();
        mainCamera.applyViewAndProjectionMatrices(staticShader);
        staticShader.drop();
        MemoryManager.getRenders().forEach(RenderProcessor::processRender);
    }
}
