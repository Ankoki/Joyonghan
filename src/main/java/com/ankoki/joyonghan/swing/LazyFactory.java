package com.ankoki.joyonghan.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LazyFactory {

	/**
	 * Creates an animated button on hover.
	 *
	 * @param name the name of the button.
	 * @return the button object.
	 */
	public static JButton createAnimatedButton(String name) {
		return LazyFactory.createAnimatedButton(name, ButtonStyle.HOME_SIDEBAR);
	}

	/**
	 * Creates an animated button on hover.
	 *
	 * @param name the name of the button.
	 * @return the button object.
	 */
	public static JButton createAnimatedButton(String name, ButtonStyle style) {
		JButton button = new JAnimatedButton(name, style);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		return button;
	}

	/**
	 * Creates a button with no border or bevel.
	 *
	 * @param name the name of the button.
	 * @return the button object.
	 */
	public static JButton createTextButton(String name) {
		JButton button = new JButton(name);
		button.setIcon(null);
		button.setBorder(null);
		button.setBorderPainted(false);
		return button;
	}

}
