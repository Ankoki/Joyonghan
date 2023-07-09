package com.ankoki.joyonghan.swing;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JPromptPasswordField extends JPasswordField {

	private final char[] prompt;

	/**
	 * Creates a new password field with the given prompt to show.
	 * TODO change this to show actual text instead of hidden password characters.
	 *
	 * @param prompt the prompt.
	 */
	public JPromptPasswordField(String prompt) {
		super(prompt);
		this.prompt = prompt.toCharArray();
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (isPrompt())
					setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getPassword().length == 0)
					setText(prompt);
			}

		});
	}

	@Override
	public char[] getPassword() {
		return isPrompt() ? new char[0] : super.getPassword();
	}

	/**
	 * Checks if the current password is the prompt.
	 *
	 * @return the true if it is the prompt.
	 */
	private boolean isPrompt() {
		char[] actual = super.getPassword();
		boolean exact = true;
		if (actual.length != prompt.length)
			return false;
		for (int i = 0; i < actual.length; i++)
			exact = exact && actual[i] == prompt[i];
		return exact;
	}

}
