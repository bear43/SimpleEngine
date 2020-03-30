package engine.shader;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

@EqualsAndHashCode(callSuper = true)
@Data
public class FontShader extends ShaderProgram {

    public FontShader() {
        super(
                new Shader("fontVertex.glsl", GL_VERTEX_SHADER),
                new Shader("fontFragment.glsl", GL_FRAGMENT_SHADER)
        );
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "texCoords");
    }
}
