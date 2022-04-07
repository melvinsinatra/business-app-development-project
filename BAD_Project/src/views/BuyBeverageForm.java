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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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

import objects.Users;
import util.MySQLConnect;

public class BuyBeverageForm extends JInternalFrame {

	JPanel pnlNorth, pnlCenter, pnlSouth, pnlLblTitle, pnlBuyBeverageForm, pnlBtnAddToCart, pnlCartButtons;
	JLabel lblTitle, lblID, lblName, lblType, lblPrice, lblStock, lblQty;
	JTextField txtID, txtName, txtType, txtPrice, txtStock;
	JSpinner spinQty;
	SpinnerModel modelQty = new SpinnerNumberModel(1, 1, 100, 1);
	JButton btnAddToCart, btnRemoveSelectedCart, btnClearCart, btnCheckout;
	GridBagConstraints gbc = new GridBagConstraints();
	JTable tableBeverage, tableCart;
	DefaultTableModel dtmBeverage, dtmCart;
	JScrollPane spBeverage, spCart;
	MySQLConnect con = new MySQLConnect();
	Vector<Object> rowBeverage;
	Vector<Object> rowBeverageInCart;
	Vector<String> columnBeverage = new Vector<>();
	Vector<String> columnCart = new Vector<>();
	PreparedStatement ps;

	public BuyBeverageForm() {
		setConfig();

		setBeverageTable();
		setBuyBeverageForm();
		setCartTable();

		setVisible(true);
	}

