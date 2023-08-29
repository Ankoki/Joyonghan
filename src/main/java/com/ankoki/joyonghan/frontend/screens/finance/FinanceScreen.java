package com.ankoki.joyonghan.frontend.screens.finance;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.frontend.Screen;
import com.ankoki.joyonghan.frontend.Sidebar;
import com.ankoki.sakura.JSON;

import javax.swing.*;

public class FinanceScreen extends Screen implements Sidebar {

	public FinanceScreen(JFrame parent) {
		super(parent);
		// We will need quite a bit of this so may as well cache it.
		JSON data = Joyonghan.getInstance().getAccount().getData();
	}

}
