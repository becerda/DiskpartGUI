package com.csuci.becerda.window;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BaseWindow extends JFrame {

	protected final int STANDARD_HEIGHT = 20;
	protected final int OFFSET = 5;

	protected final String TITLE_SEPERATOR = " - ";

	public BaseWindow(String title, int w, int h, int operation) {
		super();

		setSize(w, h);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(operation);
		setTitle(title);

		addComponents();
		setVisible(true);

		revalidate();
		repaint();

	}

	protected void addComponents() {

	}

	protected JLabel addLabel(String label, int x, int y, int w, int h) {
		JLabel l = new JLabel(label);
		l.setBounds(x, y, w, h);
		add(l);
		l.setVisible(true);
		return l;
	}

	protected JButton addButton(String label, int x, int y, int w, int h) {
		JButton b = new JButton(label);
		b.setBounds(x, y, w, h);
		add(b);
		b.setVisible(true);
		return b;
	}

	protected void updateStatus(String status) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setTitle(status);
			}
		});
	}

	protected void updateFields() {

	}
}
