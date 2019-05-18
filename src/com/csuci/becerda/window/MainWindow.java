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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

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

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	// Main Window Vars
	private final int MAIN_WINDOW_HEIGHT = 225;
	private final int MAIN_WINDOW_WIDTH = 445;
	private final String MAIN_WINDOW_TITLE = "Diskpart GUI";

	private final int MAIN_WINDOW_LARGE_PADDING = 15;
	private final int MAIN_WINDOW_SMALL_PADDING = 5;
	private final int MAIN_WINDOW_DEFAULT_BUTTON_W = BaseButton.DEFAULT_WIDTH;

	private final int MAIN_WINDOW_DEFAULT_X = 10;
	private final int MAIN_WINDOW_DEFAULT_Y = 10;
	private final int MAIN_WINDOW_DEFAULT_LABEL_W = 100;
	private final int MAIN_WINDOW_DEFAULT_LABEL_H = 20;

	// Main Window Status Vars
	private final String MAIN_WINDOW_STATUS_FINDING_VOLS = "Finding Volumes...";
	private final String MAIN_WINDOW_STATUS_REFRESHING = "Refreshing...";
	private final String MAIN_WINDOW_STATUS_SEP = " - ";
	private final String MAIN_WINDOW_STATUS_FAILED_FIND_VOLS = "Failed To Find Volumes";
	private final String MAIN_WINDOW_STATUS_FOUND_1 = "Finished, Found ";
	private final String MAIN_WINDOW_STATUS_FOUND_2 = " Volume ";
	private final String MAIN_WINDOW_STATUS_FOUND_2S = " Volumes";
	private final String MAIN_WINDOW_STATUS_SELECTED_VOL = "Selected Volume ";
	private final String MAIN_WINDOW_STATUS_SELECTED_NONE = "No Volume Selected";
	private final String MAIN_WINDOW_STATUS_FAILED_FOUND_ATTRS = "Could Not Find Attributes";
	private final String MAIN_WINDOW_STATUS_VOLUME_NOT_MOUNTED = "Volume Is Not Mounted";
	private final String MAIN_WINDOW_STATUS_VOLUME_NOT_REMOVABLE = " Is Not Removable";

	// Volume Table Vars
	private final String MAIN_WINDOW_TABLE_BYTE = "B";
	private final String MAIN_WINOW_TABLE_UNMOUNTED = "UNMOUNTED";
	private final String MAIN_WINOW_TABLE_YES = "Yes";
	private final String MAIN_WINOW_TABLE_NO = "No";

	// Main Window Menu Bar Vars
	private final String MAIN_WINDOW_MENU_FILE = "File";
	private final String MAIN_WINDOW_MENU_FILE_EXIT = "Exit";

	private final String MAIN_WINDOW_MENU_VIEW = "View";
	private final String MAIN_WINDOW_MENU_VIEW_SAV = "Show All Volumes";
	private final String MAIN_WINDOW_MENU_VIEW_MV = "Max Volumes";
	private final String MAIN_WINDOW_MENU_VIEW_MV_DIALOG = "New Maximum Shown Volumes (< 100):";
	private final String MAIN_WINDOW_MENU_VIEW_MV_TITLE = "Maximum Shown Volumes";
	private final String MAIN_WINDOW_MENU_VIEW_MV_DIALOG_SUCCESS = "Please Restart For Changes To Take Affect!";
	private final String MAIN_WINDOW_MENU_VIEW_MV_TITLE_SUCCESS = "Restart Required";
	private final String MAIN_WINDOW_MENU_VIEW_MV_DIALOG_ERROR = "Please Enter A Number Greater Than 0 And Less Than 100!";
	private final String MAIN_WINDOW_MENU_VIEW_MV_TITLE_ERROR = "Error";

	private final String MAIN_WINDOW_MENU_HELP = "Help";
	private final String MAIN_WINDOW_MENU_HELP_ABOUT = "About";
	private final String MAIN_WINDOW_MENU_HELP_UPDATE = "Check For Update";

	// Volume Label Vars
	private final String MAIN_WINDOW_VOL_LABEL = "Volume: ";
	private final int MAIN_WINDOW_VOL_LAB_X = MAIN_WINDOW_DEFAULT_X;
	private final int MAIN_WINDOW_VOL_LAB_Y = MAIN_WINDOW_DEFAULT_Y;
	private final int MAIN_WINDOW_VOL_LAB_W = MAIN_WINDOW_DEFAULT_LABEL_W;
	private final int MAIN_WINDOW_VOL_LAB_H = MAIN_WINDOW_DEFAULT_LABEL_H;

	// Volume Table Vars
	private final int MAIN_WINDOW_CB_X = MAIN_WINDOW_VOL_LAB_X;
	private final int MAIN_WINDOW_CB_Y = MAIN_WINDOW_VOL_LAB_Y + MAIN_WINDOW_VOL_LAB_H + MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_CB_W = MAIN_WINDOW_WIDTH - MAIN_WINDOW_LARGE_PADDING - MAIN_WINDOW_DEFAULT_X;
	private final int MAIN_WINDOW_CB_H = 21 * 5;
	private final String[] VOL_TABLE_COL_NAMES = { "Number", "Letter", "Name", "Size", "Read-Only" };

	// Refresh Button Vars
	private final int MAIN_WINDOW_REFRESH_BUTTON_X = MAIN_WINDOW_WIDTH - MAIN_WINDOW_DEFAULT_BUTTON_W
			- MAIN_WINDOW_LARGE_PADDING;
	private final int MAIN_WINDOW_REFRESH_BUTTON_Y = MAIN_WINDOW_VOL_LAB_Y;

	// Eject/Mount Button Vars
	private final int MAIN_WINDOW_EJECT_BUTTON_X = MAIN_WINDOW_VOL_LAB_X;
	private final int MAIN_WINDOW_EJECT_BUTTON_Y = MAIN_WINDOW_CB_Y + MAIN_WINDOW_CB_H + MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_EJECT_BUTTON_W = MAIN_WINDOW_DEFAULT_BUTTON_W;

	// Format Button Vars
	private final int MAIN_WINDOW_FORMAT_BUTTON_X = MAIN_WINDOW_EJECT_BUTTON_X + MAIN_WINDOW_EJECT_BUTTON_W
			+ MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_FORMAT_BUTTON_Y = MAIN_WINDOW_EJECT_BUTTON_Y;
	private final int MAIN_WINDOW_FORMAT_BUTTON_W = MAIN_WINDOW_DEFAULT_BUTTON_W;

	// Rename Button Vars
	private final int MAIN_WINDOW_RENAME_BUTTON_X = MAIN_WINDOW_FORMAT_BUTTON_X + MAIN_WINDOW_FORMAT_BUTTON_W
			+ MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_RENAME_BUTTON_Y = MAIN_WINDOW_EJECT_BUTTON_Y;
	private final int MAIN_WINDOW_RENAME_BUTTON_W = MAIN_WINDOW_DEFAULT_BUTTON_W;

	// Bitlocker Button Vars
	private final int MAIN_WINDOW_BL_BUTTON_X = MAIN_WINDOW_RENAME_BUTTON_X + MAIN_WINDOW_RENAME_BUTTON_W
			+ MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_BL_BUTTON_Y = MAIN_WINDOW_EJECT_BUTTON_Y;

	// ReadOnly Button Vars
	private final int MAIN_WINDOW_READONLY_BUTTON_X = MAIN_WINDOW_BL_BUTTON_X + MAIN_WINDOW_DEFAULT_BUTTON_W
			+ MAIN_WINDOW_SMALL_PADDING;
	private final int MAIN_WINDOW_READONLY_BUTTON_Y = MAIN_WINDOW_EJECT_BUTTON_Y;

	private JTable volT;
	private JButton cattr;
	private JButton umount;

	private Volume selVol = null;

	private ArrayList<Boolean> attribs;
	private ArrayList<Volume> vols;
	private ArrayList<Volume> shownVols;

	public MainWindow() {
		super();

		setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
		setLayout(null);
		setVisible(true);
		setTitle(MAIN_WINDOW_TITLE);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponents();

		revalidate();
		repaint();

		refresh();
	}

	private void addComponents() {
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

		JMenu file = new JMenu(MAIN_WINDOW_MENU_FILE);
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem exit = new JMenuItem(MAIN_WINDOW_MENU_FILE_EXIT);
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu view = new JMenu(MAIN_WINDOW_MENU_VIEW);
		view.setMnemonic(KeyEvent.VK_O);

		JCheckBoxMenuItem showVolumes = new JCheckBoxMenuItem(MAIN_WINDOW_MENU_VIEW_SAV);
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

		JMenuItem maxVolumeShown = new JMenuItem(MAIN_WINDOW_MENU_VIEW_MV);
		maxVolumeShown.setMnemonic(KeyEvent.VK_M);
		maxVolumeShown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(MainWindow.this, MAIN_WINDOW_MENU_VIEW_MV_DIALOG,
						MAIN_WINDOW_MENU_VIEW_MV_TITLE, JOptionPane.OK_CANCEL_OPTION);
				if (input != null) {
					try {
						int max = Integer.parseInt(input);
						Config.setMaxShownVolumes(max);
						Config.saveConfig();
						JOptionPane.showMessageDialog(MainWindow.this, MAIN_WINDOW_MENU_VIEW_MV_DIALOG_SUCCESS,
								MAIN_WINDOW_MENU_VIEW_MV_TITLE_SUCCESS, JOptionPane.INFORMATION_MESSAGE);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						JOptionPane.showMessageDialog(MainWindow.this, MAIN_WINDOW_MENU_VIEW_MV_DIALOG_ERROR,
								MAIN_WINDOW_MENU_VIEW_MV_TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenu help = new JMenu(MAIN_WINDOW_MENU_HELP);
		help.setMnemonic(KeyEvent.VK_H);

		JMenuItem about = new JMenuItem(MAIN_WINDOW_MENU_HELP_ABOUT);
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutWindow();
			}
		});
		
		JCheckBoxMenuItem update = new JCheckBoxMenuItem(MAIN_WINDOW_MENU_HELP_UPDATE);
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
		view.addSeparator();
		view.add(maxVolumeShown);
		help.add(about);
		help.add(update);

		menu.add(file);
		menu.add(view);
		menu.add(help);

		setJMenuBar(menu);
	}

	private void addVolumeLabel() {
		addLabel(MAIN_WINDOW_VOL_LABEL, MAIN_WINDOW_VOL_LAB_X, MAIN_WINDOW_VOL_LAB_Y, MAIN_WINDOW_VOL_LAB_W,
				MAIN_WINDOW_VOL_LAB_H);
	}

	private void addVolumeTable() {
		volT = new JTable(new String[Config.getMaxShownVolumes()][VOL_TABLE_COL_NAMES.length], VOL_TABLE_COL_NAMES);
		for (int i = 0; i < volT.getColumnCount(); i++) {
			Class<?> col_class = volT.getColumnClass(i);
			volT.setDefaultEditor(col_class, null); // remove editor
		}
		JScrollPane sp = new JScrollPane(volT);
		sp.setBounds(MAIN_WINDOW_CB_X, MAIN_WINDOW_CB_Y, MAIN_WINDOW_CB_W, MAIN_WINDOW_CB_H);
		volT.setFillsViewportHeight(true);
		volT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (shownVols != null) {
					int idx = volT.getSelectedRow();
					if (idx < shownVols.size()) {
						selVol = shownVols.get(idx);
						updateAttr();
						updateUMount();
					}
				}
			}
		});
		volT.getTableHeader().setResizingAllowed(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < volT.getColumnCount(); i++) {
			volT.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		getContentPane().add(sp);
	}

	private void updateUMount() {
		if (selVol != null) {
			if (selVol.isMounted()) {
				updateStatus(MAIN_WINDOW_STATUS_SELECTED_VOL + selVol.getLetterColon());
				umount.setText(UMountButton.EJECT);
			} else {
				updateStatus(MAIN_WINDOW_STATUS_VOLUME_NOT_MOUNTED);
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
		new RefreshButton(this, MAIN_WINDOW_REFRESH_BUTTON_X, MAIN_WINDOW_REFRESH_BUTTON_Y);
	}

	private void addEjectButton() {
		umount = new UMountButton(this, MAIN_WINDOW_EJECT_BUTTON_X, MAIN_WINDOW_EJECT_BUTTON_Y);
	}

	private void addFormatButton() {
		new FormatButton(this, MAIN_WINDOW_FORMAT_BUTTON_X, MAIN_WINDOW_FORMAT_BUTTON_Y);
	}

	private void addRenameButton() {
		new RenameButton(this, MAIN_WINDOW_RENAME_BUTTON_X, MAIN_WINDOW_RENAME_BUTTON_Y);
	}

	private void addBitLockButton() {
		new BitLockButton(this, MAIN_WINDOW_BL_BUTTON_X, MAIN_WINDOW_BL_BUTTON_Y);
	}

	private void addReadOnlyButton() {
		cattr = new ROAttribButton(this, MAIN_WINDOW_READONLY_BUTTON_X, MAIN_WINDOW_READONLY_BUTTON_Y);
	}

	public Volume getSelectedVolume() {
		return selVol;
	}

	private void clearVolumeTable() {
		for (int i = 0; i < volT.getRowCount() - 1; i++) {
			for (int j = 0; j < volT.getColumnCount(); j++) {
				volT.setValueAt("", i, j);
			}
		}
	}

	private void updateVolumeTableBox() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				clearVolumeTable();
				int size = Math.min(shownVols.size(), Config.getMaxShownVolumes());
				for (int i = 0; i < size; i++) {
					Volume v = shownVols.get(i);
					volT.setValueAt(v.getNumber() + "", i, 0);
					if (v.isMounted()) {
						volT.setValueAt(v.getLetterColon() + "", i, 1);
						volT.setValueAt(v.getLabel(), i, 2);
						volT.setValueAt(v.getSize() + " " + v.getGK() + MAIN_WINDOW_TABLE_BYTE, i, 3);
					} else {
						volT.setValueAt(MAIN_WINOW_TABLE_UNMOUNTED, i, 1);
					}
				}
				updateStatus(MAIN_WINDOW_STATUS_FOUND_1 + size + (size > 1 ? MAIN_WINDOW_STATUS_FOUND_2S
						: (size == 0 ? MAIN_WINDOW_STATUS_FOUND_2S : MAIN_WINDOW_STATUS_FOUND_2)));
			}
		});
	}

	public void refresh() {
		updateStatus(MAIN_WINDOW_STATUS_REFRESHING);
		getVolumes();
	}

	private void getVolumes() {
		Thread dp = new Thread() {
			@Override
			public void run() {
				updateStatus(MAIN_WINDOW_STATUS_FINDING_VOLS);
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
					updateStatus(MAIN_WINDOW_STATUS_FAILED_FIND_VOLS);
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
					int size = Math.min(attribs.size(), Config.getMaxShownVolumes());
					for (int i = 0; i < size; i++) {
						Volume v = vols.get(i);
						if (!Config.getShowAllVolumes()) {
							if (!v.getType().equals(Volume.TYPE_REMOVABLE)) {
								continue;
							}
						}
						volT.getModel().setValueAt(attribs.get(i) ? MAIN_WINOW_TABLE_YES : MAIN_WINOW_TABLE_NO, pos, 4);
						pos++;
					}
				} else {
					updateStatus(MAIN_WINDOW_STATUS_FAILED_FOUND_ATTRS);
				}
			}

		});
	}

	public boolean getRO() {
		return attribs.get(selVol.getNumber());
	}

	public boolean isValidVolume() {
		if (selVol == null) {
			updateStatus(MAIN_WINDOW_STATUS_SELECTED_NONE);
			return false;
		}
		if (!selVol.isMounted())
			return false;
		if (selVol.getNumber() == -1) {
			updateStatus(MAIN_WINDOW_STATUS_SELECTED_NONE);
			return false;
		}
		if (!selVol.getType().equals(Volume.TYPE_REMOVABLE)) {
			updateStatus(
					MAIN_WINDOW_STATUS_FOUND_2 + selVol.getLetterColon() + MAIN_WINDOW_STATUS_VOLUME_NOT_REMOVABLE);
			return false;
		}
		return true;
	}

	private JLabel addLabel(String label, int x, int y, int w, int h) {
		JLabel l = new JLabel(label);
		l.setBounds(x, y, w, h);
		l.setVisible(true);
		getContentPane().add(l);
		return l;
	}

	public void updateStatus(String status) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setTitle(MAIN_WINDOW_TITLE + MAIN_WINDOW_STATUS_SEP + status);
			}
		});
	}

}
