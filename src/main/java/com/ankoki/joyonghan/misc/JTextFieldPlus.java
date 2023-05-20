package com.ankoki.joyonghan.misc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Class used for showing prompts which disappear when content is inputted in the field.
 */
public class JTextFieldPlus extends JTextField {

	private final JLabel prompt;

	/**
	 * Creates a new JTextFieldPlus with a prompt which disappears when there is text in the button.
	 *
	 * @param prompt the prompt to show.
	 */
	public JTextFieldPlus(String prompt) {
		super();
		this.prompt = new JLabel(prompt);
		this.prompt.setVisible(true);
		this.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {}

			@Override
			public void removeUpdate(DocumentEvent e) {}

			@Override
			public void changedUpdate(DocumentEvent e) {
				JTextFieldPlus.this.prompt.setVisible(JTextFieldPlus.this.getText().isEmpty());
			}
		});
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (this.prompt != null)
			this.prompt.setForeground(fg);
	}

	@Override
	public void setHorizontalAlignment(int alignment) {
		super.setHorizontalAlignment(alignment);
		if (this.prompt != null)
			this.prompt.setHorizontalAlignment(alignment);
	}

	@Override
	public void setCursor(Cursor cursor) {
		super.setCursor(cursor);
		if (this.prompt != null)
			this.prompt.setCursor(cursor);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		if (this.prompt != null)
			this.prompt.setSize(width, height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (this.prompt != null)
			this.prompt.setBounds(x, y, width, height);
	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if (this.prompt != null)
			this.prompt.setVisible(aFlag);
	}

}
