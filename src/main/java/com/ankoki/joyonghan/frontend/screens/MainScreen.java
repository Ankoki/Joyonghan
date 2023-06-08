package com.ankoki.joyonghan.frontend.screens;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.frontend.Screen;
import com.ankoki.joyonghan.frontend.screens.auth.LoginScreen;
import com.ankoki.joyonghan.frontend.screens.auth.RegisterScreen;
import com.ankoki.joyonghan.swing.ButtonStyle;
import com.ankoki.joyonghan.swing.LazyFactory;

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
		JLabel tranquil = new JLabel("JO·YONG·HAN        TRANQUILITY");
		tranquil.setFont(new Font("Monospaced", Font.PLAIN, 25));
		tranquil.setBounds(480, 210, 800, 300);
		tranquil.setForeground(new Color(23, 23, 23));
		// WEE BLACK BOX
		JLabel box = new JLabel("■");
		box.setFont(new Font("Monospaced", Font.PLAIN, 25));
		box.setBounds(695, 208, 800, 300);
		box.setForeground(new Color(43, 43, 43));
		// LOGIN BUTTON
		JButton login = LazyFactory.createAnimatedButton("LOGIN", ButtonStyle.AUTH_SCREEN);
		login.setBounds(625, 425, 175, 50);
		login.addActionListener(event -> Joyonghan.getInstance().getFrontend().showScreen(new LoginScreen(this.getParent())));
		// REGISTER BUTTON
		JButton register = LazyFactory.createAnimatedButton("REGISTER", ButtonStyle.AUTH_SCREEN);
		register.setBounds(625, 490, 175, 50);
		register.addActionListener(event -> Joyonghan.getInstance().getFrontend().showScreen(new RegisterScreen(this.getParent())));
		// LAST USED
		Object last = Joyonghan.getInstance().getData().getPersistant().get("last-used");
		String readable = last == null ? "First Use" : (String) last;
		JLabel used = new JLabel(readable, JLabel.RIGHT);
		used.setFont(new Font("Monospaced", Font.PLAIN, 25));
		used.setBounds(400, 580, 1000, 300);
		used.setForeground(new Color(23, 23, 23));
		// LAST USED TEXT
		JLabel lastUsed = new JLabel("<html><u style=\"text-underline-offset:8px\">Last Login</u></html>");
		lastUsed.setFont(new Font("Monospaced", Font.PLAIN, 25));
		lastUsed.setBounds(1250, 540, 1000, 300);
		lastUsed.setForeground(new Color(23, 23, 23));
		// ADDITIONS
		this.add(title);
		this.add(tranquil);
		this.add(box);
		this.add(login);
		this.add(register);
		this.add(used);
		this.add(lastUsed);
	}

}
