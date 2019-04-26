package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.csuci.becerda.process.PowerShellProcess;
import com.csuci.becerda.window.MainWindow;

public class EjectButton extends JButton {

	private final int width = 90;
	private final int height = 20;

	public EjectButton(MainWindow mw, int x, int y) {
		super("Eject");
		setBounds(x, y, width, height);
		setVisible(true);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mw.isValidVolume()) {
					if (new PowerShellProcess().ejectVolume(mw.getSelectedVolume())) {
						JOptionPane.showMessageDialog(mw, "Safely Ejected " + mw.getSelectedVolume().getLetterColon(),
								"Ejected", JOptionPane.INFORMATION_MESSAGE);
						mw.refresh();
					} else {
						JOptionPane.showMessageDialog(mw, "Failed To Eject " + mw.getSelectedVolume().getLetterColon(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mw.getContentPane().add(this);
	}

}
