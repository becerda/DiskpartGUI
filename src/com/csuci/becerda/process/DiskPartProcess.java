package com.csuci.becerda.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csuci.becerda.volume.Volume;

public class DiskPartProcess extends ProcessRunner {
	
	public static final int PROCESS_EXIT_CODE_OK = 0;
	public static final int PROCESS_EXIT_CODE_ERR = 1;

	private ArrayList<String> scriptCommands;
	private File script;
	private String scriptName;

	public DiskPartProcess() {
		super();
		setProcess("diskpart");
		addArg("/s");
		scriptCommands = new ArrayList<String>();

	}

	private void addToScript(String line) {
		scriptCommands.add(line);
	}

	private void writeScript() {
		try {
			script = File.createTempFile("dpgscript", ".txt");
			script.deleteOnExit();

			scriptName = script.getAbsolutePath();

			BufferedWriter br = new BufferedWriter(new FileWriter(script));
			for (String s : scriptCommands) {
				br.write(s + "\n");
			}
			br.close();
			addArg(scriptName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean setReadOnly(Volume v){
		return setReadOnly(v, true);
	}
	
	public boolean clearReadOnly(Volume v){
		return setReadOnly(v, false);
	}
	
	private boolean setReadOnly(Volume v, boolean set){
		addToScript("SELECT DISK " + v.getNumber());
		addToScript("ATTRIBUTE DISK " + (set ? "SET" : "CLEAR") + " READONLY");
		writeScript();
		String output = run();
		return parseReadOnly(output);
	}
	
	private boolean parseReadOnly(String input){
		String regex = "Disk attributes (set|cleared) successfully.";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()){
			return true;
		}
		return false;
	}

	public boolean format(Volume v, FormatOptions fo) {
		addToScript("SELECT VOLUME " + v.getNumber());
		StringBuilder sb = new StringBuilder();
		sb.append("FORMAT ");
		if (!fo.isFsDefault()) {
			if (fo.isFsNTSF()) {
				sb.append("FS=NTSF ");
			} else if (fo.isFsexFAT()) {
				sb.append("FS=exFAT ");
			} else if (fo.isFsFAT32()) {
				sb.append("FS=FAT32 ");
			}
		}
		if (!fo.isUnitSizeDefault()) {
			sb.append("UNIT=" + fo.getUnit() + " ");
		}
		if (fo.isLabelSet()) {
			sb.append("LABEL=" + fo.getLabel() + " ");
		}
		if (fo.isQuick()) {
			sb.append("QUICK");
		}
		addToScript(sb.toString());
		writeScript();
		String output = run();
		script.delete();
		return parseFormat(output);
	}

	private boolean parseFormat(String input) {
		String regex = "DiskPart successfully formatted the volume.";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public ArrayList<Boolean> getAttributes(ArrayList<Volume> vols) {
		for (Volume v : vols) {
			addToScript("select disk " + v.getNumber());
			addToScript("attribute disk");
		}
		writeScript();
		String output = run();
		script.delete();
		return parseAttributes(output);
	}

	private ArrayList<Boolean> parseAttributes(String input) {
		String regex = "Read-only  : (?<set>Yes|No)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		ArrayList<Boolean> attrs = new ArrayList<Boolean>();
		while (m.find()) {
			attrs.add(m.group("set").equals("Yes") ? true : false);
		}
		return attrs;
	}

	public ArrayList<Volume> getVolumes() {
		addToScript("list volume");
		writeScript();
		String output = run();
		script.delete();
		return parseVolumes(output);
	}

	private ArrayList<Volume> parseVolumes(String input) {
		String regex = "Volume (?<volnum>[0-9]+)( ){4,5}(?<vollet>[A-Z])( ){3}(?<vollab>[a-zA-Z ]{0,10})( ){3}(?<volfs>NTFS|FAT32|exFAT)( ){2,4}(?<voltype>Partition|Removable)( ){4,5}(?<volsize>[1-9]{1,3})( )(?<volgk>K|G)B( ){2}(?<volstat>Healthy)( ){0,4}(?<volinfo>[a-zA-Z]+)?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		ArrayList<Volume> vols = new ArrayList<Volume>();
		while (m.find()) {
			Volume v = new Volume();
			v.setNumber(Integer.parseInt(m.group("volnum")));
			v.setLetter(m.group("vollet").charAt(0));
			v.setLabel(m.group("vollab"));
			v.setFs(m.group("volfs"));
			v.setType(m.group("voltype"));
			v.setSize(Integer.parseInt(m.group("volsize")));
			v.setGk(m.group("volgk").charAt(0));
			v.setStatus(m.group("volstat"));
			v.setInfo(m.group("volinfo"));
			vols.add(v);
		}

		return vols;
	}
}
