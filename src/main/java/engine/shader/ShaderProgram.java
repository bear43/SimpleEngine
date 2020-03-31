package engine.shader;

import engine.MemoryManager;
import engine.buffer.ICleanable;
import engine.math.ITransformable;
import lombok.Data;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL40.*;

@Data
public abstract class ShaderProgram implements ICleanable {

    protected final String TRANSFORMATION_MATRIX = "transformationMatrix";
    protected final String PROJECTION_MATRIX = "projectionMatrix";
    protected final String VIEW_MATRIX = "viewMatrix";

    protected int id;
    protected Shader vertexShader;
    protected Shader fragmentShader;
    protected int transformationMatrixLocation;
    protected int projectionMatrixLocation;
    protected int viewMatrixLocation;

    public ShaderProgram() {
        MemoryManager.getShaderPrograms().add(this);
    }

    public ShaderProgram(Shader vertexShader, Shader fragmentShader) {
        this();
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        this.id = glCreateProgram();
    }

    public void link() {
        vertexShader.load();
        fragmentShader.load();
        glAttachShader(id, vertexShader.getId());
        glAttachShader(id, fragmentShader.getId());
        glLinkProgram(id);
        glValidateProgram(id);
        bindAttributes();
        getUniformLocations();
    }

    public void use() {
        glUseProgram(id);
    }

    public void drop() {
        glUseProgram(0);
    }

    public void bindAttribute(int index, String name) {
        glBindAttribLocation(id, index, name);
    }

    protected abstract void bindAttributes();

    protected void getUniformLocations() {
        transformationMatrixLocation = getUniformLocation(TRANSFORMATION_MATRIX);
        projectionMatrixLocation = getUniformLocation(PROJECTION_MATRIX);
        viewMatrixLocation = getUniformLocation(VIEW_MATRIX);
    }

    public int getUniformLocation(String uniformName) {
        return glGetUniformLocation(id, uniformName);
    }

    public void loadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    public void loadInt(int location, int value) {
        glUniform1i(location, value);
    }

    public void loadVector3f(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public void loadMatrix3f(int location, boolean transpose, Matrix3f matrix) {
        glUniformMatrix3fv(location, transpose, matrix.get(new float[9]));
    }

    public void loadMatrix4f(int location, boolean transpose, Matrix4f matrix) {
        glUniformMatrix4fv(location, transpose, matrix.get(new float[16]));
    }

    public void loadBoolean(int location, boolean value) {
        glUniform1f(location, value ? 1.0f : 0.0f);
    }

    public void applyTransformation(ITransformable transformable) {
        loadMatrix4f(transformationMatrixLocation, false, transformable.getTransformation());
    }

    public void applyProjectionMatrix(Matrix4f projectionMatrix) {
        loadMatrix4f(projectionMatrixLocation, false, projectionMatrix);
    }

    public void applyViewMatrix(Matrix4f viewMatrix) {
        loadMatrix4f(viewMatrixLocation, false, viewMatrix);
    }

    @Override
    public void clean() {
        glDetachShader(id, vertexShader.getId());
        glDetachShader(id, fragmentShader.getId());
        glDeleteProgram(id);
        MemoryManager.getShaderPrograms().remove(this);
    }
}
