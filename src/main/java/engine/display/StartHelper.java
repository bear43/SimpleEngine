package engine.display;

import engine.io.KeyboardEventRoot;
import engine.io.MouseCursorEventRoot;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryUtil;

public class StartHelper {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
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
        GLFW.glfwSetKeyCallback(pWindow, new KeyboardEventRoot());
        GLFW.glfwSetCursorPosCallback(pWindow, new MouseCursorEventRoot());
        GLFW.glfwSetWindowSizeCallback(pWindow, new WindowEventRoot());
        GLFW.glfwSwapInterval(1);
        GL.createCapabilities();
        GL40.glEnable(GL11.GL_DEPTH_TEST);
        GL40.glEnable(GL11.GL_CULL_FACE);
        //GLFW.glfwSetInputMode(pWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwShowWindow(pWindow);
    }

}
