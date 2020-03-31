package engine.event;

import engine.event.struct.IEvent;
import engine.io.KeyboardEventRoot;
import org.lwjgl.glfw.GLFW;

public class KeyboardEvent implements IEvent {

    public KeyboardEvent() {
        registerAsEventHandler(KeyboardEventRoot.class);
    }

    @Override
    public void onEvent(Object... args) {
        switch((int)args[2]) {
            case 9:
                GLFW.glfwSetWindowShouldClose((long)args[0], true);
                break;
        }
    }
}
