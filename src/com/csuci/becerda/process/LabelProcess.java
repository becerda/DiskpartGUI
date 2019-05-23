package com.csuci.becerda.process;

import com.csuci.becerda.volume.Volume;

public class LabelProcess extends ProcessRunner {

	private final String PROCESS_NAME = "label";
	private final String PR_REGEX = "Cannot change label\\.  This volume is write protected\\.";

	public LabelProcess() {
		super();
		setProcess(PROCESS_NAME);
	}

	public boolean rename(Volume v, String label) {
		addArg(v.getLetterColon());
		addArg(label);
		String output = run();
		return matchOutput(output, PR_REGEX);
	}
}
