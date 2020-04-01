package engine.event;

import engine.Render;
import engine.camera.CameraMovementMode;
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
            case 25://W
                Render.cameraMovement.move(CameraMovementMode.FORWARD);
                break;
            case 38://A
                Render.cameraMovement.move(CameraMovementMode.LEFT);
                break;
            case 39://S
                Render.cameraMovement.move(CameraMovementMode.BACK);
                break;
            case 40://D
                Render.cameraMovement.move(CameraMovementMode.RIGHT);
                break;
        }
    }
}
