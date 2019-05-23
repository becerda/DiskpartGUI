package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.volume.Volume;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class UMountButton extends BaseButton {

	public static final String EJECT = "Eject";
	public static final String MOUNT = "Mount";

	private final String FAILED_TITLE = "Error";
	private final String SUCCESS_EJECT_TITLE = "Ejected";
	private final String SUCCESS_EJECT_DIALOG = "Safely Ejected ";
	private final String FAILED_EJECT_DIALOG = "Failed To Eject ";
	private final String SUCCESS_MOUNT_TITLE = "Mounted";
	private final String SUCCESS_MOUNT_DIALOG = "Volume Mounted!";
	private final String FAILED_MOUNT_DIALOG = "Failed To Mount Volume!";

	public UMountButton(MainWindow mw, int x, int y) {
		super(mw, "Eject", x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Volume v = mw.getSelectedVolume();
		if (v != null) {
			if (v.isMounted()) {
				if (mw.isValidVolume()) {
					if (new DiskPartProcess().ejectVolume(mw.getSelectedVolume())) {
						JOptionPane.showMessageDialog(mw,
								SUCCESS_EJECT_DIALOG + mw.getSelectedVolume().getLetterColon(), SUCCESS_EJECT_TITLE,
								JOptionPane.INFORMATION_MESSAGE);
						mw.refresh();
						setText(MOUNT);
					} else {
						JOptionPane.showMessageDialog(mw, FAILED_EJECT_DIALOG + mw.getSelectedVolume().getLetterColon(),
								FAILED_TITLE, JOptionPane.ERROR_MESSAGE);
					}
				}
			} else { // unmounted
				if (new DiskPartProcess().assignVolume(v)) {
					JOptionPane.showMessageDialog(mw, SUCCESS_MOUNT_DIALOG, SUCCESS_MOUNT_TITLE,
							JOptionPane.INFORMATION_MESSAGE);
					mw.refresh();
					setText(EJECT);
				} else {
					JOptionPane.showMessageDialog(mw, FAILED_MOUNT_DIALOG, FAILED_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			mw.updateMainWindowStatus(MainWindow.STATUS_SELECTED_NONE);
		}
	}
}
