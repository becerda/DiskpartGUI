package com.csuci.becerda.window;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.csuci.becerda.process.DiskPartProcess;
import com.csuci.becerda.process.FormatOptions;
import com.csuci.becerda.volume.Volume;

@SuppressWarnings("serial")
public class FormatWindow extends BaseWindow {

	private static final String TITLE = "Format";
	private static final int WIDTH = 250;
	private static final int HEIGHT = 210;

	private final String LABEL_LABEL = "Label:";
	private final String LABEL_FS = "File System:";
	private final String LABEL_US = "Unit Size:";
	private final String LABEL_QUICK = "Quick";
	private final String BUTTON_APPLY = "Apply";
	private final String BUTTON_CANCEL = "Cancel";
	private final String CONFIRM_FORMAT_DIALOG = "Are You Sure You Want To Format ";
	private final String CONFIRM_FORMAT_DIALOG_Q = "?";
	private final String CONFIRM_FORMAT_TITLE = "Confirm Format";
	private final String TITLE_FORMATTING = "Formatting...";
	private final String FORMAT_SUCCESS_DIALOG = "Format Successful";
	private final String FORMAT_SUCCESS_TITLE = "Success";
	private final String FORMAT_ERROR_DIALOG = "Error With Format";
	private final String FORMAT_ERROR_TITLE = "Error";

	private FormatOptions fo;
	private Volume v;
	private MainWindow mw;

	private JTextField label;
	private JButton apply;
	private JButton cancel;
	private Choice unit;
	private Choice fs;

	public FormatWindow(MainWindow mw, Volume v) {
		super(TITLE, WIDTH, HEIGHT, JFrame.DISPOSE_ON_CLOSE);

		this.mw = mw;
		fo = new FormatOptions();
		this.v = v;
		updateFields();
	}

	@Override
	protected void addComponents() {
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
		addLabel(LABEL_LABEL, 5, 10, 45, 20);
	}

	private void addLabelTextField() {
		label = new JTextField(20);
		label.setBounds(5 + 75, 10, 100, 20);
		label.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (label.getText().length() > 10)
					label.setText(label.getText().substring(0, 10));
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		getContentPane().add(label);
		label.setVisible(true);
	}

	private void addFSLabel() {
		addLabel(LABEL_FS, 5, 10 + 20 + 5, 75, 20);
	}

	private void addFSComboBox() {
		fs = new Choice();
		fs.setBounds(5 + 75, 10 + 20 + 5, 100, 20);

		for (String s : FormatOptions.fs) {
			fs.add(s);
		}

		getContentPane().add(fs);
		fs.setVisible(true);
	}

	private void addUnitLabel() {
		addLabel(LABEL_US, 5, 10 + 40 + 10, 75, 20);
	}

	private void addUnitComboBox() {
		unit = new Choice();
		unit.setBounds(5 + 75, 10 + 40 + 10, 100, 20);

		for (String s : FormatOptions.unitsize) {
			unit.add(s);
		}

		getContentPane().add(unit);
		unit.setVisible(true);
	}

	private void addQuckCheckBox() {
		JCheckBox quick = new JCheckBox(LABEL_QUICK);
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
		apply = new JButton(BUTTON_APPLY);
		apply.setBounds(125 - 100 - 10, 150, 100, 20);
		apply.setVisible(true);
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fo.setLabel(label.getText());

				fo.setUnitSize(unit.getSelectedIndex());

				fo.setFs(fs.getSelectedIndex());

				int resp = JOptionPane.showConfirmDialog(FormatWindow.this,
						CONFIRM_FORMAT_DIALOG + v.getLetterColon() + CONFIRM_FORMAT_DIALOG_Q, CONFIRM_FORMAT_TITLE,
						JOptionPane.YES_NO_OPTION);

				if (resp == JOptionPane.OK_OPTION) {
					apply.setEnabled(false);
					cancel.setEnabled(false);
					FormatWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					FormatWindow.this.setTitle(TITLE_FORMATTING);
					Thread form = new Thread(new Runnable() {

						@Override
						public void run() {
							if (new DiskPartProcess().format(v, fo)) {
								JOptionPane.showMessageDialog(FormatWindow.this, FORMAT_SUCCESS_DIALOG,
										FORMAT_SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
								mw.refresh();
								dispose();
							} else {
								JOptionPane.showMessageDialog(FormatWindow.this, FORMAT_ERROR_DIALOG,
										FORMAT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
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
		cancel = new JButton(BUTTON_CANCEL);
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

	@Override
	protected void updateFields(){
		if (v.getLabel().trim().length() > 0)
			label.setText(v.getLabel().trim());
	}
}
