package engine.shader;

import engine.MemoryManager;
import engine.render.IRender;

public class ShaderResolver {

    public static ShaderProgram resolve(Class<? extends ShaderProgram> shaderClass) {
        for(ShaderProgram shader : MemoryManager.getShaderPrograms()) {
            if(shader.getClass().equals(shaderClass)) {
                return shader;
            }
        }
        return null;
    }

    public static Class<? extends ShaderProgram> extractFromClass(IRender render) {
        if(render != null) {
            UseShader[] useShaderAnnotations = render.getClass().getAnnotationsByType(UseShader.class);
            return useShaderAnnotations.length > 0 ? useShaderAnnotations[0].value() : null;
        } else {
            return null;
        }
    }

    public static ShaderProgram resolveFromRender(IRender render) {
        Class<? extends ShaderProgram> clazz = extractFromClass(render);
        if(clazz != null) {
            return resolve(clazz);
        } else {
            return null;
        }
    }
}
