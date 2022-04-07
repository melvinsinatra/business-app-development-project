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

public class MainFormAdmin extends JFrame {

	JMenu profile, manage, transaction;
	JMenuBar mb;
	JMenuItem editProfile, logOff, exit, buyBeverage, viewTransactionHistory, manageBeverage;
	JLabel lblWelcome;
	JPanel pnlCenter;
	JDesktopPane dp = new JDesktopPane();

	EditProfileForm editProfileForm;
	BuyBeverageForm buyBeverageForm;
	TransactionHistoryForm transactionHistoryForm;
	ManageBeverageForm manageBeverageForm;

	public MainFormAdmin() {
		setConfig();

		setMenuAdmin();

		// Welcome Label
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBackground(new Color(51, 204, 255));
		lblWelcome = new JLabel("Welcome to Janji Jywa, " + Users.UserName + ".", SwingConstants.CENTER);
		lblWelcome.setFont(new Font(lblWelcome.getFont().getName(), Font.PLAIN, 36));
		pnlCenter.add(lblWelcome);
		add(pnlCenter, BorderLayout.CENTER);

		setVisible(true);
	}

	private void setMenuAdmin() {

		// MenuBar
		mb = new JMenuBar();
		setJMenuBar(mb);
		mb.setBackground(new Color(51, 204, 255));
		profile = new JMenu("Profile");
		manage = new JMenu("Manage");
		mb.add(profile);
		mb.add(manage);

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

		// Manage Menu
		manageBeverage = new JMenuItem("Manage Beverage");
		manage.add(manageBeverage);
		manageBeverage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dp.removeAll();
				manageBeverageForm = new ManageBeverageForm();
				dp.add(manageBeverageForm);

			}
		});
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
