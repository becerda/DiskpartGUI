package com.csuci.becerda.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.csuci.becerda.process.LabelProcess;
import com.csuci.becerda.volume.Volume;

@SuppressWarnings("serial")
public class RenameWindow extends BaseWindow {

	private static final int WIDTH = 180;
	private static final int HEIGHT = 130;

	private final String LABEL_NEW_LABEL = "New Label For Volume ";
	private final String BUTTON_APPLY = "Apply";
	private final String BUTTON_CANCEL = "CANCEL";
	private final String CONFIRM_DIALOG_1 = "Are You Sure You Want To Rename ";
	private final String CONFIRM_DIALOG_2 = " To ";
	private final String CONFIRM_TITLE = "Confirm Rename";
	private final String SUCCESS_DIALOG = "Rename Successful!";
	private final String SUCCESS_TITLE = "Success";
	private final String ERROR_DIALOG = "Error with rename!";
	private final String ERROR_TITLE = "Error";

	private MainWindow mw;
	private JLabel label;
	private JTextField tf;
	private JButton apply;
	private JButton cancel;
	private Volume v;

	public RenameWindow(MainWindow mw) {
		super(" ", WIDTH, HEIGHT, JFrame.DISPOSE_ON_CLOSE);

		this.mw = mw;
		v = mw.getSelectedVolume();

		updateFields();
	}

	@Override
	protected void addComponents() {
		addLabel();
		addTextField();
		addApplyButton();
		addCancelButton();
	}

	private void addLabel() {
		label = addLabel("", 10, 10, 150, 20);
	}

	private void addTextField() {
		tf = new JTextField();
		tf.setBounds(10, 35, 155, 20);
		tf.setVisible(true);
		tf.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (tf.getText().length() > 10)
					tf.setText(tf.getText().substring(0, 10));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					tf.setText(tf.getText().trim());
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					runRename();
				}
			}
		});
		getContentPane().add(tf);
	}

	private void addApplyButton() {
		apply = new JButton(BUTTON_APPLY);
		apply.setBounds(10, 60, 75, 20);
		apply.setVisible(true);
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runRename();
			}

		});
		getContentPane().add(apply);
	}

	private void addCancelButton() {
		cancel = new JButton(BUTTON_CANCEL);
		cancel.setBounds(90, 60, 75, 20);
		cancel.setVisible(true);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancel);
	}

	private void runRename() {
		int resp = JOptionPane.showConfirmDialog(RenameWindow.this,
				CONFIRM_DIALOG_1 + v.getLetterColon() + CONFIRM_DIALOG_2 + tf.getText().toUpperCase(), CONFIRM_TITLE,
				JOptionPane.YES_NO_OPTION);

		if (resp == JOptionPane.OK_OPTION) {
			apply.setEnabled(false);
			cancel.setEnabled(false);
			RenameWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			RenameWindow.this.setTitle("Renameing...");
			if (new LabelProcess().rename(v, tf.getText().trim())) {
				JOptionPane.showMessageDialog(RenameWindow.this, SUCCESS_DIALOG, SUCCESS_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
				mw.refresh();
				dispose();
			} else {
				JOptionPane.showMessageDialog(RenameWindow.this, ERROR_DIALOG, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				apply.setEnabled(true);
				cancel.setEnabled(true);
			}
		}
	}

	@Override
	protected void updateFields() {
		label.setText(LABEL_NEW_LABEL + mw.getSelectedVolume().getLetterColon());
	}

}
