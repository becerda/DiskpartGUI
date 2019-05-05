package com.csuci.becerda.runner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.csuci.becerda.window.MainWindow;

public class Runner {

	public static void main(String[] args) {

		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo look : looks){
			System.out.println(look.getClassName());
		}
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			new MainWindow();
		}
	}

}
