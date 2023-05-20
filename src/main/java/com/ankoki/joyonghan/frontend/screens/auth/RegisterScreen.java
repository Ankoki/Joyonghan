package com.ankoki.joyonghan.frontend.screens.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.auth.Account;
import com.ankoki.joyonghan.auth.AuthAssistant;
import com.ankoki.joyonghan.auth.AuthResult;
import com.ankoki.joyonghan.frontend.Screen;
import com.ankoki.joyonghan.frontend.screens.MainScreen;
import com.ankoki.joyonghan.frontend.screens.home.HomeScreen;
import com.ankoki.joyonghan.misc.JTextFieldPlus;
import com.ankoki.joyonghan.misc.Misc;
import com.ankoki.sakura.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterScreen extends Screen {

	public RegisterScreen(JFrame parent) {
		super(parent);
		// TITLE
		JLabel title = new JLabel("조용한");
		title.setFont(new Font("Monospaced", Font.BOLD, 175));
		title.setBounds(475, 50, 800, 300);
		title.setForeground(new Color(43, 43, 43));
		// TRANQUILITY
		JLabel tranquil = new JLabel("JO·YONG·HAN        TRANQUILITY");
		tranquil.setFont(new Font("Monospaced", Font.PLAIN, 25));
		tranquil.setBounds(480, 160, 800, 300);
		tranquil.setForeground(new Color(23, 23, 23));
		// WEE BLACK BOX
		JLabel box = new JLabel("■");
		box.setFont(new Font("Monospaced", Font.PLAIN, 25));
		box.setBounds(695, 158, 800, 300);
		box.setForeground(new Color(43, 43, 43));
		// EMAIL FIELD
		JTextFieldPlus email = new JTextFieldPlus("EMAIL");
		email.setHorizontalAlignment(JTextField.CENTER);
		email.setForeground(new Color(255, 255, 255));
		email.setBackground(new Color(75, 87, 78));
		email.setCursor(TEXT_CURSOR);
		email.setBounds(625, 400, 175, 50);
		// USERNAME FIELD
		JTextFieldPlus username = new JTextFieldPlus("USERNAME");
		username.setHorizontalAlignment(JTextField.CENTER);
		username.setForeground(new Color(255, 255, 255));
		username.setBackground(new Color(75, 87, 78));
		username.setCursor(TEXT_CURSOR);
		username.setBounds(625, 465, 175, 50);
		// PASSWORD FIELD TODO verify password is atleast 8 characters
		JPasswordField password = new JPasswordField("PASSWORD");
		password.setHorizontalAlignment(JTextField.CENTER);
		password.setForeground(new Color(255, 255, 255));
		password.setBackground(new Color(75, 87, 78));
		password.setCursor(TEXT_CURSOR);
		password.setBounds(625, 530, 175, 50);
		// ERROR LABEL
		JLabel error = new JLabel("", JLabel.CENTER);
		error.setForeground(new Color(255, 255, 255));
		error.setFont(new Font("Monospaced", Font.BOLD, 15));
		error.setBounds(315, 665, 800, 50);
		// LOGIN BUTTON
		JButton register = new JButton("Register");
		register.setBackground(new Color(75, 87, 78));
		register.setBounds(640, 600, 150, 50);
		register.setCursor(HAND_CURSOR);
		register.addActionListener(event -> {
			/*if (Misc.hasInternet()) {
				error.setText("You are not connected to the internet. Please check you have a valid connection.");
				return;
			}*/
			String mail = email.getText();
			String user = username.getText();
			char[] pass = password.getPassword();
			Pair<Account, AuthResult> pair = AuthAssistant.attemptRegister(mail, user, pass);
			Account account = pair.getFirst();
			if (account == null) {
				switch (pair.getSecond()) {
					case EMAIL_IN_USE -> error.setText("The given email is already registered. Did you mean to log in?");
					case INVALID_EMAIL -> error.setText("The given email is invalid. Are you sure you typed it right?");
					case USERNAME_IN_USE -> error.setText("The given username is already in use.");
					case INVALID_USERNAME -> error.setText("The given username is not valid. Make sure to use 4-16 alphanumeric characters.");
					case FAILURE -> error.setText("There was an internal error. Do you have internet? Please try again.");
				}
			} else {
				Joyonghan.getInstance().setAccount(account);
				Joyonghan.getInstance().getFrontend().showScreen(new HomeScreen(parent));
			}
		});
		// BACK BUTTON
		JButton back = null;
		try {
			Image image = ImageIO.read(Joyonghan.class.getResourceAsStream("/back.png"));
			image = image.getScaledInstance(75, 75, Image.SCALE_DEFAULT);
			back = new JButton(new ImageIcon(image));
			back.setOpaque(false);
			back.setContentAreaFilled(false);
			back.setBorderPainted(false);
			back.setSize(75, 75);
			back.setBounds(15, 15, 75, 75);
			back.setCursor(HAND_CURSOR);
			back.addActionListener(event -> Joyonghan.getInstance().getFrontend().showScreen(new MainScreen(parent)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// ADDITIONS
		this.add(title);
		this.add(tranquil);
		this.add(box);
		this.add(email);
		this.add(username);
		this.add(password);
		this.add(error);
		this.add(register);
		if (back != null)
			this.add(back);
	}

}
