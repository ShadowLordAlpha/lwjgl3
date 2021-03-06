/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.demo.system.jawt;

import org.lwjgl.system.Platform;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/** AWT integration demo using jawt. */
public final class JAWTDemo {

	private JAWTDemo() {
	}

	public static void main(String[] args) {
		if ( Platform.get() != Platform.WINDOWS )
			throw new UnsupportedOperationException("This demo can only run on Windows.");

		final LWJGLCanvas canvas = new LWJGLCanvas();
		canvas.setSize(640, 480);

		final JFrame frame = new JFrame("JAWT Demo");

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				canvas.destroy();
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
					frame.dispose();
					return true;
				}

				return false;
			}
		});

		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

}