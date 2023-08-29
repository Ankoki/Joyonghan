package com.ankoki.joyonghan.swing;

import java.awt.*;

public enum ButtonStyle {

	HOME_SIDEBAR(new Color(127, 145, 131), new Color(43, 43, 43), new Color(93, 115, 98), new Color(129, 155, 135)),
	AUTH_SCREEN(new Color(159, 173, 163), new Color(28, 30, 28), new Color(123, 128, 125), new Color(141, 171, 153));
	// DESTRUCTIVE(new Color(255, 138, 48), new Color(238, 238, 238), new Color(198, 86, 0), new Color(255, 161, 90));

	private final Color background;
	private final Color foreground;
	private final Color backgroundHover;
	private final Color backgroundPress;

	/**
	 * A new button style with the given colours.
	 *
	 * @param background the background.
	 * @param foreground the foreground.
	 * @param backgroundHover the hover colour.
	 * @param backgroundPress the clicked colour.
	 */
	ButtonStyle(Color background, Color foreground, Color backgroundHover, Color backgroundPress) {
		this.background = background;
		this.foreground = foreground;
		this.backgroundHover = backgroundHover;
		this.backgroundPress = backgroundPress;
	}

	/**
	 * Gets the background.
	 *
	 * @return the background colour.
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Gets the foreground.
	 *
	 * @return the foreground colour.
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * Gets the background hover.
	 *
	 * @return the background hover colour.
	 */
	public Color getBackgroundHover() {
		return backgroundHover;
	}

	/**
	 * Gets the background press.
	 *
	 * @return the background press colour.
	 */
	public Color getBackgroundPress() {
		return backgroundPress;
	}

}
