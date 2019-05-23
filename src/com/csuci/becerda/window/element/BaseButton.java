package com.csuci.becerda.window.element;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public abstract class BaseButton extends JButton implements ActionListener{
	
	public static final int DEFAULT_WIDTH = 72;
	public static final int DEFAULT_HEIGHT = 20;
	
	protected MainWindow mw;
	
	public BaseButton(MainWindow mw, String label, int x, int y, int w, int h){
		super(label);
		setBounds(x, y, w, h);
		addActionListener(this);
		this.mw = mw;
		mw.getContentPane().add(this);
		setVisible(true);
	}
}
