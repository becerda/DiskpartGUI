package com.csuci.becerda.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.csuci.becerda.process.UpdateProcess;
import com.csuci.becerda.properties.Config;

@SuppressWarnings("serial")
public class AboutWindow extends BaseWindow {

	private final static String TITLE = "Diskpart GUI";
	private final static int WIDTH = 400;
	private final static int HEIGHT = 150;

	private final String CHECK_UPDATE = "Checking For Update...";
	private final String NO_UPDATE = "No Update Found";

	public static final String SUCCESS_UPDATE = "Successfully Downloaded";
	public static final String SUCCESS_UPDATE_BODY_1 = "Close Application And Unzip Launcher_";
	public static final String SUCCESS_UPDATE_BODY_2 = ".zip, Located In Application Home Directory, To Finish Update.";
	public static final String FAILED_UPDATE = "Failed To Download";
	public static final String FAILED_UPDATE_BODY = "Failed To Update!";

	public static final String UPDATE_AVAILABLE = "There Is An Update Available! Do You Want To Download It?";
	public static final String UPDATE_AVAILABLE_TITLE = "Update Available";

	private final String BODY_LINE_1 = "Diskpart GUI Was Developed At California State University Channel Islands.";
	private final int BODY_LINE_1_W = 365;

	private final String BODY_LINE_2 = "Developed By Benjamin Cerda Under Supervision Of Michael Soltys.";
	private final int BODY_LINE_2_W = 325;

	private final String VERSION = "Current Version: ";
	private final int VERSION_W = 100;

	private final String CLOSE = "Close";
	private final int CLOSE_W = 100;

	private final String UPDATE = "Check For Update";
	private final int UPDATE_W = 120;

	private JButton update;

	public AboutWindow() {
		super(TITLE, WIDTH, HEIGHT, JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	protected void addComponents() {
		addBodyLabel();
		addVersionLabel();
		addCloseButton();
		addCheckUpdateButton();
	}

	private void addBodyLabel() {
		addLabel(BODY_LINE_1, (WIDTH / 2) - (BODY_LINE_1_W / 2), OFFSET, BODY_LINE_1_W, STANDARD_HEIGHT);
		addLabel(BODY_LINE_2, (WIDTH / 2) - (BODY_LINE_2_W / 2), OFFSET + STANDARD_HEIGHT, BODY_LINE_2_W,
				STANDARD_HEIGHT);
	}

	private void addVersionLabel() {
		addLabel(VERSION + Config.getVersion(), (WIDTH / 2) - (VERSION_W / 2), OFFSET + (STANDARD_HEIGHT * 2),
				VERSION_W, STANDARD_HEIGHT);
	}

	private void addCloseButton() {
		JButton close = addButton(CLOSE, (WIDTH / 2) - (CLOSE_W / 2), OFFSET + (STANDARD_HEIGHT * 3), CLOSE_W,
				STANDARD_HEIGHT);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutWindow.this.dispose();
			}
		});
	}

	private void addCheckUpdateButton() {
		update = addButton(UPDATE, (WIDTH / 2) - (UPDATE_W / 2), (OFFSET * 2) + (STANDARD_HEIGHT * 4), UPDATE_W,
				STANDARD_HEIGHT);
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(TITLE + TITLE_SEPERATOR + CHECK_UPDATE);
				update.setEnabled(false);
				UpdateProcess up = new UpdateProcess();
				if (up.check()) {
					int ans = JOptionPane.showConfirmDialog(AboutWindow.this, UPDATE_AVAILABLE, UPDATE_AVAILABLE_TITLE,
							JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						if (up.update()) {
							JOptionPane.showMessageDialog(AboutWindow.this,
									"Close Application And Unzip Launcher_" + up.getNewVersion()
											+ ".zip To Finish Update.",
									SUCCESS_UPDATE, JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(AboutWindow.this, "Failed To Update!", FAILED_UPDATE,
									JOptionPane.ERROR_MESSAGE);
						}
						updateStatus(TITLE);
					}
				} else {
					updateStatus(TITLE + TITLE_SEPERATOR + NO_UPDATE);
				}
				update.setEnabled(true);
			}
		});
	}

}
