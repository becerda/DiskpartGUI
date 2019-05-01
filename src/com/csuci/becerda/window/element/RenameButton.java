package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.window.MainWindow;
import com.csuci.becerda.window.RenameWindow;

@SuppressWarnings("serial")
public class RenameButton extends JButton {

	private int width = 90;
	private int height = 20;

	public RenameButton(MainWindow mw, int x, int y) {
		super("Rename");
		setBounds(x, y, width, height);
		setVisible(true);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mw.isValidVolume())
					new RenameWindow(mw);
			}
		});
		mw.getContentPane().add(this);
	}
}
