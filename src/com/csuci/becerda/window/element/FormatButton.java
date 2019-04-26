package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.window.FormatWindow;
import com.csuci.becerda.window.MainWindow;

public class FormatButton extends JButton{

	private final int width = 90;
	private final int height = 20;
	
	public FormatButton(MainWindow mw, int x, int y){
		super("Format");
		setBounds(x, y, width, height);
		setVisible(true);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mw.isValidVolume()){
					new FormatWindow(mw, mw.getSelectedVolume());
				}
			}
		});
		mw.getContentPane().add(this);
	}
}
