package com.csuci.becerda.runner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.csuci.becerda.properties.Config;
import com.csuci.becerda.window.MainWindow;

public class Runner {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} finally {
			Config.loadConfig();
			new MainWindow();
		}
	}

}
