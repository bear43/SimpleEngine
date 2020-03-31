package engine;

import engine.buffer.Loader;
import engine.camera.Camera;
import engine.camera.CameraMouseMotion;
import engine.display.StartHelper;
import engine.event.CameraMouseMotionEvent;
import engine.event.KeyboardEvent;
import engine.event.MouseMotionEvent;
import engine.event.ResizeEvent;
import engine.io.source.OBJLoader;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.render.*;
import engine.shader.*;
import engine.texture.source.TextureSourcePNG;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private static final float scale = 1.0f;

    private static final float[] triangle = {
            -scale, 0.00f, -scale,
            -scale, 0.00f, scale,
            scale,  0.00f, scale,
            scale,  0.00f, -scale
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

    private static TexturedModel objModel;

    public static Camera mainCamera = new Camera(
            45.0f,
            StartHelper.WIDTH,
            StartHelper.HEIGHT,
            0.001f,
            -10f,
            0.0f, 0.05f, 2.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.1f, 0.0f
    );

    public static CameraMouseMotion cameraMouseMotion = new CameraMouseMotion(mainCamera);

    public static void init() {
        RawModel groundModel = Loader.createModel("triangle", triangle, 4, indices, texCoords, new TextureSourcePNG("test.png"));
        OBJLoader objLoader = new OBJLoader();
        objLoader.load();
        objModel = new TexturedModel("test", objLoader.createVAO(), objLoader.getVerticesCount(), objLoader.getIndicesCount(), new TextureSourcePNG("cube.png"));
        objModel.getTransformation().translate(0.0f, -80.0f, -1.5f);
        ShaderProgram staticShader = new StaticShader();
        staticShader.link();
        ShaderProgram fontShader = new FontShader();
        fontShader.link();
        new FontRender();
        new StaticRender();
        new FPSRender();
        new ResizeEvent();
        new KeyboardEvent();
        new MouseMotionEvent();
        new CameraMouseMotionEvent();
    }

    public static void render() {
        glClearColor(0.6f, 0.6f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
        objModel.getTransformation().translate(0.00f, -0.1f, 0.0f);
        MemoryManager.getRenders().forEach(RenderProcessor::processRender);
    }
}
