package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyboardEvent implements GLFWKeyCallbackI {

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        switch(scancode) {
            case 9:
                GLFW.glfwSetWindowShouldClose(window, true);
                break;
        }
    }
}
