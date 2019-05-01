package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.process.ProcessRunner;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class BitLockButton extends JButton{

	private int width = 90;
	private int height = 20;
	
	public BitLockButton(MainWindow mw, int x, int y){
		super("BitLock");
		setBounds(x, y, width, height);
		setVisible(true);
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProcessRunner p = new ProcessRunner();
				p.setProcess("control");
				p.addArg("/name");
				p.addArg("Microsoft.BitLockerDriveEncryption");
				p.run();
			}
		});
		mw.getContentPane().add(this);
	}
}
