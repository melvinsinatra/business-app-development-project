package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import objects.Beverage;
import util.MySQLConnect;

public class ManageBeverageForm extends JInternalFrame {

	JLabel lblTitle, newBeverageID, newBeverageName, newBeverageType, newBeveragePrice, newBeverageStock, beverageID,
			beverageName, beverageType, beveragePrice, beverageStock, addStock;
	JTextField txtNewBeverageID, txtNewBeverageName, txtNewBeveragePrice, txtBeverageID, txtBeverageName,
			txtBeveragePrice, txtBeverageStock;
	JSpinner spinNewBeverageStock, spinAddStock;
	SpinnerModel modelNewBeverageStock = new SpinnerNumberModel(1, 1, 100, 1);
	SpinnerModel modelAddStock = new SpinnerNumberModel(1, 1, 100, 1);
	JComboBox comboNewBeverageType, comboBeverageType;
	JButton btnInsertBeverage, btnReset, btnUpdateBeverage, btnDeleteBeverage, btnAddStock;
	JPanel pnlTop, pnlBottom, pnlLblTitle, pnlAddStock;
	JTable tableBeverage;
	DefaultTableModel dtmBeverage;
	JScrollPane spBeverage;
	Vector<String> columnBeverage = new Vector<>();
	Vector<Object> rowBeverage = new Vector<>();
	Vector<Beverage> beverages = new Vector<>();
	GridBagConstraints gbc = new GridBagConstraints();
	String[] arrBeverageType = { "Boba", "Coffee", "Tea", "Smoothies" };
	MySQLConnect con = new MySQLConnect();

	public ManageBeverageForm() {
		setConfig();
		setManageBeverageTable();
		setManageBeverageForm();
		setVisible(true);
	}

