package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.volume.Volume;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class UMountButton extends JButton {

	public static final String EJECT = "Eject";
	public static final String MOUNT = "Mount";

	private final int width = 90;
	private final int height = 20;

	public UMountButton(MainWindow mw, int x, int y) {
		super("Eject");
		setBounds(x, y, width, height);
		setVisible(true);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Volume v = mw.getSelectedVolume();
				if (v.isMounted()) {
					if (mw.isValidVolume()) {
						if (new DiskPartProcess().ejectVolume(mw.getSelectedVolume())) {
							JOptionPane.showMessageDialog(mw,
									"Safely Ejected " + mw.getSelectedVolume().getLetterColon(), "Ejected",
									JOptionPane.INFORMATION_MESSAGE);
							mw.refresh();
							setText(MOUNT);
						} else {
							JOptionPane.showMessageDialog(mw,
									"Failed To Eject " + mw.getSelectedVolume().getLetterColon(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if (new DiskPartProcess().assignVolume(v)) {
						JOptionPane.showMessageDialog(mw, "Volume Mounted!", "Mounted",
								JOptionPane.INFORMATION_MESSAGE);
						mw.refresh();
						setText(EJECT);
					} else {
						JOptionPane.showMessageDialog(mw, "Failed To Mount Volume",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mw.getContentPane().add(this);
	}

}
