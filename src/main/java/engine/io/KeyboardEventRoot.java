package engine.io;

import engine.event.struct.IEventRoot;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyboardEventRoot implements GLFWKeyCallbackI, IEventRoot {

    public KeyboardEventRoot() {
        registerAsEventRoot();
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        routeEventHandlers(window, key, scancode, action, mods);
    }
}
