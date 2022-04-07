package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import objects.Users;
import util.MySQLConnect;

public class LoginForm extends JFrame implements ActionListener, MouseListener {

	JLabel lblTitle, lblEmail, lblPassword, lblSignUp;
	JPanel pnlTitle, pnlLogin, pnlBottom, pnlBtnLogin;
	JTextField txtEmail;
	JPasswordField txtPassword;
	JButton btnLogin;
	GridBagConstraints gbc = new GridBagConstraints();
	MySQLConnect con = new MySQLConnect();

	public LoginForm() {

		setConfig();

		// Title
		pnlTitle = new JPanel();
		pnlTitle.setBackground(new Color(51, 204, 255));
		lblTitle = new JLabel("Login Form");
		lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.PLAIN, 24));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		pnlTitle.add(lblTitle);
		add(pnlTitle, BorderLayout.NORTH);

		setLoginForm();

		setVisible(true);
	}

	private void setLoginForm() {

		pnlLogin = new JPanel(new GridBagLayout());

		pnlLogin.setBackground(new Color(51, 204, 255));
		pnlBottom = new JPanel(new GridBagLayout());
		gbc.insets = new Insets(10, 30, 5, 30);

		// Email Label
		lblEmail = new JLabel("Email", SwingConstants.LEFT);
		lblEmail.setPreferredSize(new Dimension(75, 20));
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlLogin.add(lblEmail, gbc);

		// Email TextField
		txtEmail = new JTextField();
		txtEmail.setPreferredSize(new Dimension(200, 20));
		gbc.gridx = 1;
		gbc.gridy = 0;
		pnlLogin.add(txtEmail, gbc);

		// Password Label
		lblPassword = new JLabel("Password", SwingConstants.LEFT);
		lblPassword.setPreferredSize(new Dimension(75, 20));
		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlLogin.add(lblPassword, gbc);

		// Password PasswordField
		txtPassword = new JPasswordField();
		txtPassword.setPreferredSize(new Dimension(200, 20));
		gbc.gridx = 1;
		gbc.gridy = 1;
		pnlLogin.add(txtPassword, gbc);

		// Login Button
		pnlBtnLogin = new JPanel();
		btnLogin = new JButton("Login");
		btnLogin.setPreferredSize(new Dimension(75, 25));

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				String password = new String(txtPassword.getPassword());
				
				String query = "SELECT * FROM users WHERE UserEmail = '" + email + "' AND UserPassword = '" + password
						+ "'";
				ResultSet rs = con.executeQuery(query);

				try {
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Welcome, " + con.rs.getString("UserName"));
						Users.UserId = rs.getString("UserID");
						Users.UserName = rs.getString("UserName");
						Users.UserGender = rs.getString("UserGender");
						Users.UserAddress = rs.getString("UserAddress");
						Users.UserEmail = rs.getString("UserEmail");
						Users.UserPassword = rs.getString("UserPassword");
						Users.UserPhone = rs.getString("UserPhone");
						Users.UserRole= rs.getString("UserRole");

						dispose();
						if (rs.getString("UserRole").toString().equalsIgnoreCase("customer")) {
							new MainFormCustomer();
						} else if (rs.getString("UserRole").equalsIgnoreCase("admin")) {
							new MainFormAdmin();
						}

					} else {
						JOptionPane.showMessageDialog(null, "Wrong email/password!");
					}
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlBottom.add(btnLogin, gbc);

		// Sign Up Here
		lblSignUp = new JLabel("Sign Up Here");
		lblSignUp.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new RegisterForm();
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlBottom.add(lblSignUp, gbc);
		pnlBottom.setBackground(new Color(51, 204, 255));
		pnlBottom.setBorder(new EmptyBorder(5, 5, 5, 5));

		add(pnlLogin, BorderLayout.CENTER);
		add(pnlBottom, BorderLayout.SOUTH);

	}

	private void setConfig() {
		setSize(450, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		setTitle("Login Form");
		setLocationRelativeTo(null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	// ==============================================================

}
