package engine.event;

import engine.Render;
import engine.event.struct.IEvent;
import engine.event.struct.IEventRoot;

public class CameraMouseMotionEvent implements IEvent {

    private double oldX, oldY;
    private boolean firstMove = true;

    public CameraMouseMotionEvent() {
        registerAsEventHandler(MouseMotionEvent.class);
    }

    @Override
    public void onEvent(Object... args) {
        double x = (double)args[0], y = (double)args[1];
        if(firstMove) {
            oldX = x;
            oldY = y;
            firstMove = false;
        } else {
            double deltaX = x-oldX;
            double deltaY = y-oldY;
            Render.cameraMouseMotion.motion(deltaX, -deltaY);
            oldX = x;
            oldY = y;
        }
    }
}
