package com.ankoki.joyonghan;

import com.ankoki.joyonghan.auth.Account;
import com.ankoki.joyonghan.auth.PasswordHasher;
import com.ankoki.joyonghan.database.Database;
import com.ankoki.joyonghan.frontend.MainFrame;
import com.ankoki.joyonghan.frontend.screens.MainScreen;
import com.ankoki.joyonghan.frontend.screens.home.HomeScreen;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.joyonghan.misc.OperatingSystem;
import com.ankoki.joyonghan.misc.PaymentCalculator;
import com.ankoki.sakura.JSON.MalformedJsonException;
import com.ankoki.sakura.JSONSerializable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

// 조용한 ~ TRANQUIL
public class Joyonghan {

	private static Joyonghan instance;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy | HH:mm");

	public static Joyonghan getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Joyonghan is enabling.");
		if (Misc.getOperatingSystem() == OperatingSystem.MAC) {
			System.setProperty("apple.awt.application.appearance", "system");
			System.setProperty("Xdock:name", "JOYONGHAN");
		}
		JSONSerializable.register(Account.class);
		JSONSerializable.register(PaymentCalculator.class);
		try {
			Joyonghan.instance = new Joyonghan(new AppData(Misc.getOperatingSystem()), new MainFrame());
			Runnable destroyDialog = Joyonghan.instance.showLoadingScreen();
			Runtime.getRuntime().addShutdownHook(new Thread(() -> Joyonghan.getInstance().shutdown()));
			while (!Joyonghan.getInstance().data.isLoaded()) {}
			Object obj = Joyonghan.getInstance().data.getPersistant().get("current-account");
			MainScreen screen = new MainScreen(instance.getFrontend());
			if (!Misc.hasInternet())
				Joyonghan.getInstance().getFrontend().showScreen(screen);
			if (obj != null) {
				Map<String, Object> map = (Map<String, Object>) obj;
				if (map.get("email") != null) {
					Account account = Account.deserialize(map);
					if (account != null && account.isValid()) {
						System.out.println("Account '" + account.getUsername() + "' is valid and able to log in.");
						Joyonghan.getInstance().getFrontend().showScreen(new HomeScreen(instance.getFrontend()));
					} else
						Joyonghan.getInstance().getFrontend().showScreen(screen);
				} else
					Joyonghan.getInstance().getFrontend().showScreen(screen);
			} else
				Joyonghan.getInstance().getFrontend().showScreen(screen);
			destroyDialog.run();
			System.out.printf("Joyonghan has been enabled in %sms.\n", System.currentTimeMillis() - start);
		} catch (IOException | IllegalAccessException | MalformedJsonException ex) {
			ex.printStackTrace();
		}
	}

	private Account account;
	private final AppData data;
	private final MainFrame frontend;
	private final PasswordHasher hasher = new PasswordHasher();
	private final Database database = new Database();
	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

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
	 * Gets the scheduler linked to this instance.
	 *
	 * @return the executor.
	 */
	public ScheduledExecutorService getExecutor() {
		return executor;
	}

	/**
	 * Gets the currently logged in account.
	 *
	 * @return the account that is logged in.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the account that is currently logged in.
	 * Also stores it in the app data.
	 *
	 * @param account the new account.
	 */
	public void setAccount(Account account, boolean persistent) {
		this.account = account;
		if (account != null && persistent)
			this.data.getPersistant().put("current-account", account);
		else {
			Map<String, Object> empty = new LinkedHashMap<>();
			empty.put("username", null);
			empty.put("uuid", null);
			empty.put("email", null);
			empty.put("password-hash", null);
			this.data.getPersistant().put("current-account", empty);
		}
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

	/**
	 * Gets the database which is used in this instance.
	 *
	 * @return the database.
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * Runs all shutdown procedures for Joyonghan.
	 */
	public void shutdown() {
		long start = System.currentTimeMillis();
		try {
			System.out.println("Shutting down Joyonghan.");
			this.getDatabase().disconnect();
			this.getData().getPersistant().put("last-used", Joyonghan.DATE_FORMAT.format(new Date()));
			this.getData().saveSync();
			Joyonghan.instance = null;
			System.out.printf("Joyonghan shut down in %sms.\n", System.currentTimeMillis() - start);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Shows the loading screen.
	 *
	 * @return a runnable which deletes the screen.
	 * @throws IOException if an error occurs during reading the image.
	 */
	public Runnable showLoadingScreen() throws IOException {
		JDialog dialog = new JDialog((Frame) null);
		dialog.setModal(false);
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		InputStream stream = Joyonghan.class.getResourceAsStream("/icon.png");
		if (stream == null)
			throw new IllegalAccessError("There was an issue reading the resource /icon.png.");
		Image image = ImageIO.read(stream);
		image = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
		JLabel splash = new JLabel(new ImageIcon(image));
		splash.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		splash.setSize(100, 100);
		dialog.add(splash, BorderLayout.CENTER);
		dialog.setBackground(new Color(0, 0, 0, 0));
		dialog.pack();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - dialog.getWidth()) / 2;
		int y = (screenSize.height - dialog.getHeight()) / 2;
		dialog.setLocation(x, y);
		dialog.setVisible(true);
		return () -> {
			dialog.setVisible(false);
			dialog.dispose();
		};
	}

}
