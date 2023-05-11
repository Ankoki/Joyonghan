package com.ankoki.joyonghan.frontend;

import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.joyonghan.misc.Misc.OperatingSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {

	public MainFrame() {
		System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
		System.setProperty("Xdock:name", "JOYONGHAN");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.decode("#7F9183"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/dock.png")));
		if (Misc.getOperatingSystem() == OperatingSystem.MAC) {
			try {
				Taskbar.getTaskbar().setIconImage(ImageIO.read(this.getClass().getResource("/dock.png")));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Shows the given screen.
	 *
	 * @param screen the screen to show.
	 */
	public void showScreen(Screen screen) {
		this.getContentPane().removeAll();
		for (JComponent component : screen)
			this.getContentPane().add(component);
		this.setVisible(true);
	}

}
