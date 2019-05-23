package com.csuci.becerda.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.csuci.becerda.properties.Config;

public class UpdateProcess extends ProcessRunner {

	private final String PROCESS_NAME = "cmd.exe";
	private final String PROCESS_ARG = "/c";

	private final String UPDATE_URL = "https://github.com/becerda/DiskpartGUI";
	private final String UPDATE_CHECK_REGEX = "GUI for Windows Diskpart( |\n)Version: (?<version>[0-9].[0-9]+):(?<vernum>[0-9]+)";
	private final String UPDATE_REGEX = "Successfully Downloaded Version ([0-9]+.[0-9]+)";

	private String command_1 = "powershell.exe -noprofile -executionpolicy bypass -file .\\dpguips.ps1 -new_version ";
	private String command_2 = " -old_version ";

	private String newVersion;

	public UpdateProcess() {
		super();
	}

	public boolean check() {
		try {
			URL u = new URL(UPDATE_URL);
			URLConnection conn = u.openConnection();
			BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String text = r.lines().collect(Collectors.joining("\n"));
			return parseVersion(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean parseVersion(String input) {
		Pattern p = Pattern.compile(UPDATE_CHECK_REGEX);
		Matcher m = p.matcher(input);
		String checkedVersion = "";
		if (m.find()) {
			checkedVersion = m.group("vernum");
			newVersion = m.group("version");
		}
		if (checkedVersion.length() == 0)
			return false;

		int uver = Integer.parseInt(checkedVersion);

		if (uver > Config.VERSION_NUM) {
			return true;
		}
		return false;
	}

	public boolean update() {
		setProcess(PROCESS_NAME);
		addArg(PROCESS_ARG);
		addArg(command_1 + newVersion + command_2 + Config.ACTUAL_VERSION);
		String output = run();
		return matchOutput(output, UPDATE_REGEX);
	}

	public String getNewVersion() {
		return newVersion;
	}
}
