package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;

import com.csuci.becerda.window.FormatWindow;
import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class FormatButton extends BaseButton{
	
// Constants
	private static final String TITLE = "Format";

	public FormatButton(MainWindow mw, int x, int y){
		super(mw, TITLE, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(mw.isValidVolume()){
			new FormatWindow(mw, mw.getSelectedVolume());
		}
	}
}
