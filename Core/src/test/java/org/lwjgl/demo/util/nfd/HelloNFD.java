/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.demo.util.nfd;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Platform;
import org.lwjgl.util.nfd.NFDPathSet;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.util.nfd.NativeFileDialog.*;

public final class HelloNFD {

	private HelloNFD() {
	}

	public static void main(String[] args) {
		final int mod;
		String modDescr;
		if ( Platform.get() == Platform.MACOSX ) {
			mod = GLFW_MOD_SUPER;
			modDescr = "Cmd";
		} else {
			mod = GLFW_MOD_CONTROL;
			modDescr = "Ctrl";
		}

		GLFWErrorCallback errorfun = GLFWErrorCallback.createPrint();
		GLFWKeyCallback keyfun = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if ( action == GLFW_RELEASE )
					return;

				switch ( key ) {
					case GLFW_KEY_ESCAPE:
						glfwSetWindowShouldClose(window, GLFW_TRUE);
						break;
					case GLFW_KEY_O:
						if ( (mods & mod) != 0 ) {
							if ( (mods & GLFW_MOD_SHIFT) != 0 )
								openMulti();
							else
								openSingle();
						}
						break;
					case GLFW_KEY_S:
						if ( (mods & mod) != 0 )
							save();
						break;
				}
			}
		};

		errorfun.set();
		if ( glfwInit() != GLFW_TRUE )
			throw new IllegalStateException("Unable to initialize GLFW");

		long window = glfwCreateWindow(300, 300, "Hello NativeFileDialog!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		keyfun.set(window);

		// Center window
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(
			window,
			(vidmode.width() - 300) / 2,
			(vidmode.height() - 300) / 2
		);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(1);

		System.out.println("Press " + modDescr + "+O to launch the single file open dialog.");
		System.out.println("Press " + modDescr + "+Shift+O to launch the multi file open dialog.");
		System.out.println("Press " + modDescr + "+S to launch the file save dialog.");
		while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
			glfwPollEvents();

			glClear(GL_COLOR_BUFFER_BIT);
			glfwSwapBuffers(window);
		}

		glfwDestroyWindow(window);

		glfwTerminate();

		keyfun.free();
		errorfun.free();
	}

	private static void openSingle() {
		PointerBuffer outPath = memAllocPointer(1);

		try {
			int result = NFD_OpenDialog("png,jpg;pdf", null, outPath);
			switch ( result ) {
				case NFD_OKAY:
					System.out.println("Success!");
					System.out.println(outPath.getStringUTF8(0));
					nNFD_Free(outPath.get(0));
					break;
				case NFD_CANCEL:
					System.out.println("User pressed cancel.");
					break;
				default: // NFD_ERROR
					System.err.format("Error: %s\n", NFD_GetError());
			}
		} finally {
			memFree(outPath);
		}
	}

	private static void openMulti() {
		NFDPathSet pathSet = NFDPathSet.calloc();

		try {
			int result = NFD_OpenDialogMultiple("png,jpg;pdf", null, pathSet);
			switch ( result ) {
				case NFD_OKAY:
					long count = NFD_PathSet_GetCount(pathSet);
					for ( long i = 0; i < count; i++ ) {
						String path = NFD_PathSet_GetPath(pathSet, i);
						System.out.format("Path %d: %s\n", i, path);
					}
					NFD_PathSet_Free(pathSet);
					break;
				case NFD_CANCEL:
					System.out.println("User pressed cancel.");
					break;
				default: // NFD_ERROR
					System.err.format("Error: %s\n", NFD_GetError());
			}
		} finally {
			pathSet.free();
		}
	}

	private static void save() {
		PointerBuffer savePath = memAllocPointer(1);

		try {
			int result = NFD_SaveDialog("png,jpg;pdf", null, savePath);
			switch ( result ) {
				case NFD_OKAY:
					System.out.println("Success!");
					System.out.println(savePath.getStringUTF8(0));
					nNFD_Free(savePath.get(0));
					break;
				case NFD_CANCEL:
					System.out.println("User pressed cancel.");
					break;
				default: // NFD_ERROR
					System.err.format("Error: %s\n", NFD_GetError());
			}
		} finally {
			memFree(savePath);
		}
	}

}