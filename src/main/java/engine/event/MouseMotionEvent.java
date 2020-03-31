package engine.event;

import engine.Render;
import engine.event.struct.IEvent;
import engine.event.struct.IEventRoot;
import engine.io.MouseCursorEventRoot;

public class MouseMotionEvent implements IEvent, IEventRoot {
    public MouseMotionEvent() {
        registerAsEventHandler(MouseCursorEventRoot.class);
        registerAsEventRoot();
    }


    @Override
    //window, xpos, ypos
    public void onEvent(Object... args) {
        double x = (double)args[1], y = (double)args[2];
        routeEventHandlers(x, y);
    }
}
