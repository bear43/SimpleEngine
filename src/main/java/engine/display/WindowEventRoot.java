package engine.display;

import engine.event.struct.IEventRoot;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public class WindowEventRoot implements GLFWWindowSizeCallbackI, IEventRoot {

    public WindowEventRoot() {
        registerAsEventRoot();
    }

    @Override
    public void invoke(long window, int width, int height) {
        routeEventHandlers(window, width, height);
    }
}
