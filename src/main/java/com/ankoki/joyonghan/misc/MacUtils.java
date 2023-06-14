package com.ankoki.joyonghan.misc;

import com.ankoki.joyonghan.Joyonghan;
import com.ankoki.joyonghan.frontend.MainFrame;
import com.ankoki.joyonghan.frontend.Screen;
import com.ankoki.joyonghan.frontend.screens.MainScreen;
import com.ankoki.joyonghan.frontend.screens.auth.AuthScreen;
import com.ankoki.joyonghan.frontend.screens.auth.LoginScreen;
import com.ankoki.joyonghan.frontend.screens.auth.RegisterScreen;
import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;

// TODO fix
public class MacUtils {

	/**
	 * Edit's the mac's touchbar if applicable.
	 *
	 * @param screen the screen to setup on.
	 */
	public static void showTouchbar(Screen screen) {
		// Failsafe
		if (Misc.getOperatingSystem() != OperatingSystem.MAC)
			return;
		MainFrame frontend = Joyonghan.getInstance().getFrontend();
		JTouchBar touchbar = new JTouchBar();
		touchbar.setCustomizationIdentifier("JoyonghanTouchBar");
		if (screen instanceof MainScreen) {
			TouchBarButton login = new TouchBarButton();
			login.setTitle("Login");
			login.setAction(view -> frontend.showScreen(new LoginScreen(frontend)));
			touchbar.addItem(new TouchBarItem("Login", login, true));
			TouchBarButton register = new TouchBarButton();
			register.setTitle("Register");
			register.setAction(view -> frontend.showScreen(new RegisterScreen(frontend)));
			touchbar.addItem(new TouchBarItem("Register", register, true));
		} else if (screen instanceof AuthScreen) {
			TouchBarButton home = new TouchBarButton();
			home.setTitle("Home");
			home.setAction(view -> frontend.showScreen(new MainScreen(frontend)));
		}
		touchbar.show(screen.getParent());
	}

}
