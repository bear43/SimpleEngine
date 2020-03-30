import engine.MemoryManager;
import engine.Render;
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

    public static void main(String[] args) {
        StartHelper.run();
        Render.init();
        while(!GLFW.glfwWindowShouldClose(StartHelper.getpWindow())) {
            Render.render();
            GLFW.glfwSwapBuffers(StartHelper.getpWindow());
            GLFW.glfwPollEvents();
        }
        MemoryManager.clean();
        GLFW.glfwDestroyWindow(StartHelper.getpWindow());
        GLFW.glfwTerminate();
    }
}
