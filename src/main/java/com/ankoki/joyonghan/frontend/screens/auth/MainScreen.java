package com.ankoki.joyonghan.frontend.screens.auth;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.frontend.Screen;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends Screen {

	public MainScreen(JFrame parent) {
		super(parent);
		parent.setLayout(null);
		// TITLE
		JLabel title = new JLabel("조용한");
		title.setFont(new Font("Monospaced", Font.BOLD, 175));
		title.setBounds(475, 100, 800, 300);
		title.setForeground(new Color(43, 43, 43));
		// TRANQUILITY
		JLabel tranquil = new JLabel("JO·YONG·HAN    ■   TRANQUILITY");
		tranquil.setFont(new Font("Monospaced", Font.PLAIN, 25));
		tranquil.setBounds(480, 210, 800, 300);
		tranquil.setForeground(new Color(23, 23, 23));
		// LOGIN BUTTON
		JButton login = new JButton("LOGIN");
		login.setBackground(new Color(75, 87, 78));
		login.setBounds(625, 425, 175, 50);
		login.addActionListener(button -> Joyonghan.getInstance().getFrontend().showScreen(new LoginScreen(this.getParent())));
		// REGISTER BUTTON
		JButton register = new JButton("REGISTER");
		register.setBackground(new Color(75, 87, 78));
		register.setBounds(625, 490, 175, 50);
		register.addActionListener(button -> Joyonghan.getInstance().getFrontend().showScreen(new RegisterScreen(this.getParent())));
		this.add(title);
		this.add(tranquil);
		this.add(login);
		this.add(register);
	}

}