	private void setBeverageTable() {
		pnlNorth = new JPanel();
		pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
		pnlNorth.setPreferredSize(new Dimension(1440, 286));
		pnlNorth.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlNorth.setBackground(new Color(51, 204, 255));

		// Buy Beverage Label (Title)
		lblTitle = new JLabel("Buy Beverage", SwingConstants.CENTER);
		pnlLblTitle = new JPanel(new BorderLayout());
		pnlLblTitle.add(lblTitle);
		pnlLblTitle.setPreferredSize(new Dimension(1200, 30));
		pnlLblTitle.setBackground(new Color(51, 204, 255));
		pnlNorth.add(pnlLblTitle);

		// Beverage Table
		columnBeverage.add("Beverage Id");
		columnBeverage.add("Beverage Name");
		columnBeverage.add("Beverage Type");
		columnBeverage.add("Beverage Price");
		columnBeverage.add("Beverage Stock");

		dtmBeverage = new DefaultTableModel(columnBeverage, 0);
		tableBeverage = new JTable(dtmBeverage);
		tableBeverage.getTableHeader().setReorderingAllowed(false);

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
		tableBeverage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableBeverage.getSelectedRow();
				txtID.setText(dtmBeverage.getValueAt(row, 0).toString());
				txtName.setText(dtmBeverage.getValueAt(row, 1).toString());
				txtType.setText(dtmBeverage.getValueAt(row, 2).toString());
				txtPrice.setText(dtmBeverage.getValueAt(row, 3).toString());
				txtStock.setText(dtmBeverage.getValueAt(row, 4).toString());
			}
		});

		spBeverage = new JScrollPane(tableBeverage);
		pnlNorth.add(spBeverage);
		add(pnlNorth, BorderLayout.NORTH);

	}

	private void setBuyBeverageForm() {
		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		pnlCenter.setPreferredSize(new Dimension(1440, 276));
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlCenter.setBackground(new Color(51, 204, 255));

		pnlBuyBeverageForm = new JPanel(new GridBagLayout());
		pnlBuyBeverageForm.setBackground(new Color(51, 204, 255));
		gbc.insets = new Insets(5, 5, 5, 5);

		// Beverage ID Label
		lblID = new JLabel("Beverage ID", SwingConstants.LEFT);
		lblID.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlBuyBeverageForm.add(lblID, gbc);

		// Beverage ID TextField
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 0;
		pnlBuyBeverageForm.add(txtID, gbc);

		// Beverage Name Label
		lblName = new JLabel("Beverage Name", SwingConstants.LEFT);
		lblName.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlBuyBeverageForm.add(lblName, gbc);

		// Beverage Name TextField
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 1;
		pnlBuyBeverageForm.add(txtName, gbc);

		// Beverage Type Label
		lblType = new JLabel("Beverage Type", SwingConstants.LEFT);
		lblType.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlBuyBeverageForm.add(lblType, gbc);

		// Beverage Type TextField
		txtType = new JTextField();
		txtType.setEditable(false);
		txtType.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 2;
		pnlBuyBeverageForm.add(txtType, gbc);

		// Beverage Price Label
		lblPrice = new JLabel("Beverage Price", SwingConstants.LEFT);
		lblPrice.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 2;
		gbc.gridy = 0;
		pnlBuyBeverageForm.add(lblPrice, gbc);

		// Beverage Price TextField
		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 0;
		pnlBuyBeverageForm.add(txtPrice, gbc);

		// Beverage Stock Label
		lblStock = new JLabel("Beverage Stock", SwingConstants.LEFT);
		lblStock.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 2;
		gbc.gridy = 1;
		pnlBuyBeverageForm.add(lblStock, gbc);

		// Beverage Stock TextField
		txtStock = new JTextField();
		txtStock.setEditable(false);
		txtStock.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 1;
		pnlBuyBeverageForm.add(txtStock, gbc);

		// Beverage Quantity Label
		lblQty = new JLabel("Beverage Quantity", SwingConstants.LEFT);
		lblQty.setPreferredSize(new Dimension(250, 30));
		gbc.gridx = 2;
		gbc.gridy = 2;
		pnlBuyBeverageForm.add(lblQty, gbc);

		// Beverage Quantity Spinner
		spinQty = new JSpinner(modelQty);
		spinQty.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 2;
		pnlBuyBeverageForm.add(spinQty, gbc);

		pnlCenter.add(pnlBuyBeverageForm);

		// Add to Cart Button
		btnAddToCart = new JButton("Add to Cart");

		btnAddToCart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Query untuk mencari dan mengambil data Beverage yang di select

				if (tableBeverage.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select the beverage!");
				} else {
					String query1 = "SELECT * FROM beverages WHERE BeverageID = '" + txtID.getText() + "'";
					ResultSet rs = con.executeQuery(query1);
					try {
						if (rs.next()) {

							// Menampung data Beverage yang di select
							String id = con.rs.getString("BeverageID");
							String name = con.rs.getString("BeverageName");
							String type = con.rs.getString("BeverageType");
							Integer price = con.rs.getInt("BeveragePrice");
							Integer stock = con.rs.getInt("BeverageStock");

							if (stock > 0) {
								int row = tableBeverage.getSelectedRow();
								int qty = (int) spinQty.getValue();
								int subTot = (Integer.parseInt(txtPrice.getText()) * qty);
								String subTotal = "Rp. " + subTot + ",-";

								// Mencari data yang duplikat pada tabel Cart
								int rowCount = tableCart.getRowCount();
								int rowExists = 0;

								rowBeverageInCart = new Vector<>();
								rowBeverageInCart.add(id);
								rowBeverageInCart.add(name);
								rowBeverageInCart.add(type);
								rowBeverageInCart.add(price);
								rowBeverageInCart.add(stock);
								rowBeverageInCart.add(qty);
								rowBeverageInCart.add(subTotal);
								dtmCart.addRow(rowBeverageInCart);

								String idInCart = "";
								for (int i = 0; i < rowCount; i++) {
									idInCart = tableCart.getValueAt(i, 0).toString();

									if (txtID.getText().toString().equals(idInCart)) {
										rowExists = i;

										// Quantity Baru
										int qtyInCart = (int) dtmCart.getValueAt(rowExists, 5);
										int newQty = (qtyInCart + (int) spinQty.getValue());
										dtmCart.setValueAt(newQty, rowExists, 5);

										// SubTotal baru
										int newSubTot = (Integer.parseInt(txtPrice.getText())
												* (int) dtmCart.getValueAt(rowExists, 5));
//										int newSubTot = subTot + addSubTot;
										String newSubTotal = "Rp. " + newSubTot + ",-";
										dtmCart.setValueAt(newSubTotal, rowExists, 6);

										dtmCart.removeRow(rowExists + 1);
									}

								}

								// Update stock di database beverage kalo ada yang dupe
								int newStock = (int) con.rs.getInt("BeverageStock") - qty;
								String query2 = "UPDATE beverages SET BeverageStock = " + newStock
										+ " WHERE BeverageID = '" + dtmBeverage.getValueAt(row, 0).toString() + "'";
								con.executeUpdate(query2);

								// Update stock di Beverage Table
								dtmBeverage.setValueAt(newStock, tableBeverage.getSelectedRow(), 4);
								JOptionPane.showMessageDialog(null, "Successfully Insert Cart!");

								// Insert data into carts database
								String query3 = "SELECT * FROM carts WHERE BeverageID = '" + id + "'";
								rs = con.executeQuery(query3);
								if (rs.next() == true) {

									// Update stock where BeverageID already exists
									String query4 = "UPDATE carts SET Quantity = " + (rs.getInt("Quantity") + qty)
											+ " WHERE BeverageID = '" + id + "'";
									con.executeUpdate(query4);

								} else {
									// Masukan Beverage yang diinsert pada database cart
									String query5 = String.format(
											"INSERT INTO carts (UserID, BeverageID, Quantity) VALUES ('%s', '%s', %d)",
											Users.getUserId(), id, qty);
									con.executeUpdate(query5);
								}

							} else {
								JOptionPane.showMessageDialog(null, "There is no more stock for this beverage!");
							}

						}

					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}

			}
		});

		btnAddToCart.setPreferredSize(new Dimension(250, 60));

		pnlCenter.add(btnAddToCart);

		add(pnlCenter, BorderLayout.CENTER);
	}

	private void setCartTable() {
		pnlSouth = new JPanel();
		pnlSouth.setLayout(new BoxLayout(pnlSouth, BoxLayout.Y_AXIS));
		pnlSouth.setPreferredSize(new Dimension(1440, 276));
		pnlSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlSouth.setBackground(new Color(51, 204, 255));

		// Cart Table
		columnCart.add("Beverage ID");
		columnCart.add("Beverage Name");
		columnCart.add("Beverage Type");
		columnCart.add("Beverage Price");
		columnCart.add("Beverage Stock");
		columnCart.add("Beverage Quantity");
		columnCart.add("Sub Total");

		dtmCart = new DefaultTableModel(columnCart, 0);
		tableCart = new JTable(dtmCart);
		tableCart.getTableHeader().setReorderingAllowed(false);
		tableCart.setModel(dtmCart);

		// Adding Beverages to table / Filling Table from database
		rowBeverageInCart = new Vector<>();
		try {
			String query = "SELECT * FROM carts JOIN beverages ON carts.BeverageID = beverages.BeverageId";
			ResultSet rs = con.executeQuery(query);

			while (rs.next()) {
				rowBeverageInCart = new Vector<>();

				String id = rs.getString("BeverageID");
				String name = rs.getString("BeverageName");
				String type = rs.getString("BeverageType");
				int price = rs.getInt("BeveragePrice");
				int stock = rs.getInt("BeverageStock");
				int qty = rs.getInt("Quantity");
				int subTot = price * rs.getInt("Quantity");
				String subTotal = "Rp. " + subTot + ",-";

				rowBeverageInCart.add(id);
				rowBeverageInCart.add(name);
				rowBeverageInCart.add(type);
				rowBeverageInCart.add(price);
				rowBeverageInCart.add(stock);
				rowBeverageInCart.add(qty);
				rowBeverageInCart.add(subTotal);
				dtmCart.addRow(rowBeverageInCart);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		spCart = new JScrollPane(tableCart);
		pnlSouth.add(spCart);

		// Cart Buttons
		pnlCartButtons = new JPanel(new FlowLayout());
		pnlCartButtons.setBackground(new Color(51, 204, 255));

		btnRemoveSelectedCart = new JButton("Remove Selected Cart");

		btnRemoveSelectedCart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (tableCart.getSelectionModel().isSelectionEmpty() == true) {
					JOptionPane.showMessageDialog(null, "Please select a cart!");
				} else {
					int selectedRow = tableCart.getSelectedRow();

					String query = "DELETE FROM carts WHERE BeverageID = '" + dtmCart.getValueAt(selectedRow, 0) + "'";
					PreparedStatement ps = con.prepareStatement(query);
					try {
						ps.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Update stock di Beverage Table
					for (int i = 0; i < tableBeverage.getRowCount(); i++) {
						String beverageIDBeverage = dtmBeverage.getValueAt(i, 0).toString();
						String beverageIDCart = dtmCart.getValueAt(selectedRow, 0).toString();
						if (beverageIDCart.equals(beverageIDBeverage)) {
							int stock = (int) dtmBeverage.getValueAt(i, 4);
							int qty = (int) dtmCart.getValueAt(tableCart.getSelectedRow(), 5);
							dtmBeverage.setValueAt(stock + qty, i, 4);
						}
					}

					dtmCart.removeRow(selectedRow);
					JOptionPane.showMessageDialog(null, "Successfully remove selected cart!");
				}

			}
		});

		btnRemoveSelectedCart.setPreferredSize(new Dimension(466, 25));
		btnClearCart = new JButton("Clear Cart");

		btnClearCart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (tableCart.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Cart is empty!");
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to clear cart?",
							"Confirmation Message", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {

						ResultSet rs = con.executeQuery(
								"SELECT * FROM carts JOIN beverages ON carts.BeverageID = beverages.BeverageID");

						try {
							while (rs.next()) {

								int stock = rs.getInt("BeverageStock");// Menampung stock yang diletakkan di dalam cart
								int qty = rs.getInt("Quantity");// Menampung quantity yang diletakkan di dalam cart
								String id = con.rs.getString("BeverageId");// Menampung BeverageID
								int row = tableBeverage.getSelectedRow();

								String query1 = "UPDATE beverages SET BeverageStock = " + (stock + qty)
										+ " WHERE BeverageID = '" + id + "'";
								PreparedStatement ps = con.prepareStatement(query1);
								ps.execute();

								// Update data di tabel Beverages
								for (int i = 0; i < tableBeverage.getRowCount(); i++) {
									if (rs.getString("BeverageID").equals(dtmBeverage.getValueAt(i, 0))) {
										dtmBeverage.setValueAt(stock + qty, i, 4);
									}
								}

							}

							// Mengosongkan tabel carts di database
							String query2 = "DELETE FROM carts WHERE UserID = '" + Users.UserId + "'";
							con.executeUpdate(query2);
							// Clear Table
							for (int i = 0; i < dtmCart.getRowCount(); i++) {
								dtmCart.removeRow(i);
							}
							dtmCart.setRowCount(0);

						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
				}

			}
		});

		btnClearCart.setPreferredSize(new Dimension(466, 25));
		btnCheckout = new JButton("Checkout");

		btnCheckout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (tableCart.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Cart is empty!");
				} else {

					// Generate transactionID
					String query1 = "SELECT * FROM headertransactions WHERE TransactionID = (SELECT max(TransactionID) FROM headertransactions)";
					ResultSet rs = con.executeQuery(query1);

					String lastID = "";
					try {
						while (rs.next()) {
							lastID = rs.getString("TransactionID");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Insert data ke database di tabel headertransactions
					String transactionID = generateTransactionID(lastID);
					String query2 = String.format("INSERT INTO headertransactions VALUES ('%s', '%s', '%s')",
							transactionID, Users.UserId, getLocalDate().toString());
					PreparedStatement ps1 = con.prepareStatement(query2);
					try {
						ps1.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Insert data ke database di tabel detailtransactions
					for (int i = 0; i < dtmCart.getRowCount(); i++) {
						String query3 = String.format("INSERT INTO detailtransactions VALUES ('%s', '%s', %d)",
								transactionID, dtmCart.getValueAt(i, 0), dtmCart.getValueAt(i, 5));
						PreparedStatement ps2 = con.prepareStatement(query3);
						try {
							ps2.execute();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					// Clear cart table in database
					String query = "DELETE FROM carts WHERE UserID = '" + Users.UserId + "'";
					con.executeUpdate(query);

					// Clear cart table
					for (int i = 0; i < dtmCart.getRowCount() + 1; i++) {
						dtmCart.removeRow(i);
					}
					dtmCart.setRowCount(0);
					JOptionPane.showMessageDialog(null, "Successfully checkout cart!");
				}

			}
		});

		btnCheckout.setPreferredSize(new Dimension(466, 25));
		pnlCartButtons.add(btnRemoveSelectedCart);
		pnlCartButtons.add(btnClearCart);
		pnlCartButtons.add(btnCheckout);

		pnlSouth.add(pnlCartButtons);

		add(pnlSouth, BorderLayout.SOUTH);
	}

	private LocalDate getLocalDate() {
		Date input = new Date();
		Instant instant = input.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		LocalDate date = zdt.toLocalDate();

		return date;
	}

	private String generateTransactionID(String id) {

		String x = "" + id.charAt(2) + id.charAt(3) + id.charAt(4); // Digit dari TransactionID terakhir yang ada di
																	// dalam table transactionHeader
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

		String transactionID = "TR" + digit1 + digit2 + digit3;

		return transactionID;
	}

	private void setConfig() {
		setSize(1440, 860);
		setResizable(false);
		setLayout(new BorderLayout());
		setBackground(new Color(51, 204, 255));
		setClosable(true);
		setMaximizable(true);
	}

}
