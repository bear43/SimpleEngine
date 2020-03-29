package engine.display;

import engine.io.KeyboardEvent;
import engine.io.MouseCursorEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;

public class StartHelper {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String title = "Simple Engine";
    private static long pWindow;

    public static long getpWindow() {
        return pWindow;
    }

    public static long createWindowInstance() {
        pWindow = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, MemoryUtil.NULL, MemoryUtil.NULL);
        return pWindow;
    }

    public static void run() {
        if(!GLFW.glfwInit()) throw new RuntimeException("Cannot start GLFW");
        createWindowInstance();
        GLFW.glfwMakeContextCurrent(pWindow);
        GLFW.glfwSetKeyCallback(pWindow, new KeyboardEvent());
        GLFW.glfwSetCursorPosCallback(pWindow, new MouseCursorEvent());
        GLFW.glfwSetWindowSizeCallback(pWindow, new WindowEvent());
        GL.createCapabilities();
        //GL40.glEnable(GL11.GL_DEPTH_TEST);
        //GL40.glEnable(GL11.GL_CULL_FACE);
        GLFW.glfwShowWindow(pWindow);
    }

}
