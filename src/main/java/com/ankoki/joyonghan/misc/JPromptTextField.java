package com.ankoki.joyonghan.misc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JPromptTextField extends JTextField {

	private final String prompt;
	private boolean isWritten;

	public JPromptTextField(String prompt) {
		super(prompt);
		this.prompt = prompt;
		this.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				this.tick();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				this.tick();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				this.tick();
			}

			public void tick() {
				isWritten = !getText().equals(prompt);
			}

		});
		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (!isWritten)
					setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().trim().isEmpty())
					setText(prompt);
			}

		});
	}

	@Override
	public String getText() {
		String actual = super.getText();
		return actual.equals(this.prompt) ? "" : super.getText();
	}

}