	private void setManageBeverageTable() {
		pnlTop = new JPanel();
		pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
		pnlTop.setPreferredSize(new Dimension(1430, 410));
		pnlTop.setBackground(new Color(51, 204, 255));
		pnlTop.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Manage Beverage Label (Title)
		lblTitle = new JLabel("Manage Beverage", SwingConstants.CENTER);
		pnlLblTitle = new JPanel(new BorderLayout());
		pnlLblTitle.setBackground(new Color(51, 204, 255));
		pnlLblTitle.setPreferredSize(new Dimension(1200, 30));
		pnlLblTitle.add(lblTitle);
		pnlTop.add(pnlLblTitle);

		// Beverage Table
		columnBeverage.add("Beverage Id");
		columnBeverage.add("Beverage Name");
		columnBeverage.add("Beverage Type");
		columnBeverage.add("Beverage Price");
		columnBeverage.add("Beverage Stock");

		dtmBeverage = new DefaultTableModel(columnBeverage, 0);
		tableBeverage = new JTable(dtmBeverage);
		tableBeverage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tableBeverage.getSelectedRow();
				txtBeverageName.setText("");
				txtBeveragePrice.setText("");
				txtBeverageID.setText(dtmBeverage.getValueAt(row, 0).toString());
				txtBeverageStock.setText(dtmBeverage.getValueAt(row, 4).toString());
			}
		});

		// Adding Beverages to table / Filling Table from database
		rowBeverage = new Vector<>();
		try {
			String query = "SELECT * FROM beverages";
			ResultSet rs = con.executeQuery(query);

			while (rs.next()) {
				rowBeverage = new Vector<>();

				String id = rs.getString("BeverageID");
				String name = rs.getString("BeverageName");
				String type = rs.getString("BeverageType");
				int price = rs.getInt("BeveragePrice");
				int stock = rs.getInt("BeverageStock");

				rowBeverage.add(id);
				rowBeverage.add(name);
				rowBeverage.add(type);
				rowBeverage.add(price);
				rowBeverage.add(stock);
				dtmBeverage.addRow(rowBeverage);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		tableBeverage.setModel(dtmBeverage);

		spBeverage = new JScrollPane(tableBeverage);
		pnlTop.add(spBeverage);
		add(pnlTop, BorderLayout.NORTH);

	}

	private void setManageBeverageForm() {
		pnlBottom = new JPanel(new GridBagLayout());
		pnlBottom.setBackground(new Color(51, 204, 255));
		pnlBottom.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Manage Beverage Form
		gbc.insets = new Insets(5, 5, 5, 5);

		// New Beverage ID Label
		newBeverageID = new JLabel("New Beverage ID", SwingConstants.LEFT);
		newBeverageID.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlBottom.add(newBeverageID, gbc);

		// New Beverage ID TextField
		String query = "SELECT * FROM beverages WHERE BeverageID = (SELECT max(BeverageID) FROM beverages)";
		ResultSet rs = con.executeQuery(query);

		String newBeverageID = ""; // New Beverage ID
		try {
			while (rs.next()) {
				newBeverageID = rs.getString("BeverageID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtNewBeverageID = new JTextField();
		txtNewBeverageID.setText(generateBeverageID(newBeverageID));
		txtNewBeverageID.setEditable(false);
		txtNewBeverageID.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 1;
		gbc.gridy = 0;
		pnlBottom.add(txtNewBeverageID, gbc);

		// New Beverage Name Label
		newBeverageName = new JLabel("New Beverage Name", SwingConstants.LEFT);
		newBeverageName.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlBottom.add(newBeverageName, gbc);

		// New Beverage Name TextField
		txtNewBeverageName = new JTextField();
		txtNewBeverageName.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 1;
		gbc.gridy = 1;
		pnlBottom.add(txtNewBeverageName, gbc);

		// New Beverage Type Label
		newBeverageType = new JLabel("New Beverage Type", SwingConstants.LEFT);
		newBeverageType.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlBottom.add(newBeverageType, gbc);

		// New Beverage Type ComboBox
		comboNewBeverageType = new JComboBox(arrBeverageType);
		comboNewBeverageType.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 1;
		gbc.gridy = 2;
		pnlBottom.add(comboNewBeverageType, gbc);

		// New Beverage Price Label
		newBeveragePrice = new JLabel("New Beverage Price", SwingConstants.LEFT);
		newBeveragePrice.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 0;
		gbc.gridy = 3;
		pnlBottom.add(newBeveragePrice, gbc);

		// New Beverage Price TextField
		txtNewBeveragePrice = new JTextField();
		txtNewBeveragePrice.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 1;
		gbc.gridy = 3;
		pnlBottom.add(txtNewBeveragePrice, gbc);

		// New Beverage Stock Label
		newBeverageStock = new JLabel("New Beverage Stock", SwingConstants.LEFT);
		newBeverageStock.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 0;
		gbc.gridy = 4;
		pnlBottom.add(newBeverageStock, gbc);

		// New Beverage Stock Spinner
		spinNewBeverageStock = new JSpinner(modelNewBeverageStock);
		spinNewBeverageStock.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 1;
		gbc.gridy = 4;
		pnlBottom.add(spinNewBeverageStock, gbc);

		// Insert Beverage Button
		btnInsertBeverage = new JButton("Insert Beverage");

		btnInsertBeverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String newId = txtNewBeverageID.getText();
				String newName = txtNewBeverageName.getText().toString();
				String newType = comboNewBeverageType.getSelectedItem().toString();
				int newPrice = Integer.parseInt(txtNewBeveragePrice.getText());
				int newStock = Integer.parseInt(spinNewBeverageStock.getValue().toString());

				if (newBeverageNameValid() == true && newBeverageTypeValid() == true && newBeveragePriceValid() == true
						&& newBeverageStockValid() == true) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to add beverage stock?",
							"Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {

						// Masukin beverage baru ke database
						String query = String.format(
								"INSERT INTO beverages (BeverageID, BeverageName, BeverageType, BeveragePrice, BeverageStock) VALUES ('%s', '%s', '%s', %d, %d)",
								newId, newName, newType, newPrice, newStock);
						con.executeUpdate(query);

						// Masukin beverage baru ke tabel
						rowBeverage = new Vector<>();
						rowBeverage.add(newId);
						rowBeverage.add(newName);
						rowBeverage.add(newType);
						rowBeverage.add(newPrice);
						rowBeverage.add(newStock);
						dtmBeverage.addRow(rowBeverage);

						// Update New BeverageID
						// New Beverage ID TextField
						String query2 = "SELECT * FROM beverages WHERE BeverageID = (SELECT max(BeverageID) FROM beverages)";
						ResultSet rs = con.executeQuery(query2);

						String newBeverageID = ""; // New Beverage ID
						try {
							while (rs.next()) {
								newBeverageID = rs.getString("BeverageID");
							}
						} catch (SQLException exc) {
							// TODO Auto-generated catch block
							exc.printStackTrace();
						}
						txtNewBeverageID.setText(generateBeverageID(newBeverageID));
					}
				} else {
					JOptionPane.showMessageDialog(null, "Invalid data");
				}

			}
		});

		btnInsertBeverage.setPreferredSize(new Dimension(600, 30));
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlBottom.add(btnInsertBeverage, gbc);

		// Reset Button
		btnReset = new JButton("Reset");

		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtNewBeverageName.setText("");
				txtNewBeveragePrice.setText("");
				spinNewBeverageStock.setValue(1);

			}
		});

		btnReset.setPreferredSize(new Dimension(600, 30));
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlBottom.add(btnReset, gbc);

		// Beverage ID Label
		beverageID = new JLabel("Beverage ID", SwingConstants.LEFT);
		beverageID.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		pnlBottom.add(beverageID, gbc);

		// Beverage ID TextField
		txtBeverageID = new JTextField();
		txtBeverageID.setEditable(false);
		txtBeverageID.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 0;
		pnlBottom.add(txtBeverageID, gbc);

		// Beverage Name Label
		beverageName = new JLabel("Beverage Name", SwingConstants.LEFT);
		beverageName.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 1;
		pnlBottom.add(beverageName, gbc);

		// Beverage Name TextField
		txtBeverageName = new JTextField();
		txtBeverageName.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 1;
		pnlBottom.add(txtBeverageName, gbc);

		// Beverage Type Label
		beverageType = new JLabel("Beverage Type", SwingConstants.LEFT);
		beverageType.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 2;
		pnlBottom.add(beverageType, gbc);

		// Beverage Type ComboBox
		comboBeverageType = new JComboBox(arrBeverageType);
		comboBeverageType.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 2;
		pnlBottom.add(comboBeverageType, gbc);

		// Beverage Price Label
		beveragePrice = new JLabel("Beverage Price", SwingConstants.LEFT);
		beveragePrice.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 3;
		pnlBottom.add(beveragePrice, gbc);

		// Beverage Price TextField
		txtBeveragePrice = new JTextField();
		txtBeveragePrice.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 3;
		pnlBottom.add(txtBeveragePrice, gbc);

		// Beverage Stock Label
		beverageStock = new JLabel("Beverage Stock", SwingConstants.LEFT);
		beverageStock.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 4;
		pnlBottom.add(beverageStock, gbc);

		// Beverage Stock TextField
		txtBeverageStock = new JTextField();
		txtBeverageStock.setEditable(false);
		txtBeverageStock.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 4;
		pnlBottom.add(txtBeverageStock, gbc);

		// Update Beverage Button
		btnUpdateBeverage = new JButton("Update Beverage");

		btnUpdateBeverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtBeverageID.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill the beverage id!");
				} else if (beverageNameValid() == true && beverageTypeValid() == true && beveragePriceValid() == true) {
//					String query1 = "SELECT * FROM beverages WHERE BeverageID = '" + txtBeverageID.getText().toString() + "'";
//					ResultSet rs = con.executeQuery(query1);

					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to update beverage?",
							"Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						int row = tableBeverage.getSelectedRow();
						String query = String.format(
								"UPDATE beverages SET BeverageName = '%s', BeverageType = '%s', BeveragePrice = %d WHERE BeverageID = '%s'",
								txtBeverageName.getText().toString(), comboBeverageType.getSelectedItem().toString(),
								Integer.parseInt(txtBeveragePrice.getText()), dtmBeverage.getValueAt(row, 0));
						con.executeUpdate(query);

						// Update data di Beverage Table
						dtmBeverage.setValueAt(txtBeverageName.getText().toString(), tableBeverage.getSelectedRow(), 1);
						dtmBeverage.setValueAt(comboBeverageType.getSelectedItem().toString(),
								tableBeverage.getSelectedRow(), 2);
						dtmBeverage.setValueAt(Integer.parseInt(txtBeveragePrice.getText()),
								tableBeverage.getSelectedRow(), 3);
					}
				}

			}
		});

		btnUpdateBeverage.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlBottom.add(btnUpdateBeverage, gbc);

		// Delete Beverage Button
		btnDeleteBeverage = new JButton("Delete Beverage");

		btnDeleteBeverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!txtBeverageID.getText().isEmpty()) {

					String query = "DELETE FROM beverages WHERE BeverageID = '" + txtBeverageID.getText().toString() + "'";

					try {
						PreparedStatement ps = con.con.prepareStatement(query);
						ps.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Update data di Beverage Table
					int row = tableBeverage.getSelectedRow();
					dtmBeverage.removeRow(row);

				} else {
					JOptionPane.showMessageDialog(null, "Please select a beverage to update!");
				}

			}
		});

		btnDeleteBeverage.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlBottom.add(btnDeleteBeverage, gbc);

		// ===Add Stock===

		// Add Stock Label
		addStock = new JLabel("Add Stock", SwingConstants.LEFT);
		addStock.setPreferredSize(new Dimension(200, 30));

		// Add Stock Spinner
		spinAddStock = new JSpinner(modelAddStock);
		spinAddStock.setPreferredSize(new Dimension(200, 30));

		// Add Stock Button
		btnAddStock = new JButton("Add Stock");
		btnAddStock.setPreferredSize(new Dimension(200, 30));

		btnAddStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int addStock = (int) spinAddStock.getValue();
				int stockAtSelectedRow = (int) dtmBeverage.getValueAt(tableBeverage.getSelectedRow(), 4);
				int newStock = stockAtSelectedRow + addStock;

				if (!txtBeverageID.getText().isEmpty() && beverageStockValid() == true) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to add beverage stock?",
							"Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						String query1 = "UPDATE beverages SET BeverageStock = " + newStock + " WHERE BeverageID = '"
								+ tableBeverage.getValueAt(tableBeverage.getSelectedRow(), 0) + "'";
						con.executeUpdate(query1);

						// Update data di Beverage Table
						dtmBeverage.setValueAt(newStock, tableBeverage.getSelectedRow(), 4);

					}

				}

			}
		});

		btnAddStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		pnlAddStock = new JPanel(new FlowLayout());
		pnlAddStock.setBackground(new Color(51, 204, 255));
		pnlAddStock.add(addStock);
		pnlAddStock.add(spinAddStock);
		pnlAddStock.add(btnAddStock);

		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnlBottom.add(pnlAddStock, gbc);

		add(pnlBottom, BorderLayout.CENTER);
	}

	private String generateBeverageID(String id) {

		String x = "" + id.charAt(2) + id.charAt(3) + id.charAt(4); // Digit dari BeverageID terakhir yang ada di dalam
		// table beverages
		int digit1 = 0;
		int digit2 = 0;
		int idCount = Integer.parseInt(x);
		int digit3 = idCount + 1;

		if (digit3 > 9 && digit3 < 100) {
			digit2 = digit3 / 10;
			digit3 = digit3 % 10;

		} else if (digit3 > 99 && digit3 < 1000) {
			digit1 = digit3 / 100;
			digit2 = (digit3 / 10) % 10;
			digit3 = (digit3 % 100) % 10;
		}

		String beverageID = "BE" + digit1 + digit2 + digit3;

		return beverageID;
	}

	public boolean isNumeric(String str) {

		int a = 0;

		for (int j = 0; j < str.length(); j++) {
			if (Character.isDigit(str.charAt(j))) {
				a++;
			}
		}

		if (a == str.length()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean beverageStockValid() {

		if ((int) spinAddStock.getValue() > 0 && isNumeric(Integer.toString((int) spinAddStock.getValue()))) {
			return true;
		} else {
			return false;
		}

	}

	private boolean newBeverageStockValid() {

		if ((int) spinNewBeverageStock.getValue() > 0
				&& isNumeric(Integer.toString((int) spinNewBeverageStock.getValue()))) {
			return true;
		} else {
			return false;
		}

	}

	private boolean beveragePriceValid() {

		if (Integer.parseInt(txtBeveragePrice.getText()) > 0 && isNumeric(txtBeveragePrice.getText())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean newBeveragePriceValid() {

		if (Integer.parseInt(txtNewBeveragePrice.getText()) > 0 && isNumeric(txtNewBeveragePrice.getText())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean beverageTypeValid() {

		String beverageType = (String) comboBeverageType.getSelectedItem();

		if (beverageType.equals("Boba") || beverageType.equals("Coffee") || beverageType.equals("Tea")
				|| beverageType.equals("Smoothies")) {
			return true;
		} else {
			return false;
		}

	}

	private boolean newBeverageTypeValid() {

		String newBeverageType = (String) comboNewBeverageType.getSelectedItem();

		if (newBeverageType.equals("Boba") || newBeverageType.equals("Coffee") || newBeverageType.equals("Tea")
				|| newBeverageType.equals("Smoothies")) {
			return true;
		} else {
			return false;
		}

	}

	public boolean beverageNameValid() {

		try {
			if (txtBeverageName.getText().length() >= 5 && txtBeverageName.getText().length() <= 30) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean newBeverageNameValid() {

		try {
			if (txtNewBeverageName.getText().length() >= 5 && txtNewBeverageName.getText().length() <= 30) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private void setConfig() {
		setSize(1440, 850);
		setLayout(new BorderLayout());
		setBackground(new Color(51, 204, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setClosable(true);
	}

}
