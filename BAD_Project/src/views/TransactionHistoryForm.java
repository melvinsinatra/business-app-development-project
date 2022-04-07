package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import util.MySQLConnect;

public class TransactionHistoryForm extends JInternalFrame {

	JLabel lblTitle, lblSelectedID, lblGrandTotal;
	JPanel pnlTop, pnlBottom, pnlLblTitle, pnlSelectedID, pnlGrandTotal;
	JTextField txtSelectedID, txtGrandTotal;
	JTable tableTransactionHistory, tableTransactionDetail;
	DefaultTableModel dtmTransactionHistory, dtmTransactionDetail;
	JScrollPane spTransactionHistory, spTransactionDetail;
	Vector<String> columnTransactionHistory = new Vector<>();
	Vector<String> columnTransactionDetail = new Vector<>();
	Vector<Object> rowTransactionsHistory = new Vector<>();
	Vector<Object> rowTransactionDetails;
	MySQLConnect con = new MySQLConnect();

	public TransactionHistoryForm() {
		setConfig();
		setTransactionHistoryTable();
		setTransactionDetailsTable();
		setVisible(true);
	}

	private void setTransactionHistoryTable() {
		pnlTop = new JPanel();
		pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
		pnlTop.setPreferredSize(new Dimension(1430, 410));
		pnlTop.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlTop.setBackground(new Color(51, 204, 255));

		// Transaction History Label (Title)
		lblTitle = new JLabel("Transaction History", SwingConstants.CENTER);
		pnlLblTitle = new JPanel(new BorderLayout());
		pnlLblTitle.add(lblTitle);
		pnlLblTitle.setPreferredSize(new Dimension(1200, 30));
		pnlLblTitle.setBackground(new Color(51, 204, 255));
		pnlTop.add(pnlLblTitle);

		// Transaction History Table
		columnTransactionHistory.add("Transaction ID");
		columnTransactionHistory.add("User ID");
		columnTransactionHistory.add("Transaction Date");

		dtmTransactionHistory = new DefaultTableModel(columnTransactionHistory, 0);
		tableTransactionHistory = new JTable(dtmTransactionHistory);
		tableTransactionHistory.setModel(dtmTransactionHistory);

		tableTransactionHistory.addMouseListener(new MouseListener() {

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
				dtmTransactionDetail.setRowCount(0);
				int row = tableTransactionHistory.getSelectedRow();
				String selectedID = dtmTransactionHistory.getValueAt(row, 0).toString();
				txtSelectedID.setText(selectedID);

				String query = "SELECT * FROM detailtransactions JOIN beverages ON detailtransactions.BeverageID=beverages.BeverageID WHERE TransactionID = '"
						+ selectedID + "'";

				ResultSet rs = con.executeQuery(query);

				try {
					int grandTotal = 0;
					while (rs.next()) {
						int a = (int) (rs.getInt("BeveragePrice") * rs.getInt("Quantity")); // Untuk menampung subTotal
						rowTransactionDetails = new Vector<>();
						String transactionID = rs.getString("TransactionID");
						String beverageID = rs.getString("BeverageID");
						String beverageName = rs.getString("BeverageName");
						String beverageType = rs.getString("BeverageType");
						String beveragePrice = rs.getString("BeveragePrice");
						String qty = rs.getString("Quantity");
						String subTotal = "Rp. " + a + ",-";

						rowTransactionDetails.add(transactionID);
						rowTransactionDetails.add(beverageID);
						rowTransactionDetails.add(beverageName);
						rowTransactionDetails.add(beverageType);
						rowTransactionDetails.add(beveragePrice);
						rowTransactionDetails.add(qty);
						rowTransactionDetails.add(subTotal);
						dtmTransactionDetail.addRow(rowTransactionDetails);

						grandTotal = grandTotal + a;

					}
					txtGrandTotal.setText("Rp, " + grandTotal + ",-");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		tableTransactionHistory.getTableHeader().setReorderingAllowed(false);
		spTransactionHistory = new JScrollPane(tableTransactionHistory);

		// Mengambil data dari database dan dimasukkan ke dalam
		// tableTransactionHistory
		String query1 = "SELECT * FROM headertransactions";
		ResultSet rs = con.executeQuery(query1);

		try {
			while (rs.next() == true) {
				rowTransactionsHistory = new Vector<>();
				// Menampung data Transaction
				String transactionID = rs.getString("TransactionID");
				String userID = rs.getString("UserID");
				Date transactionDate = rs.getDate("TransactionDate");

				rowTransactionsHistory.add(transactionID);
				rowTransactionsHistory.add(userID);
				rowTransactionsHistory.add(transactionDate);
				dtmTransactionHistory.addRow(rowTransactionsHistory);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		pnlTop.add(spTransactionHistory);

		// Selected ID
		pnlSelectedID = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSelectedID.setBackground(new Color(51, 204, 255));

		lblSelectedID = new JLabel("Selected ID", SwingConstants.LEFT);
		pnlSelectedID.add(lblSelectedID);

		txtSelectedID = new JTextField();
		txtSelectedID.setEditable(false);
		txtSelectedID.setPreferredSize(new Dimension(200, 30));
		pnlSelectedID.add(txtSelectedID);

		pnlTop.add(pnlSelectedID);
		add(pnlTop, BorderLayout.NORTH);
	}

	private void setTransactionDetailsTable() {
		pnlBottom = new JPanel();
		pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.Y_AXIS));
		pnlBottom.setPreferredSize(new Dimension(1430, 410));
		pnlBottom.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlBottom.setBackground(new Color(51, 204, 255));

		// Transaction Detail Table
		columnTransactionDetail.add("Transaction ID");
		columnTransactionDetail.add("Beverage ID");
		columnTransactionDetail.add("Beverage Name");
		columnTransactionDetail.add("Beverage Type");
		columnTransactionDetail.add("Beverage Price");
		columnTransactionDetail.add("Beverage Quantity");
		columnTransactionDetail.add("Sub Total");

		dtmTransactionDetail = new DefaultTableModel(columnTransactionDetail, 0);
		tableTransactionDetail = new JTable(dtmTransactionDetail);
		tableTransactionDetail.getTableHeader().setReorderingAllowed(false);
		tableTransactionDetail.setModel(dtmTransactionDetail);
		spTransactionDetail = new JScrollPane(tableTransactionDetail);
		pnlBottom.add(spTransactionDetail);

		// Grand Total
		pnlGrandTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlGrandTotal.setBackground(new Color(51, 204, 255));

		lblGrandTotal = new JLabel("Grand Total", SwingConstants.RIGHT);
		pnlGrandTotal.add(lblGrandTotal);

		txtGrandTotal = new JTextField("Rp. 0,-");
		txtGrandTotal.setEditable(false);
		txtGrandTotal.setPreferredSize(new Dimension(200, 30));
		pnlGrandTotal.add(txtGrandTotal);
		pnlBottom.add(pnlGrandTotal);

		add(pnlBottom, BorderLayout.SOUTH);

	}

	private void setConfig() {
		setSize(1440, 850);
		setLayout(new BorderLayout());
		setBackground(new Color(51, 204, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setClosable(true);
		setMaximizable(true);
	}

}
