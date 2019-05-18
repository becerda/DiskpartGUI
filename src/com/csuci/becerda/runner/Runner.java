package com.csuci.becerda.runner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.csuci.becerda.process.UpdateProcess;
import com.csuci.becerda.properties.Config;
import com.csuci.becerda.window.MainWindow;

public class Runner {

	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static void main(String[] args) {
		Config.loadConfig();
		
		if (Config.getCheckForUpdate()) {
			UpdateProcess update = new UpdateProcess();
			if (update.check()) {
				JOptionPane.showMessageDialog(null,
						"There Is An Update Available!\nTo Update Close The Program And Run The Update File!",
						"Update Available", JOptionPane.INFORMATION_MESSAGE);

			}
		}

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
			new MainWindow();
		}
	}

}
