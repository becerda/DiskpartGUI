package com.csuci.becerda.process;

import com.csuci.becerda.volume.Volume;

public class PowerShellProcess extends ProcessRunner {

	public PowerShellProcess() {
		super();
		setProcess("powershell");
		addArg("-Command");
	}

	public boolean ejectVolume(Volume v) {
		addArg("(New-Object -comObject Shell.Application).Namespace(17).ParseName(\\\"" + v.getLetterColon()
				+ "\\\").InvokeVerb(\\\"Eject\\\")");
		String output = run();
		return parseEject(output);
	}
	
	private boolean parseEject(String input){
		if(input.length() > 0)
			return false;
		return true;
	}

}
