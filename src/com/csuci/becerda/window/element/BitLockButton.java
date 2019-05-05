package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.csuci.becerda.process.ProcessRunner;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class BitLockButton extends BaseButton{

	public BitLockButton(MainWindow mw, int x, int y){
		super(mw, "BitLock", x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProcessRunner p = new ProcessRunner();
		p.setProcess("control");
		p.addArg("/name");
		p.addArg("Microsoft.BitLockerDriveEncryption");
		p.run();	
	}
}
