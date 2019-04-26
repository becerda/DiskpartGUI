package com.csuci.becerda.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessRunner {

	ArrayList<String> args;
	String processName;

	public ProcessRunner() {
		args = new ArrayList<String>();

	}

	public void setProcess(String process) {
		processName = process;
	}

	public String run(){
		try{
			ArrayList<String> commandLine = new ArrayList<String>();
			commandLine.add(processName);
			commandLine.addAll(args);
			
			ProcessBuilder pb = new ProcessBuilder(commandLine);
			final Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			return sb.toString();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public void addArg(String arg){
		args.add(arg);
	}
}
