package engine.camera;

import engine.shader.ShaderProgram;
import lombok.Data;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

@Data
public class Camera {

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;

    private float fov;
    private float aspect;
    private float zNear;
    private float zFar;

    private Vector3f eye;//Eye position
    private Vector3f center;//Eye looks at
    private Vector3f up;//What is up relative to the eye

    private Vector3f direction;

    private int width;
    private int height;
    private float halfWidth;
    private float halfHeight;


    public Camera(
            /* Projection matrix settings */
            float fov,
            int width,
            int height,
            float zNear,
            float zFar,
            /* View matrix settings */
            Vector3f eye,
            Vector3f center,
            Vector3f up
    ) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.halfWidth = width/2.0f;
        this.halfHeight = height/2.0f;
        this.aspect = (float)width/(float)height;
        this.zNear = zNear;
        this.zFar = zFar;
        this.eye = eye;
        this.center = center;
        this.up = up;
        this.direction = this.center.sub(this.eye);
        projectionMatrix = new Matrix4f().setPerspective(fov, aspect, zNear, zFar);
        viewMatrix = new Matrix4f().setLookAt(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
    }

    public Camera(
            /* Projection matrix settings */
            float fov,
            int width,
            int height,
            float zNear,
            float zFar,
            /* View matrix settings */
            float eyeX,
            float eyeY,
            float eyeZ,
            float centerX,
            float centerY,
            float centerZ,
            float upX,
            float upY,
            float upZ
    ) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.halfWidth = width/2.0f;
        this.halfHeight = height/2.0f;
        this.aspect = (float)width/(float)height;
        this.zNear = zNear;
        this.zFar = zFar;
        this.eye = new Vector3f(eyeX, eyeY, eyeZ);
        this.center = new Vector3f(centerX, centerY, centerZ);
        this.direction = this.center.sub(this.eye);
        this.up = new Vector3f(upX, upY, upZ);
        projectionMatrix = new Matrix4f().setPerspective(fov, aspect, zNear, zFar);
        viewMatrix = new Matrix4f().setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    public void applyViewAndProjectionMatrices(ShaderProgram shaderProgram) {
        shaderProgram.applyProjectionMatrix(projectionMatrix);
        shaderProgram.applyViewMatrix(viewMatrix);
    }

    public void applyViewMatrix(ShaderProgram shaderProgram) {
        shaderProgram.applyViewMatrix(viewMatrix);
    }

    public void applyProjectionMatrix(ShaderProgram shaderProgram) {
        shaderProgram.applyProjectionMatrix(projectionMatrix);
    }

    public void updateProjectionMatrixOnResize(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfWidth = width/2.0f;
        this.halfHeight = height/2.0f;
        this.aspect = (float)width/(float)height;
        updateProjectionMatrix();
    }

    public void updateProjectionMatrix() {
        projectionMatrix.identity().perspective(fov, aspect, zNear, zFar);
    }

    public void updateViewMatrix() {
        viewMatrix.identity().lookAt(eye, center, up);
    }

    public Vector2f normalizedRelativeToWidthAndHeight(float x, float y) {
        return new Vector2f(x/width, y/height);
    }

    public Vector2f normalizedDisplayCoordinateToWidthAndHeight(float x, float y) {
        return new Vector2f(x/halfWidth, y/halfHeight);
    }

    public Vector2f convertAbsoluteDisplayPositionToDisplayCoordinate(float x, float y) {
        return new Vector2f(x-halfWidth, halfHeight-y);
    }

    private void updateViewMatrixByChangeDirection() {
        viewMatrix.identity();
        viewMatrix.lookAt(eye, new Vector3f(eye).add(direction), up);
    }

    public void setDirection(float x, float y, float z) {
        direction.x = x;
        direction.y = y;
        direction.z = z;
        updateViewMatrixByChangeDirection();
    }
}
