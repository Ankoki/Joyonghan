package com.ankoki.joyonghan.frontend.screens.auth;

import com.ankoki.joyonghan.frontend.Screen;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends Screen {

	public MainScreen(JFrame parent) {
		super(parent);
		JLabel title = new JLabel("조용한", JLabel.CENTER);
		title.setFont(new Font("Monospaced", Font.BOLD, 100));
		title.setLocation(10, 10);
		JLabel tranquil = new JLabel("JO·YONG·HAN - TRANQUIL");
		tranquil.setFont(new Font("Monospaced", Font.PLAIN, 25));
		tranquil.setBounds(960, 550, 200, 100);
		this.add(title);
		this.add(tranquil);
	}

}
