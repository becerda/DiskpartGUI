package com.csuci.becerda.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csuci.becerda.volume.Volume;

public class LabelProcess extends ProcessRunner{
	
	private final String PROCESS_NAME = "label";
	private final String PR_REGEX = "Cannot change label\\.  This volume is write protected\\.";

	
	public LabelProcess(){
		super();
		setProcess(PROCESS_NAME);
		
	}
	
	public boolean rename(Volume v, String label){
		addArg(v.getLetterColon());
		addArg(label);
		String output = run();
		return parseRename(output);
	}
	
	private boolean parseRename(String input){
		Pattern p = Pattern.compile(PR_REGEX);
		Matcher m = p.matcher(input);
		if(m.find()){
			return false;
		}
		return true;
	}
}
