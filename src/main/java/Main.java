import engine.MemoryManager;
import engine.buffer.Loader;
import engine.display.StartHelper;
import engine.model.RawModel;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL40.*;

public class Main {

    private static final float[] triangle = {
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private static final int[] indices = {
            0, 1, 2,
            2, 3, 0
    };

    private static final float[] texCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    private static RawModel triangleModel;

    private static ShaderProgram staticShader;

    public static void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        triangleModel.getTransformation().rotate(0.01f, 0.0f, 1.0f, 0.0f);
        staticShader.applyTransformation(triangleModel);
        triangleModel.draw();
    }

    public static void main(String[] args) {
        StartHelper.run();
        triangleModel = Loader.createModel("triangle", triangle, 4, indices, texCoords);
        staticShader = new StaticShader();
        staticShader.link();
        Matrix4f projectionMatrix = new Matrix4f().setPerspective(90.0f, 1024.0f/768.0f, 0.5f, -100f);
        Matrix4f viewMatrix = new Matrix4f().setLookAt(0.0f, 0.00f, 1f, 0.0f, 0.0f, -0.0f, 0.0f, 0.1f, 0.0f);
        /*Matrix4f.projViewFromRectangle(
                new Vector3f(0.0f, 0.0f, 0.25f),
                new Vector3f(-0.25f, -0.25f, 0.15f),
                new Vector3f(0.125f, 0.0f, 0.0f),
                new Vector3f(0.0f, 0.125f, 0.0f),
                0.25f, false,
                projectionMatrix,
                viewMatrix
                );*/
        staticShader.applyViewMatrix(viewMatrix);
        staticShader.applyProjectionMatrix(projectionMatrix);
        while(!GLFW.glfwWindowShouldClose(StartHelper.getpWindow())) {
            staticShader.use();
            render();
            staticShader.drop();
            GLFW.glfwSwapBuffers(StartHelper.getpWindow());
            GLFW.glfwPollEvents();
        }
        MemoryManager.clean();
        GLFW.glfwDestroyWindow(StartHelper.getpWindow());
        GLFW.glfwTerminate();
    }
}
