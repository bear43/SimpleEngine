package engine.camera;

import lombok.Data;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Data
public class CameraMouseMotion {
    private Camera camera;

    private double xAngle;
    private double yAngle;
    private double mouseSensitivity = 0.1;

    public CameraMouseMotion(Camera camera) {
        this.camera = camera;
        convertDirectionToAngles(camera.getDirection());
    }

    private void proceedMotion(double x, double y) {
        xAngle += x;
        yAngle += y;
        if(xAngle > 360 || xAngle < -360) {
            xAngle = 0;
        }
        if(yAngle > 90) {
            yAngle = 90;
        } else if(yAngle < -45) {
            yAngle = -45;
        }
    }

    private void convertDirectionToAngles(Vector3f direction) {
        direction = new Vector3f(direction).normalize();
        float yAngleRadians = Math.asin(direction.y);
        yAngle = Math.toDegrees(yAngleRadians);
        float cosYf = Math.cos(yAngleRadians);
        xAngle = Math.toDegrees(Math.asin(direction.z/cosYf));
    }

    public void applyMotion(float x, float y, float z) {
        camera.setDirection(x, y, z);

    }


    public void motion(double x, double y) {
        x*=mouseSensitivity;
        y*=mouseSensitivity;
        proceedMotion(x, y);
        float xf = (float)Math.toRadians(xAngle);
        float yf = (float)Math.toRadians(yAngle);
        float cosXf = Math.cos(xf);
        float sinXf = Math.sin(xf);
        float sinYf = Math.sin(yf);
        float cosYf = Math.cos(yf);
        applyMotion(
                cosXf*cosYf,
                sinYf,
                sinXf*cosYf
        );
    }
}
