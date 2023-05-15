package com.ankoki.joyonghan.frontend;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.joyonghan.misc.OperatingSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {

	/**
	 * Creates the main frame that Joyonghan relies on.
	 */
	public MainFrame() {
		System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
		System.setProperty("Xdock:name", "JOYONGHAN");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> Joyonghan.getInstance().shutdown()));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(127, 145, 131));
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
		this.repaint();
		for (JComponent component : screen)
			this.getContentPane().add(component);
		this.revalidate();
		this.setVisible(true);
		System.out.println("The screen '" + screen.getClass().getSimpleName() + "' has been shown.");
	}

}
