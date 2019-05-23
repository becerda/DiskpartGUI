package com.csuci.becerda.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.properties.Config;
import com.csuci.becerda.volume.Volume;
import com.csuci.becerda.window.element.BaseButton;
import com.csuci.becerda.window.element.BitLockButton;
import com.csuci.becerda.window.element.FormatButton;
import com.csuci.becerda.window.element.ROAttribButton;
import com.csuci.becerda.window.element.RefreshButton;
import com.csuci.becerda.window.element.RenameButton;
import com.csuci.becerda.window.element.UMountButton;
import com.csuci.becerda.window.element.VolumeTable;

@SuppressWarnings("serial")
public class MainWindow extends BaseWindow {

	// Main Window Vars
	private static final String TITLE = "Diskpart GUI";
	private static final int HEIGHT = 225;
	private static final int WIDTH = 449;

	private final int LARGE_PADDING = 15;
	private final int SMALL_PADDING = 5;
	private final int DEFAULT_BUTTON_W = BaseButton.DEFAULT_WIDTH;

	private final int DEFAULT_X = 10;
	private final int DEFAULT_Y = 10;
	private final int DEFAULT_LABEL_W = 100;
	private final int DEFAULT_LABEL_H = 20;

	// Main Window Status Vars
	private final String STATUS_FINDING_VOLS = "Finding Volumes...";
	private final String STATUS_REFRESHING = "Refreshing...";
	private final String STATUS_FAILED_FIND_VOLS = "Failed To Find Volumes";
	private final String STATUS_FOUND_1 = "Finished, Found ";
	private final String STATUS_FOUND_2 = " Volume ";
	private final String STATUS_FOUND_2S = " Volumes";
	private final String STATUS_SELECTED_VOL = "Selected Volume ";
	public static final String STATUS_SELECTED_NONE = "No Volume Selected";
	private final String STATUS_FAILED_FOUND_ATTRS = "Could Not Find Attributes";
	private final String STATUS_VOLUME_NOT_MOUNTED = "Volume Is Not Mounted";
	private final String STATUS_VOLUME_NOT_REMOVABLE = " Is Not Removable";

	// Main Window Menu Bar Vars
	private final String MENU_FILE = "File";
	private final String MENU_FILE_EXIT = "Exit";

	private final String MENU_VIEW = "View";
	private final String MENU_VIEW_SAV = "Show All Volumes";

	private final String MENU_HELP = "Help";
	private final String MENU_HELP_ABOUT = "About";
	private final String MENU_HELP_UPDATE = "Auto Check For Update";

	// Volume Label Vars
	private final String VOL_LABEL = "Volume: ";
	private final int VOL_LAB_X = DEFAULT_X;
	private final int VOL_LAB_Y = DEFAULT_Y;
	private final int VOL_LAB_W = DEFAULT_LABEL_W;
	private final int VOL_LAB_H = DEFAULT_LABEL_H;

	// Volume Table Vars
	private final String TABLE_YES = "Yes";
	private final String TABLE_NO = "No";
	private final int CB_X = VOL_LAB_X;
	private final int CB_Y = VOL_LAB_Y + VOL_LAB_H + SMALL_PADDING;
	private final int CB_W = WIDTH - LARGE_PADDING - DEFAULT_X;
	private final int CB_H = 21 * 5;

	// Refresh Button Vars
	private final int REFRESH_BUTTON_X = WIDTH - DEFAULT_BUTTON_W - LARGE_PADDING;
	private final int REFRESH_BUTTON_Y = VOL_LAB_Y;

	// Eject/Mount Button Vars
	private final int EJECT_BUTTON_X = VOL_LAB_X;
	private final int EJECT_BUTTON_Y = CB_Y + CB_H + SMALL_PADDING;
	private final int EJECT_BUTTON_W = DEFAULT_BUTTON_W;

	// Format Button Vars
	private final int FORMAT_BUTTON_X = EJECT_BUTTON_X + EJECT_BUTTON_W + SMALL_PADDING;
	private final int FORMAT_BUTTON_Y = EJECT_BUTTON_Y;
	private final int FORMAT_BUTTON_W = DEFAULT_BUTTON_W;

