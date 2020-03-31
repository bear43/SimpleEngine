package engine.event;

import engine.Render;
import engine.display.WindowEventRoot;
import engine.event.struct.IEvent;
import engine.event.struct.IEventRoot;
import org.lwjgl.opengl.GL40;

public class ResizeEvent implements IEvent, IEventRoot {

    public ResizeEvent() {
        registerAsEventHandler(WindowEventRoot.class);
        registerAsEventRoot();
    }

    @Override
    public void onEvent(Object... args) {
        GL40.glViewport(0, 0, (int)args[1], (int)args[2]);
        Render.mainCamera.updateProjectionMatrixOnResize((int)args[1], (int)args[2]);
        routeEventHandlers(args);
    }
}
