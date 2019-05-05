package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.volume.Volume;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class ROAttribButton extends BaseButton {

	private static final int WIDTH = 118;
	private static final int HEIGHT = 20;

	public static final String READONLY_BUTTON_SET = "Set Read-Only";
	public static final String READONLY_BUTTON_CLEAR = "Clear Read-Only";

	public ROAttribButton(MainWindow mw, int x, int y) {
		super(mw, "Set Read-Only", x, y, WIDTH, HEIGHT);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (mw.isValidVolume()) {
			setEnabled(false); 
			if (mw.getRO())
				setReadOnly(false);
			else
				setReadOnly(true);
		}
	}

	private void setReadOnly(boolean set) {
		Volume v = mw.getSelectedVolume();
		String sc = "";
		if (set)
			sc = "Set";
		else
			sc = "Clear";

		int ans = JOptionPane.showConfirmDialog(mw,
				"Do You Want To " + sc + " The Read-Only Flag On Volume " + v.getLetterColon() + "?",
				sc + " Read-Only" + v.getLetterColon(), JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {
			int code;
			if (set)
				code = new DiskPartProcess().setReadOnly(v) ? DiskPartProcess.PROCESS_EXIT_CODE_OK
						: DiskPartProcess.PROCESS_EXIT_CODE_ERR;
			else
				code = new DiskPartProcess().clearReadOnly(v) ? DiskPartProcess.PROCESS_EXIT_CODE_OK
						: DiskPartProcess.PROCESS_EXIT_CODE_ERR;

			if (code == DiskPartProcess.PROCESS_EXIT_CODE_OK) {
				JOptionPane.showMessageDialog(mw,
						"Successfully " + (set ? sc : sc + "ed") + " Read-Only Flag On Volume " + v.getLetterColon(),
						sc + " Read-Only " + v.getLetterColon(), JOptionPane.INFORMATION_MESSAGE);
				mw.updateStatus(v.getLetterColon() + " Read-Only Set");
				mw.refresh();
				if (set)
					setText(READONLY_BUTTON_CLEAR);
				else
					setText(READONLY_BUTTON_SET);
			} else
				JOptionPane.showMessageDialog(mw, "Failed To " + sc + " Read-Only Flag On Volume " + v.getLetterColon(),
						sc + " Read-Only " + v.getLetterColon(), JOptionPane.ERROR_MESSAGE);
		} else {
			setEnabled(true);
		}
	}
}
