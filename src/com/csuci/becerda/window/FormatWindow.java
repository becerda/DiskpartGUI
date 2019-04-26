package com.csuci.becerda.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.process.FormatOptions;
import com.csuci.becerda.volume.Volume;

public class FormatWindow extends JFrame {

	private final int width = 250;
	private final int height = 210;

	private FormatOptions fo;
	private Volume v;
	private MainWindow mw;

	private JTextField label;
	private JButton apply;
	private JButton cancel;

	public FormatWindow(MainWindow mw, Volume v) {
		super();

		this.mw = mw;
		fo = new FormatOptions();
		this.v = v;

		setSize(width, height);
		setLayout(null);
		setVisible(true);
		setTitle("Format");
		setLocationRelativeTo(null);
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addComponents();

		revalidate();
		repaint();
	}

	private void addComponents() {
		addLabelLabel();
		addLabelTextField();
		addFSLabel();
		addFSComboBox();
		addUnitLabel();
		addUnitComboBox();
		addQuckCheckBox();
		addApplyButton();
		addCancelButton();
	}

	private void addLabelLabel() {
		addLabel("Label:", 5, 10, 45, 20);
	}

	private void addLabelTextField() {
		label = new JTextField(20);
		if (v.getLabel().trim().length() > 0)
			label.setText(v.getLabel().trim());
		label.setBounds(5 + 75, 10, 100, 20);
		label.setVisible(true);
		label.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (label.getText().length() > 10)
					label.setText(label.getText().substring(0, 10));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		getContentPane().add(label);
	}

	private void addFSLabel() {
		addLabel("File System:", 5, 10 + 20 + 5, 75, 20);
	}

	private void addFSComboBox() {
		JComboBox<String> fs = new JComboBox<String>(FormatOptions.fs);
		fs.setBounds(5 + 75, 10 + 20 + 5, 100, 20);
		fs.setVisible(true);
		fs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (fs.getSelectedIndex()) {
				case 0:
					fo.setFsDefault();
					break;
				case 1:
					fo.setFsNTFS();
					break;
				case 2:
					fo.setFsFAT32();
					break;
				case 3:
					fo.setFsexFAT();
					break;
				}
			}
		});
		getContentPane().add(fs);
	}

	private void addUnitLabel() {
		addLabel("Unit Size:", 5, 10 + 40 + 10, 75, 20);
	}

	private void addUnitComboBox() {
		JComboBox<String> unit = new JComboBox<String>(FormatOptions.unitsize);
		unit.setBounds(5 + 75, 10 + 40 + 10, 100, 20);
		unit.setVisible(true);
		unit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (unit.getSelectedIndex()) {
				case 0:
					fo.setUnitSizeDefault();
					break;
				case 1:
					fo.setUnitSize512();
					break;
				case 2:
					fo.setUnitSize1024();
					break;
				case 3:
					fo.setUnitSize2048();
					break;
				case 4:
					fo.setUnitSize4096();
					break;
				case 5:
					fo.setUnitSize8192();
					break;
				case 6:
					fo.setUnitSize16K();
					break;
				case 7:
					fo.setUnitSize32K();
					break;
				case 8:
					fo.setUnitSize64K();
					break;
				case 9:
					fo.setUnitSize128K();
					break;
				case 10:
					fo.setUnitSize256K();
					break;
				case 11:
					fo.setUnitSize512K();
					break;
				case 12:
					fo.setUnitSize1024K();
					break;
				case 13:
					fo.setUnitSize2048K();
					break;
				case 14:
					fo.setUnitSize4096K();
					break;
				case 15:
					fo.setUnitSize8192K();
					break;
				case 16:
					fo.setUnitSize16384K();
					break;
				case 17:
					fo.setUnitSize32768K();
					break;
				}
			}
		});
		getContentPane().add(unit);
	}

	private void addQuckCheckBox() {
		JCheckBox quick = new JCheckBox("Quick");
		quick.setSelected(true);
		quick.setBounds(5, 10 + 60 + 15, 100, 20);
		quick.setVisible(true);
		quick.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				fo.setQuick(quick.isSelected());
			}
		});
		getContentPane().add(quick);
	}

	private void addApplyButton() {
		apply = new JButton("Apply");
		apply.setBounds(125 - 100 - 10, 150, 100, 20);
		apply.setVisible(true);
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fo.setLabel(label.getText());

				int resp = JOptionPane.showConfirmDialog(FormatWindow.this,
						"Are you sure you want to format " + v.getLetterColon() + "?", "Confirm Format",
						JOptionPane.YES_NO_OPTION);

				if (resp == JOptionPane.OK_OPTION) {
					apply.setEnabled(false);
					cancel.setEnabled(false);
					FormatWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					FormatWindow.this.setTitle("Formatting...");
					Thread form = new Thread(new Runnable() {

						@Override
						public void run() {
							if (new DiskPartProcess().format(v, fo)) {
								JOptionPane.showMessageDialog(FormatWindow.this, "Format Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
								mw.refresh();
								dispose();
							} else {
								JOptionPane.showMessageDialog(FormatWindow.this, "Error with format", "Error", JOptionPane.ERROR_MESSAGE);
								apply.setEnabled(true);
								cancel.setEnabled(true);
								FormatWindow.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							}
						}
					});
					form.start();
				}

			}

		});
		getContentPane().add(apply);
	}

	private void addCancelButton() {
		cancel = new JButton("Cancel");
		cancel.setBounds(125 + 5, 150, 100, 20);
		cancel.setVisible(true);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancel);
	}

	private JLabel addLabel(String label, int x, int y, int w, int h) {
		JLabel l = new JLabel(label);
		l.setBounds(x, y, w, h);
		l.setVisible(true);
		getContentPane().add(l);
		return l;
	}

}
