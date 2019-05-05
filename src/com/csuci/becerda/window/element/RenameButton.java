package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;

import com.csuci.becerda.window.MainWindow;
import com.csuci.becerda.window.RenameWindow;

@SuppressWarnings("serial")
public class RenameButton extends BaseButton {

	public RenameButton(MainWindow mw, int x, int y) {
		super(mw, "Rename", x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (mw.isValidVolume())
			new RenameWindow(mw);
	}
}
