package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;

import com.csuci.becerda.process.ProcessRunner;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class BitLockButton extends BaseButton{
	
	// Constants
	private final static String TITLE = "BitLock";
	private final String PROCESS_NAME = "control";
	private final String PROCESS_ARG_1 = "/name";
	private final String PROCESS_ARG_2 = "Microsoft.BitLockerDriveEncryption";

	public BitLockButton(MainWindow mw, int x, int y){
		super(mw, TITLE, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProcessRunner p = new ProcessRunner();
		p.setProcess(PROCESS_NAME);
		p.addArg(PROCESS_ARG_1);
		p.addArg(PROCESS_ARG_2);
		p.run();	
	}
}
