package com.csuci.becerda.window.element;

import java.awt.event.ActionEvent;

import com.csuci.becerda.window.MainWindow;

@SuppressWarnings("serial")
public class RefreshButton extends BaseButton{
	
	// Constants
	private static final String TITLE = "Refresh";
	
	public RefreshButton(MainWindow mw, int x, int y){
		super(mw, TITLE, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.mw = mw;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mw.refresh();
	}

}
