package com.ankoki.joyonghan.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Screen extends ArrayList<JComponent> {

	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR), TEXT_CURSOR = new Cursor(Cursor.TEXT_CURSOR);

	private final JFrame parent;

	/**
	 * Creates a new screen with the given parent.
	 *
	 * @param parent the parent frame.
	 */
	public Screen(JFrame parent) {
		this.parent = parent;
	}

	/**
	 * Gets the parent frame of this screen.
	 *
	 * @return the frame.
	 */
	public JFrame getParent() {
		return parent;
	}

}
