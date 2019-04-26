package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.window.MainWindow;

public class RefreshButton extends JButton{
	
	private final int WIDTH = 80;
	private final int HEIGHT = 20;
	
	public RefreshButton(MainWindow mw, int x, int y){
		super("Refresh");
		setBounds(x, y, WIDTH, HEIGHT);
		setVisible(true);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mw.refresh();
			}
		});
		mw.getContentPane().add(this);
	}

}
