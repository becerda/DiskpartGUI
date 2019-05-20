package com.csuci.becerda.runner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.csuci.becerda.process.UpdateProcess;
import com.csuci.becerda.properties.Config;
import com.csuci.becerda.window.AboutWindow;
import com.csuci.becerda.window.MainWindow;

public class Runner {

	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static void main(String[] args) {
		Config.loadConfig();

		try {
			UIManager.setLookAndFeel(LOOK_AND_FEEL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} finally {

			if (Config.getCheckForUpdate()) {
				UpdateProcess up = new UpdateProcess();
				if (up.check()) {
					int ans = JOptionPane.showConfirmDialog(null, AboutWindow.UPDATE_AVAILABLE,
							AboutWindow.UPDATE_AVAILABLE_TITLE, JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						if (up.update()) {
							JOptionPane.showMessageDialog(null,
									AboutWindow.SUCCESS_UPDATE_BODY_1 + up.getNewVersion()
											+ AboutWindow.SUCCESS_UPDATE_BODY_2,
									AboutWindow.SUCCESS_UPDATE, JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, AboutWindow.FAILED_UPDATE_BODY,
									AboutWindow.FAILED_UPDATE, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			new MainWindow();
		}
	}

}
