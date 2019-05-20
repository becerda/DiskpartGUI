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

	// General Strings
	private final String PROCESS_NAME = "diskpart";
	private final String CMD_ARG_SCRIPT = "/s";
	private final String SCRIPT_FILE_NAME = "dpgscript";
	private final String SCRIPT_FILE_EXT = ".txt";
	private final String SELECT_DISK = "SELECT DISK ";
	private final String ATTRIBUTE_DISK = "ATTRIBUTE DISK ";
	private final String SELECT_VOLUME = "SELECT VOLUME ";
	private final String LIST_VOLUME = "LIST VOLUME";

	// Set Read Only Strings
	private final String RO_SET = "SET";
	private final String RO_CLEAR = "CLEAR";
	private final String RO_READONLY = " READONLY";
	private final String PRO_REGEX = "Disk attributes (set|cleared) successfully.";

	// Format Strings
	private final String F_FORMAT = "FORMAT ";
	private final String F_FORMAT_NTSF = "FS=NTSF ";
	private final String F_FORMAT_EXFAT = "FS=exFAT ";
	private final String F_FORMAT_FAT32 = "FS=FAT32 ";
	private final String F_UNIT = "UNIT=";
	private final String F_LABEL = "LABEL=";
	private final String F_QUICK = "QUICK";
	private final String PF_REGEX = "DiskPart successfully formatted the volume.";

	// Attribute Strings
	private final String PA_REGEX = "Read-only  : (?<set>Yes|No)";

	// Volume Strings
	private final String PV_REGEX = "Volume (?<volnum>[0-9]+)( ){4,5}(?<vollet>[A-Z ])( ){0,3}(?<vollab>[a-zA-Z ]{0,11})( ){2,3}(?<volfs>NTFS|FAT32|exFAT| )?( ){2,7}(?<voltype>Partition|Removable)?( ){3,14}(?<volsize>[1-9]{1,4})?( )(?<volgk>K|G|M| )B( ){2}(?<volstat>Healthy)?( ){0,11}(?<volinfo>[a-zA-Z]+)?";
	private final String PV_REGEX_GROUP_VOL_NUM = "volnum";
	private final String PV_REGEX_GROUP_VOL_LET = "vollet";
	private final String PV_REGEX_GROUP_VOL_LAB = "vollab";
	private final String PV_REGEX_GROUP_VOL_FS = "volfs";
	private final String PV_REGEX_GROUP_VOL_TYPE = "voltype";
	private final String PV_REGEX_GROUP_VOL_SIZ = "volsize";
	private final String PV_REGEX_GROUP_VOL_GK = "volgk";
	private final String PV_REGEX_GROUP_VOL_STAT = "volstat";
	private final String PV_REGEX_GROUP_VOL_INFO = "volinfo";

	// Unmount Strings
	private final String U_REMOVE = "REMOVE ALL DISMOUNT";
	private final String U_ASSIGN = "ASSIGN";
	private final String PU_REGEX_U = "DiskPart successfully dismounted and offlined the volume.";
	private final String PU_REGEX_A = "DiskPart successfully assigned the drive letter or mount point.";

	private ArrayList<String> scriptCommands;
	private File script;
	private String scriptName;

	public DiskPartProcess() {
		super();
		setProcess(PROCESS_NAME);
		addArg(CMD_ARG_SCRIPT);
		scriptCommands = new ArrayList<String>();

	}

	private void addToScript(String line) {
		scriptCommands.add(line);
	}

	private void writeScript() {
		try {
			script = File.createTempFile(SCRIPT_FILE_NAME, SCRIPT_FILE_EXT);
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

	public boolean setReadOnly(Volume v) {
		return setReadOnly(v, true);
	}

	public boolean clearReadOnly(Volume v) {
		return setReadOnly(v, false);
	}

	private boolean setReadOnly(Volume v, boolean set) {
		addToScript(SELECT_DISK + v.getNumber());
		addToScript(ATTRIBUTE_DISK + (set ? RO_SET : RO_CLEAR) + RO_READONLY);
		writeScript();
		String output = run();
		return parseReadOnly(output);
	}

	private boolean parseReadOnly(String input) {
		Pattern p = Pattern.compile(PRO_REGEX);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public boolean format(Volume v, FormatOptions fo) {
		addToScript(SELECT_VOLUME + v.getNumber());
		StringBuilder sb = new StringBuilder();
		sb.append(F_FORMAT);
		if (!fo.isFsDefault()) {
			if (fo.isFsNTSF()) {
				sb.append(F_FORMAT_NTSF);
			} else if (fo.isFsexFAT()) {
				sb.append(F_FORMAT_EXFAT);
			} else if (fo.isFsFAT32()) {
				sb.append(F_FORMAT_FAT32);
			}
		}
		if (!fo.isUnitSizeDefault()) {
			sb.append(F_UNIT + fo.getUnit() + " ");
		}
		if (fo.isLabelSet()) {
			sb.append(F_LABEL + fo.getLabel() + " ");
		}
		if (fo.isQuick()) {
			sb.append(F_QUICK);
		}
		addToScript(sb.toString());
		writeScript();
		String output = run();
		script.delete();
		return parseFormat(output);
	}

	private boolean parseFormat(String input) {
		Pattern p = Pattern.compile(PF_REGEX);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public ArrayList<Boolean> getAttributes(ArrayList<Volume> vols) {
		for (Volume v : vols) {
			addToScript(SELECT_DISK + v.getNumber());
			addToScript(ATTRIBUTE_DISK);
		}
		writeScript();
		String output = run();
		script.delete();
		return parseAttributes(output);
	}

	private ArrayList<Boolean> parseAttributes(String input) {
		Pattern p = Pattern.compile(PA_REGEX);
		Matcher m = p.matcher(input);
		ArrayList<Boolean> attrs = new ArrayList<Boolean>();
		while (m.find()) {
			attrs.add(m.group("set").equals("Yes") ? true : false);
		}
		return attrs;
	}

	public ArrayList<Volume> getVolumes() {
		addToScript(LIST_VOLUME);
		writeScript();
		String output = run();
		script.delete();
		return parseVolumes(output);
	}

	private ArrayList<Volume> parseVolumes(String input) {
		Pattern p = Pattern.compile(PV_REGEX);
		Matcher m = p.matcher(input);
		ArrayList<Volume> vols = new ArrayList<Volume>();
		while (m.find()) {
			Volume v = new Volume();
			v.setNumber(Integer.parseInt(m.group(PV_REGEX_GROUP_VOL_NUM)));
			v.setLetter(m.group(PV_REGEX_GROUP_VOL_LET).charAt(0));
			v.setLabel(m.group(PV_REGEX_GROUP_VOL_LAB));
			v.setFs(m.group(PV_REGEX_GROUP_VOL_FS));
			v.setType(m.group(PV_REGEX_GROUP_VOL_TYPE));
			v.setSize(Integer.parseInt(m.group(PV_REGEX_GROUP_VOL_SIZ)));
			v.setGk(m.group(PV_REGEX_GROUP_VOL_GK).charAt(0));
			v.setStatus(m.group(PV_REGEX_GROUP_VOL_STAT));
			v.setInfo(m.group(PV_REGEX_GROUP_VOL_INFO));
			vols.add(v);
		}

		return vols;
	}

	public boolean ejectVolume(Volume v) {
		addToScript(SELECT_VOLUME + v.getNumber());
		addToScript(U_REMOVE);
		writeScript();
		String output = run();
		script.delete();
		return parseEject(output);
	}

	private boolean parseEject(String input) {
		Pattern p = Pattern.compile(PU_REGEX_U);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public boolean assignVolume(Volume v) {
		addToScript(SELECT_VOLUME + v.getNumber());
		addToScript(U_ASSIGN);
		writeScript();
		String output = run();
		return parseAssign(output);
	}

	public boolean parseAssign(String input) {
		Pattern p = Pattern.compile(PU_REGEX_A);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
