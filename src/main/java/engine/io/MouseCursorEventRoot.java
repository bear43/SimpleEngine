package engine.io;

import engine.event.struct.IEventRoot;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class MouseCursorEventRoot implements GLFWCursorPosCallbackI, IEventRoot {

    public MouseCursorEventRoot() {
        registerAsEventRoot();
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        routeEventHandlers(window, xpos, ypos);
    }
}