	// Rename Button Vars
	private final int RENAME_BUTTON_X = FORMAT_BUTTON_X + FORMAT_BUTTON_W + SMALL_PADDING;
	private final int RENAME_BUTTON_Y = EJECT_BUTTON_Y;
	private final int RENAME_BUTTON_W = DEFAULT_BUTTON_W;

	// Bitlocker Button Vars
	private final int BL_BUTTON_X = RENAME_BUTTON_X + RENAME_BUTTON_W + SMALL_PADDING;
	private final int BL_BUTTON_Y = EJECT_BUTTON_Y;

	// ReadOnly Button Vars
	private final int READONLY_BUTTON_X = BL_BUTTON_X + DEFAULT_BUTTON_W + SMALL_PADDING;
	private final int READONLY_BUTTON_Y = EJECT_BUTTON_Y;

	private VolumeTable vt;
	private JButton cattr;
	private JButton umount;

	private Volume selVol = null;

	private ArrayList<Boolean> attribs;
	private ArrayList<Volume> vols;
	private ArrayList<Volume> shownVols;

	public MainWindow() {
		super(TITLE, WIDTH, HEIGHT, JFrame.EXIT_ON_CLOSE);
		refresh();
	}

	@Override
	protected void addComponents() {
		addMenuBar();
		addVolumeLabel();
		addVolumeTable();
		addRefreshButton();
		addEjectButton();
		addFormatButton();
		addRenameButton();
		addBitLockButton();
		addReadOnlyButton();
	}

