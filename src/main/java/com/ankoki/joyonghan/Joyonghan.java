package com.ankoki.joyonghan;

import com.ankoki.joyonghan.accounts.PasswordHasher;
import com.ankoki.joyonghan.database.Database;
import com.ankoki.joyonghan.frontend.MainFrame;
import com.ankoki.joyonghan.frontend.screens.auth.MainScreen;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.JSON.MalformedJsonException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// 조용한 ~ TRANQUIL
public class Joyonghan {

	private static Joyonghan current;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Joyonghan is enabling.");
		try {
			JDialog dialog = new JDialog((Frame) null);
			dialog.setModal(false);
			dialog.setUndecorated(true);
			dialog.setLayout(new BorderLayout());
			Image image = ImageIO.read(Joyonghan.class.getResourceAsStream("/icon.png"));
			image = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
			JLabel splash = new JLabel(new ImageIcon(image));
			splash.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
			splash.setSize(200, 200);
			dialog.add(splash, BorderLayout.CENTER);
			dialog.setBackground(new Color(0, 0, 0, 0));
			dialog.pack();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screenSize = toolkit.getScreenSize();
			int x = (screenSize.width - dialog.getWidth()) / 2;
			int y = (screenSize.height - dialog.getHeight()) / 2;
			dialog.setLocation(x, y);
			dialog.setVisible(true);
			Joyonghan.current = new Joyonghan(new AppData(Misc.getOperatingSystem()), new MainFrame());
			Joyonghan.current.getFrontend().showScreen(new MainScreen(current.getFrontend()));
			dialog.setVisible(false);
			dialog.dispose();
			System.out.printf("Joyonghan has been enabled in %sms.", System.currentTimeMillis() - start);
		} catch (IOException | IllegalAccessException | MalformedJsonException ex) {
			ex.printStackTrace();
		}
	}

	private final AppData data;
	private final MainFrame frontend;
	private final PasswordHasher hasher = new PasswordHasher();
	private final Database database = new Database();

	/**
	 * Creates a new Joyonghan instance.
	 *
	 * @param data the data to represent it.
	 * @param frontend the frontend to be shown.
	 */
	public Joyonghan(AppData data, MainFrame frontend) {
		this.data = data;
		this.frontend = frontend;
	}

	/**
	 * Gets the app data which is used in this instance.
	 *
	 * @return the app data.
	 */
	public AppData getData() {
		return data;
	}

	/**
	 * Gets the frontend which is used in this instance.
	 *
	 * @return the frontend.
	 */
	public MainFrame getFrontend() {
		return frontend;
	}

	/**
	 * Gets the password hasher which is used in this instance.
	 *
	 * @return the hasher.
	 */
	public PasswordHasher getHasher() {
		return hasher;
	}

}
