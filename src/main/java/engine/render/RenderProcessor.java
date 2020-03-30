package engine.render;

import engine.shader.ShaderProgram;
import engine.shader.ShaderResolver;

public class RenderProcessor {

    public static void processRender(IRender render) {
        ShaderProgram shaderProgram = ShaderResolver.resolveFromRender(render);
        if(shaderProgram != null) {
            shaderProgram.use();
            render.render();
            shaderProgram.drop();
        } else {
            render.render();
            //throw new RuntimeException("Cannot process render: " + render.getName());
        }
    }
}
