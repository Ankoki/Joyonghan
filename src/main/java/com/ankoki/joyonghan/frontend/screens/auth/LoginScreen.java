package com.ankoki.joyonghan.frontend.screens.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.frontend.screens.MainScreen;
import com.ankoki.joyonghan.swing.ButtonStyle;
import com.ankoki.joyonghan.swing.JPromptPasswordField;
import com.ankoki.joyonghan.swing.JPromptTextField;
import com.ankoki.joyonghan.swing.LazyFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class LoginScreen extends AuthScreen {

	public LoginScreen(JFrame parent) {
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
		JTextField email = new JPromptTextField("EMAIL/USERNAME");
		email.setHorizontalAlignment(JTextField.CENTER);
		email.setForeground(new Color(255, 255, 255));
		email.setBackground(new Color(75, 87, 78));
		email.setCursor(TEXT_CURSOR);
		email.setBounds(625, 400, 175, 50);
		// PASSWORD FIELD
		JPasswordField password = new JPromptPasswordField("PASSWORD");
		password.setHorizontalAlignment(JTextField.CENTER);
		password.setForeground(new Color(255, 255, 255));
		password.setBackground(new Color(75, 87, 78));
		password.setCursor(TEXT_CURSOR);
		password.setBounds(625, 465, 175, 50);
		// ERROR LABEL
		JLabel error = new JLabel("", JLabel.CENTER);
		error.setForeground(new Color(255, 255, 255));
		error.setFont(new Font("Monospaced", Font.BOLD, 15));
		error.setBounds(315, 600, 800, 50);
		// LOGIN BUTTON
		JButton login = LazyFactory.createAnimatedButton("Login", ButtonStyle.AUTH_SCREEN);
		login.setBounds(640, 540, 150, 50);
		login.addActionListener(event -> super.attemptLogin(email.getText(), password.getPassword(), error));
		email.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					LoginScreen.this.attemptLogin(email.getText(), password.getPassword(), error);
				else if (event.getKeyCode() == KeyEvent.VK_TAB)
					password.requestFocus();
			}

		});
		password.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					LoginScreen.this.attemptLogin(email.getText(), password.getPassword(), error);
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
		this.add(password);
		this.add(error);
		this.add(login);
		this.add(back);
	}

}
