package com.ankoki.joyonghan.frontend;

import com.ankoki.joyonghan.auth.AuthAssistant;
import com.ankoki.joyonghan.misc.MacUtils;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.joyonghan.misc.OperatingSystem;
import com.ankoki.joyonghan.swing.LazyFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {

	private final static JPanel SIDEBAR = new JPanel();

	static {
		SIDEBAR.setBackground(new Color(98, 127, 104));
		SIDEBAR.setLayout(null);
		SIDEBAR.setBounds(0, 0, 325, 1000);
		JButton account = LazyFactory.createAnimatedButton("Account");
		JButton settings = LazyFactory.createAnimatedButton("Settings");
		account.setBounds(30, 650, 250, 50);
		settings.setBounds(30, 710, 250, 50);
		account.addActionListener(e -> AuthAssistant.logOut());
		SIDEBAR.add(account);
		SIDEBAR.add(settings);
	}

	private Screen current = null;

	/**
	 * Creates the main frame that Joyonghan relies on.
	 */
	public MainFrame() {
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
		if (screen instanceof Sidebar) {
			if (!(current instanceof Sidebar)) {
				this.getContentPane().removeAll();
				screen.add(SIDEBAR);
			} else {
				for (Component component : this.getContentPane().getComponents()) {
					if (component != SIDEBAR)
						this.getContentPane().remove(component);
				}
			}
		} else
			this.getContentPane().removeAll();
		this.repaint();
		for (JComponent component : screen)
			this.getContentPane().add(component);
		this.revalidate();
		if (Misc.getOperatingSystem() == OperatingSystem.MAC)
			MacUtils.showTouchbar(screen);
		this.setVisible(true);
		this.current = screen;
		System.out.println("The screen '" + screen.getClass().getSimpleName() + "' has been shown.");
	}

}
