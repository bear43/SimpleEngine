package engine.display;

import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL40;

public class WindowEvent implements GLFWWindowSizeCallbackI {
    @Override
    public void invoke(long window, int width, int height) {
        GL40.glViewport(0, 0, width, height);
    }
}
