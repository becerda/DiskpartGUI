package com.csuci.becerda.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csuci.becerda.volume.Volume;

public class LabelProcess extends ProcessRunner{

	
	public LabelProcess(){
		super();
		setProcess("label");
		
	}
	
	public boolean rename(Volume v, String label){
		addArg(v.getLetterColon());
		addArg(label);
		String output = run();
		return parseRename(output);
	}
	
	private boolean parseRename(String input){
		String regex = "Cannot change label\\.  This volume is write protected\\.";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()){
			return false;
		}
		return true;
	}
}
