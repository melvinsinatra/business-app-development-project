package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.Beverage;
import objects.Users;
import util.MySQLConnect;

public class MainFormCustomer extends JFrame {

	JMenu profile, manage, transaction;
	JMenuBar mb;
	JMenuItem editProfile, logOff, exit, buyBeverage, viewTransactionHistory, manageBeverage;
	JLabel lblWelcome;
	JPanel pnlCenter;
	JDesktopPane dp = new JDesktopPane();
	MySQLConnect con = new MySQLConnect();

	EditProfileForm editProfileForm;
	BuyBeverageForm buyBeverageForm;
	TransactionHistoryForm transactionHistoryForm;
	ManageBeverageForm manageBeverageForm;

	public MainFormCustomer() {
		setConfig();

		setMenuCustomer();
		setWelcomeLabel();

		setVisible(true);
	}

	private void setMenuCustomer() {

		// MenuBar
		mb = new JMenuBar();
		setJMenuBar(mb);
		mb.setBackground(new Color(51, 204, 255));
		profile = new JMenu("Profile");
		transaction = new JMenu("Transaction");
		mb.add(profile);
		mb.add(transaction);

		// ===Profile Menu===
		// Edit Profile
		editProfile = new JMenuItem("Edit Profile");
		profile.add(editProfile);
		editProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dp.removeAll();
				editProfileForm = new EditProfileForm();
				dp.add(editProfileForm);

			}
		});

		// Log Off
		logOff = new JMenuItem("Log Off");
		profile.add(logOff);
		logOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginForm();
			}
		});

		// Exit
		exit = new JMenuItem("Exit");
		profile.add(exit);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// ===Transaction Menu===
		// Buy Beverage
		buyBeverage = new JMenuItem("Buy Beverage");
		transaction.add(buyBeverage);
		buyBeverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dp.removeAll();
				setWelcomeLabel();
				buyBeverageForm = new BuyBeverageForm();
				dp.add(buyBeverageForm);

			}
		});

		// View Transaction History
		viewTransactionHistory = new JMenuItem("View Transaction History");
		transaction.add(viewTransactionHistory);
		viewTransactionHistory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dp.removeAll();
				setWelcomeLabel();
				transactionHistoryForm = new TransactionHistoryForm();
				dp.add(transactionHistoryForm);

			}
		});
	}

	private void setWelcomeLabel() {
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(new Color(51, 204, 255));
		lblWelcome = new JLabel("Welcome to Janji Jywa, " + Users.UserName + ".", SwingConstants.CENTER);
		lblWelcome.setFont(new Font(lblWelcome.getFont().getName(), Font.PLAIN, 36));
		pnlCenter.add(lblWelcome);
		add(pnlCenter, BorderLayout.CENTER);
	}

	private void setConfig() {
		setTitle("Main Form");
		setContentPane(dp);
		dp.setBackground(new Color(51, 204, 255));
		setSize(1440, 900);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(new Color(51, 204, 255));
	}

}
