package com.csuci.becerda.process;

public class FormatOptions {

	public static final String FS_DEFAULT = "Default";

	public static final String[] fs = { "Default", "NTFS", "FAT32", "exFAT" };

	public final String[] NTFSunit = { "512", "1024", "2048", "4096", "8192", "16K", "32K", "64K", "128K", "256K",
			"512K", "1024K", "2048K" };

	public final String[] FAT32unit = { "4096", "8192", "16K", "32K", "64K" };

	public final String[] exFATunit = { "1024", "2048", "4096", "8192", "16K", "32K", "64K", "128K", "256K", "512K",
			"1024K", "2048K", "4096K", "8192K", "16384K", "32768K" };

	public static final String[] unitsize = { "Default", "512", "1024", "2048", "4096", "8192", "16K", "32K", "64K",
			"128K", "256K", "512K", "1024K", "2048K", "4096K", "8192K", "16384K", "32768K" };

	private String f;
	private int unit;
	private boolean quick;
	private String label;

	public FormatOptions() {
		setFsDefault();
		setUnitSizeDefault();
		setQuick(true);
		label = "";
	}

	public void setFsDefault() {
		f = fs[0];
	}

	public void setFsNTFS() {
		f = fs[1];
	}

	public void setFsFAT32() {
		f = fs[2];
	}

	public void setFsexFAT() {
		f = fs[3];
	}

	public void setUnitSize512() {
		unit = 1;
	}

	public void setUnitSize1024() {
		unit = 2;
	}

	public void setUnitSize2048() {
		unit = 3;
	}

	public void setUnitSize4096() {
		unit = 4;
	}

	public void setUnitSize8192() {
		unit = 5;
	}

	public void setUnitSize16K() {
		unit = 6;
	}

	public void setUnitSize32K() {
		unit = 7;
	}

	public void setUnitSize64K() {
		unit = 8;
	}

	public void setUnitSize128K() {
		unit = 9;
	}

	public void setUnitSize256K() {
		unit = 10;
	}

	public void setUnitSize512K() {
		unit = 11;
	}

	public void setUnitSize1024K() {
		unit = 12;
	}

	public void setUnitSize2048K() {
		unit = 13;
	}

	public void setUnitSize4096K() {
		unit = 14;
	}

	public void setUnitSize8192K() {
		unit = 15;
	}

	public void setUnitSize16384K() {
		unit = 16;
	}

	public void setUnitSize32768K() {
		unit = 17;
	}

	public void setUnitSizeDefault() {
		unit = 0;
	}

	public boolean isUnitSizeDefault() {
		return unit == 0;
	}

	public void setQuick(boolean set) {
		quick = set;
	}

	public String getFS() {
		return f;
	}

	public String getUnit() {
		return unitsize[unit];
	}

	public boolean isQuick() {
		return quick;
	}

	public boolean isFsDefault() {
		return f.equals(fs[0]);
	}

	public boolean isFsNTSF() {
		return f.equals(fs[1]);
	}

	public boolean isFsFAT32() {
		return f.equals(fs[2]);
	}

	public boolean isFsexFAT() {
		return f.equals(fs[3]);
	}

	public void setLabel(String label) {
		if (label.length() > 11) {
			this.label = label.substring(0, 11);
		} else if (label.length() == 0) {
			this.label = "";
		} else {
			this.label = label;
		}
	}

	public String getLabel() {
		return label;
	}

	public boolean isLabelSet() {
		return label.length() > 0;
	}

}
