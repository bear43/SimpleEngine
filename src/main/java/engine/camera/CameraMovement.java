package engine.camera;

import lombok.Data;
import org.joml.Vector3f;

@Data
public class CameraMovement {
    private Camera camera;
    private float speed = 1.3f;

    public CameraMovement(Camera camera) {
        this.camera = camera;
    }

    private Vector3f getRightDirection() {
        return new Vector3f(camera.getDirection()).normalize().cross(camera.getUp());
    }

    private Vector3f getLeftDirection() {
        return getRightDirection().mul(-1f);
    }

    private Vector3f getForwardDirection() {
        return new Vector3f(camera.getDirection()).normalize();
    }

    private Vector3f getBackDirection() {
        return getForwardDirection().mul(-1f);
    }

    private void applyMove(Vector3f step) {
        camera.getEye().add(step.mul(speed));
        camera.updateViewMatrix();
    }

    public void move(CameraMovementMode movementMode) {
        switch (movementMode) {
            case FORWARD:
                applyMove(getForwardDirection());
                break;
            case LEFT:
                applyMove(getLeftDirection());
                break;
            case BACK:
                applyMove(getBackDirection());
                break;
            case RIGHT:
                applyMove(getRightDirection());
                break;
        }
    }
}
