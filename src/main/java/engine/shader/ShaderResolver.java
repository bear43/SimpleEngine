package engine.shader;

import engine.MemoryManager;
import engine.render.IRender;

import java.lang.annotation.Annotation;

public class ShaderResolver {

    public static ShaderProgram resolve(Class<? extends ShaderProgram> shaderClass) {
        return MemoryManager.getShaderProgramByClass(shaderClass);
    }

    public static Class<? extends ShaderProgram> extractFromClass(IRender render) {
        if(render != null) {
            UseShader annotation = render.getClass().getDeclaredAnnotation(UseShader.class);
            return annotation == null ? null : annotation.value();
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
