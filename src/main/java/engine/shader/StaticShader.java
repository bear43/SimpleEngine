package engine.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(
                new Shader("vertex.shader", GL_VERTEX_SHADER),
                new Shader("fragment.shader", GL_FRAGMENT_SHADER)
        );
    }

    @Override
    public void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "texCoords");
    }

}
