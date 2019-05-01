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
public class RenameWindow extends JFrame{

	private final int width = 180;
	private final int height = 130;
	
	private MainWindow mw;
	private JTextField tf;
	private JButton apply;
	private JButton cancel;
	private Volume v;
	
	public RenameWindow(MainWindow mw){
		super();
		
		this.mw = mw;
		v = mw.getSelectedVolume();
		setSize(width, height);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle(" ");
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addComponents();
		
		revalidate();
		repaint();
	}
	
	private void addComponents(){
		addLabel();
		addTextField();
		addApplyButton();
		addCancelButton();
	}
	
	private void addLabel(){
		addLabel("New Label For Volume " + mw.getSelectedVolume().getLetterColon(), 10, 10, 150, 20);
	}
	
	private void addTextField(){
		tf = new JTextField();
		tf.setBounds(10, 35, 155, 20);
		tf.setVisible(true);
		tf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(tf.getText().length() > 10)
					tf.setText(tf.getText().substring(0, 10));
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
		getContentPane().add(tf);
	}
	
	private void addApplyButton(){
		apply = new JButton("Apply");
		apply.setBounds(10, 60, 75, 20);
		apply.setVisible(true);
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int resp = JOptionPane.showConfirmDialog(RenameWindow.this,
						"Are You Sure You Want To Rename " + v.getLetterColon() + "?", "Confirm Rename",
						JOptionPane.YES_NO_OPTION);

				if (resp == JOptionPane.OK_OPTION) {
					apply.setEnabled(false);
					cancel.setEnabled(false);
					RenameWindow.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					RenameWindow.this.setTitle("Renameing...");
					Thread rn = new Thread(new Runnable() {

						@Override
						public void run() {
							if (new LabelProcess().rename(v, tf.getText().trim())) {
								JOptionPane.showMessageDialog(RenameWindow.this, "Rename Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
								mw.refresh();
								dispose();
							} else {
								JOptionPane.showMessageDialog(RenameWindow.this, "Error with rename", "Error", JOptionPane.ERROR_MESSAGE);
								apply.setEnabled(true);
								cancel.setEnabled(true);
								RenameWindow.this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							}
						}
					});
					rn.start();
				}

			}

		});
		getContentPane().add(apply);
	}

	private void addCancelButton() {
		cancel = new JButton("Cancel");
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
	
	private JLabel addLabel(String label, int x, int y, int w, int h) {
		JLabel l = new JLabel(label);
		l.setBounds(x, y, w, h);
		l.setVisible(true);
		getContentPane().add(l);
		return l;
	}
	
}