	private void addMenuBar() {
		JMenuBar menu = new JMenuBar();

		JMenu file = new JMenu(MENU_FILE);
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem exit = new JMenuItem(MENU_FILE_EXIT);
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu view = new JMenu(MENU_VIEW);
		view.setMnemonic(KeyEvent.VK_O);

		JCheckBoxMenuItem showVolumes = new JCheckBoxMenuItem(MENU_VIEW_SAV);
		showVolumes.setMnemonic(KeyEvent.VK_H);
		showVolumes.setSelected(Config.getShowAllVolumes());
		showVolumes.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Config.setShowAllVolumes(true);
				} else {
					Config.setShowAllVolumes(false);
				}
				Config.saveConfig();
				refresh();
			}
		});

		JMenu help = new JMenu(MENU_HELP);
		help.setMnemonic(KeyEvent.VK_H);

		JMenuItem about = new JMenuItem(MENU_HELP_ABOUT);
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutWindow();
			}
		});

		JCheckBoxMenuItem update = new JCheckBoxMenuItem(MENU_HELP_UPDATE);
		update.setMnemonic(KeyEvent.VK_U);
		update.setSelected(Config.getCheckForUpdate());
		update.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Config.setCheckForUpdate(true);
				} else {
					Config.setCheckForUpdate(false);
				}
				Config.saveConfig();
			}
		});

		file.add(exit);

		view.add(showVolumes);

		help.add(about);
		help.addSeparator();
		help.add(update);

		menu.add(file);
		menu.add(view);
		menu.add(help);

		setJMenuBar(menu);
	}

	private void addVolumeLabel() {
		addLabel(VOL_LABEL, VOL_LAB_X, VOL_LAB_Y, VOL_LAB_W, VOL_LAB_H);
	}

	private void addVolumeTable() {
		vt = new VolumeTable(CB_X, CB_Y, CB_W, CB_H);
		ListSelectionListener lsl = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (shownVols != null) {
					int idx = vt.getSelectedRow();
					if (idx >= 0) {
						if (idx < shownVols.size()) {
							selVol = shownVols.get(idx);
							updateAttr();
							updateUMount();
						}
					} else {
						selVol = null;
					}
				}
			}
		};
		vt.addListSelectionListener(lsl);
		getContentPane().add(vt);
		vt.setVisible(true);
	}

	private void updateUMount() {
		if (selVol != null) {
			if (selVol.isMounted()) {
				updateMainWindowStatus(STATUS_SELECTED_VOL + selVol.getLetterColon());
				umount.setText(UMountButton.EJECT);
			} else {
				updateMainWindowStatus(STATUS_VOLUME_NOT_MOUNTED);
				umount.setText(UMountButton.MOUNT);
			}
		}
	}

	private void updateAttr() {
		if (cattr != null && selVol != null) {
			if (attribs.get(selVol.getNumber())) {
				cattr.setText(ROAttribButton.READONLY_BUTTON_CLEAR);
			} else {
				cattr.setText(ROAttribButton.READONLY_BUTTON_SET);
			}
		}
	}

	private void addRefreshButton() {
		new RefreshButton(this, REFRESH_BUTTON_X, REFRESH_BUTTON_Y);
	}

	private void addEjectButton() {
		umount = new UMountButton(this, EJECT_BUTTON_X, EJECT_BUTTON_Y);
	}

	private void addFormatButton() {
		new FormatButton(this, FORMAT_BUTTON_X, FORMAT_BUTTON_Y);
	}

	private void addRenameButton() {
		new RenameButton(this, RENAME_BUTTON_X, RENAME_BUTTON_Y);
	}

	private void addBitLockButton() {
		new BitLockButton(this, BL_BUTTON_X, BL_BUTTON_Y);
	}

	private void addReadOnlyButton() {
		cattr = new ROAttribButton(this, READONLY_BUTTON_X, READONLY_BUTTON_Y);
	}

	public Volume getSelectedVolume() {
		return selVol;
	}

	private void updateVolumeTableBox() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				vt.clear();
				int size = Math.min(shownVols.size(), vols.size());
				for (int i = 0; i < size; i++) {
					Volume v = shownVols.get(i);
					vt.addVolume(v);
				}
				updateMainWindowStatus(STATUS_FOUND_1 + size
						+ (size > 1 ? STATUS_FOUND_2S : (size == 0 ? STATUS_FOUND_2S : STATUS_FOUND_2)));
			}
		});
	}

	public void refresh() {
		updateMainWindowStatus(STATUS_REFRESHING);
		getVolumes();
	}

	private void getVolumes() {
		Thread dp = new Thread() {
			@Override
			public void run() {
				updateMainWindowStatus(STATUS_FINDING_VOLS);
				vols = new DiskPartProcess().getVolumes();
				if (vols != null) {
					if (Config.getShowAllVolumes()) {
						shownVols = vols;
					} else {
						if (shownVols == null)
							shownVols = new ArrayList<Volume>();
						else
							shownVols.clear();
						for (Volume v : vols) {
							if (v.getType().equals(Volume.TYPE_REMOVABLE))
								shownVols.add(v);
						}
					}
					updateVolumeTableBox();
					getAttributes();
					cattr.setEnabled(true);
				} else
					updateMainWindowStatus(STATUS_FAILED_FIND_VOLS);
			}
		};
		dp.start();
	}

	private void getAttributes() {
		cattr.setEnabled(false);
		attribs = new DiskPartProcess().getAttributes(vols);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (attribs.size() > 0) {
					int pos = 0;
					int size = Math.min(attribs.size(), vols.size());
					for (int i = 0; i < size; i++) {
						Volume v = vols.get(i);
						if (!Config.getShowAllVolumes()) {
							if (!v.getType().equals(Volume.TYPE_REMOVABLE)) {
								continue;
							}
						}
						vt.updateCell(pos, VolumeTable.READONLY_COL, attribs.get(i) ? TABLE_YES : TABLE_NO);
						pos++;
					}
				} else {
					updateMainWindowStatus(STATUS_FAILED_FOUND_ATTRS);
				}
			}

		});
	}

	public boolean getRO() {
		return attribs.get(selVol.getNumber());
	}

	public boolean isValidVolume() {
		if (selVol == null) {
			updateMainWindowStatus(STATUS_SELECTED_NONE);
			return false;
		}
		if (!selVol.isMounted())
			return false;
		if (selVol.getNumber() == -1) {
			updateMainWindowStatus(STATUS_SELECTED_NONE);
			return false;
		}
		if (!selVol.getType().equals(Volume.TYPE_REMOVABLE)) {
			updateMainWindowStatus(STATUS_FOUND_2 + selVol.getLetterColon() + STATUS_VOLUME_NOT_REMOVABLE);
			return false;
		}
		return true;
	}

	public void updateMainWindowStatus(String status) {
		updateStatus(TITLE + TITLE_SEPERATOR + status);
	}

}
