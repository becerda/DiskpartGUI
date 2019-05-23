package com.csuci.becerda.window.element;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.csuci.becerda.volume.Volume;

@SuppressWarnings("serial")
public class VolumeTable extends JPanel {

	private static final String[] VOL_TABLE_COL_NAMES = { "Number", "Letter", "Name", "Size", "Read-Only" };
	private final String VOLUME_UNMOUNTED = "UNMOUNTED";
	public static final int NUMBER_COL = 0;
	public static final int LETTER_COL = 1;
	public static final int NAME_COL = 2;
	public static final int SIZE_COL = 3;
	public static final int READONLY_COL = 4;

	private JScrollPane sp;
	private DefaultTableModel model;
	private JTable table;

	public VolumeTable(int x, int y, int w, int h) {
		super();
		setBounds(x, y, w, h);
		setLayout(new BorderLayout());

		table = new JTable();
		model = new DefaultTableModel(new Object[] { VOL_TABLE_COL_NAMES[NUMBER_COL], VOL_TABLE_COL_NAMES[LETTER_COL],
				VOL_TABLE_COL_NAMES[NAME_COL], VOL_TABLE_COL_NAMES[SIZE_COL], VOL_TABLE_COL_NAMES[READONLY_COL] }, 0);
		table.setModel(model);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.CENTER);
		}

		sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(w, h));
		sp.setVisible(true);
		table.setVisible(true);
		add(BorderLayout.CENTER, sp);
	}

	public void updateCell(int row, int col, String data) {
		table.setValueAt(data, row, col);
	}

	public void addVolume(Volume v) {
		if (v.isMounted()) {
			model.addRow(new Object[] { v.getNumber(), v.getLetterColon() + "", v.getLabel().trim(),
					v.getSize() + " " + v.getGK() + "B", "" });
		} else {
			model.addRow(new Object[] { v.getNumber(), VOLUME_UNMOUNTED, "", "", "" });
		}
		Class<?> colClass = table.getColumnClass(model.getRowCount() - 1);
		table.setDefaultEditor(colClass, null);
	}

	public void clear() {
		table.clearSelection();
		for (int i = table.getRowCount() - 1; i > -1; i--) {
			model.removeRow(i);
		}
		model.fireTableDataChanged();
	}

	public void addListSelectionListener(ListSelectionListener lsl) {
		table.getSelectionModel().addListSelectionListener(lsl);
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}
}
