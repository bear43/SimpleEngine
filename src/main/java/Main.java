import engine.MemoryManager;
import engine.buffer.Loader;
import engine.display.StartHelper;
import engine.model.RawModel;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
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
        staticShader.applyTransformation(triangleModel);
        triangleModel.draw();
    }

    public static void main(String[] args) {
        StartHelper.run();
        triangleModel = Loader.createModel("triangle", triangle, 4, indices, texCoords);
        staticShader = new StaticShader();
        staticShader.link();
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
