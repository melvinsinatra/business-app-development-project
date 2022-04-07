package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.MySQLConnect;

public class RegisterForm extends JFrame {

	JLabel lblTitle, lblID, lblUserName, lblEmail, lblPhone, lblAddress, lblPassword, lblGender, lblRole, lblSignIn;
	JPanel pnlTitle, pnlForm, pnlGenders, pnlBottom;
	JTextField txtID, txtUserName, txtEmail, txtPhone;
	JTextArea txtAddress;
	JPasswordField txtPassword;
	JRadioButton rbMale, rbFemale;
	JComboBox boxRole;
	JButton btnRegister;
	ButtonGroup bg = new ButtonGroup();
	GridBagConstraints gbc = new GridBagConstraints();
	MySQLConnect con = new MySQLConnect();

	public RegisterForm() {

		setConfig();

		// Title
		pnlTitle = new JPanel();
		pnlTitle.setBackground(new Color(51, 204, 255));
		lblTitle = new JLabel("Register Form");
		lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.PLAIN, 24));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		pnlTitle.add(lblTitle);
		add(pnlTitle, BorderLayout.NORTH);

		setRegisterForm();

		setVisible(true);
	}

	private void setRegisterForm() {

		pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setBackground(new Color(51, 204, 255));
		gbc.insets = new Insets(10, 30, 5, 30);

		// ID Label
		lblID = new JLabel("ID", SwingConstants.LEFT);
		lblID.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlForm.add(lblID, gbc);

		// ID TextField
		// generateID
		String query = "SELECT * FROM users WHERE UserID = (SELECT max(UserID) FROM users)";
		ResultSet rs = con.executeQuery(query);

		String userID = "";
		try {
			while (rs.next()) {
				userID = rs.getString("UserID");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		txtID = new JTextField(generateUserID(userID));
		txtID.setEnabled(false);
		txtID.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 0;
		pnlForm.add(txtID, gbc);

		// User Name Label
		lblUserName = new JLabel("User Name", SwingConstants.LEFT);
		lblUserName.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlForm.add(lblUserName, gbc);

		// User Name TextField
		txtUserName = new JTextField();
		txtUserName.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 1;
		pnlForm.add(txtUserName, gbc);

		// Email Label
		lblEmail = new JLabel("Email", SwingConstants.LEFT);
		lblEmail.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlForm.add(lblEmail, gbc);

		// Email TextField
		txtEmail = new JTextField();
		txtEmail.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 2;
		pnlForm.add(txtEmail, gbc);

		// Phone Label
		lblPhone = new JLabel("Phone", SwingConstants.LEFT);
		lblPhone.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 3;
		pnlForm.add(lblPhone, gbc);

		// Phone TextField
		txtPhone = new JTextField();
		txtPhone.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 3;
		pnlForm.add(txtPhone, gbc);

		// Address Label
		lblAddress = new JLabel("Address", SwingConstants.LEFT);
		lblAddress.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 4;
		pnlForm.add(lblAddress, gbc);

		// Address TextArea
		txtAddress = new JTextArea(3, 1);
		txtAddress.setPreferredSize(new Dimension(350, 60));
		gbc.gridx = 1;
		gbc.gridy = 4;
		pnlForm.add(txtAddress, gbc);

		// Password Field
		lblPassword = new JLabel("Password", SwingConstants.LEFT);
		lblPassword.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 5;
		pnlForm.add(lblPassword, gbc);

		// Password PasswordField
		txtPassword = new JPasswordField();
		txtPassword.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 5;
		pnlForm.add(txtPassword, gbc);

		// Gender Field
		lblGender = new JLabel("Gender", SwingConstants.LEFT);
		lblGender.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 6;
		pnlForm.add(lblGender, gbc);

		// Gender Radio Buttons
		rbMale = new JRadioButton("Male");
		rbMale.setBackground(new Color(51, 204, 255));
		rbFemale = new JRadioButton("Female");
		rbFemale.setBackground(new Color(51, 204, 255));
		bg.add(rbFemale);
		bg.add(rbMale);
		pnlGenders = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlGenders.add(rbFemale);
		pnlGenders.add(rbMale);
		pnlGenders.setPreferredSize(new Dimension(350, 30));
		pnlGenders.setBackground(new Color(51, 204, 255));
		gbc.gridx = 1;
		gbc.gridy = 6;
		pnlForm.add(pnlGenders, gbc);

		// Role Field
		lblRole = new JLabel("Role", SwingConstants.LEFT);
		lblRole.setPreferredSize(new Dimension(65, 30));
		gbc.gridx = 0;
		gbc.gridy = 7;
		pnlForm.add(lblRole, gbc);

		// Role ComboBox
		String[] roles = { "Admin", "Customer" };
		boxRole = new JComboBox(roles);
		boxRole.setPreferredSize(new Dimension(350, 30));
		gbc.gridx = 1;
		gbc.gridy = 7;
		pnlForm.add(boxRole, gbc);

		add(pnlForm, BorderLayout.CENTER);

		// Bottom Panel
		pnlBottom = new JPanel(new GridBagLayout());
		pnlBottom.setBackground(new Color(51, 204, 255));
		add(pnlBottom, BorderLayout.SOUTH);
		// Register Button
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (userNameValid() == true && emailValid() == true && phoneNumberValid() == true
						&& addressValid() == true && genderValid() == true && passwordValid() == true) {
					con.executeUpdate(String.format(
							"INSERT INTO users (UserID, UserName, UserEmail, UserPassword, UserGender, UserAddress, UserPhone, UserRole) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
							txtID.getText(), txtUserName.getText(), txtEmail.getText(),
							new String(txtPassword.getPassword()), genderChoice(), txtAddress.getText(),
							txtPhone.getText(), boxRole.getSelectedItem().toString()));
					JOptionPane.showMessageDialog(null, "Successfully register!");
				}
				if (userNameValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid name!");
				}
				if (emailValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid email!");
				}
				if (passwordValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid password!");
				}
				if (phoneNumberValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid phone number!");
				}
				if (addressValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid address");
				}
				if (genderValid() == false) {
					JOptionPane.showMessageDialog(null, "Please select a gender");
				}

			}
		});
		btnRegister.setPreferredSize(new Dimension(100, 25));
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlBottom.add(btnRegister, gbc);

		// Sign In
		lblSignIn = new JLabel("Sign In");
		lblSignIn.addMouseListener(new MouseListener() {

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
				new LoginForm();
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlBottom.add(lblSignIn, gbc);
		pnlBottom.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private String generateUserID(String id) {

		String x = "" + id.charAt(2) + id.charAt(3) + id.charAt(4); // Digit dari UserID terakhir yang ada di dalam
		// table users
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

		String UserID = "US" + digit1 + digit2+ digit3;

		return UserID;
	}

	public boolean passwordValid() {

		String password = new String(txtPassword.getPassword());

		try {
			if (password.length() >= 5 && password.length() <= 30 && isAlphaNumeric(password) == true) {
				return true;

			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public String genderChoice() {

		String gender = "";

		if (rbMale.isSelected()) {
			gender = "Male";
		} else if (rbFemale.isSelected()) {
			gender = "Female";
		}

		return gender;
	}

	public boolean genderValid() {

		if (rbMale.isSelected() || rbFemale.isSelected()) {
			return true;
		}

		else {
			return false;
		}
	}

	public boolean addressValid() {

		try {
			if (txtAddress.getText().length() >= 10 && txtAddress.getText().endsWith(" Street")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean phoneNumberValid() {

		try {
			if (txtPhone.getText().length() >= 12 && isNumeric(txtPhone.getText()) == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean userNameValid() {

		try {
			if (txtUserName.getText().length() >= 5 && txtUserName.getText().length() <= 30) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean emailValid() {

		// [email]@[provider].[domain]

		int count1 = 0; // Count '@'
		int count2 = 0; // Count '.'
		boolean a = false; // Validation for Email must contain exactly one '@'
		boolean b = false; // Validation for Email must contain exactly one '.'
		boolean c = false; // Validation for Character '@' must not be next to '.'
		String email = txtEmail.getText();

		// Character '@' must not be next to '.'
		try {
			if (email.charAt(email.indexOf('.') - 1) != '@' && email.charAt(email.indexOf('.') + 1) != '@') {
				c = true;
			}
		} catch (Exception e) {
			return false;
		}

		// Must contain exactly one ‘@’.
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@')
				count1++;
		}

		if (count1 == 1) {
			a = true;
		}

		// Must contain exactly one '.' after '@'
		for (int i = email.indexOf("@") + 2; i < email.length(); i++) {
			if (email.charAt(i) == '.')
				count2++;
		}

		if (count2 == 1) {
			b = true;
		}

		try {
			if (!email.startsWith("@") && !email.startsWith(".") && !email.endsWith("@") && !email.endsWith(".")
					&& a == true && b == true && c == true) {
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

	}

	public boolean isAlphaNumeric(String str) {
		int alpha = 0;
		int num = 0;

		for (int z = 0; z < str.length(); z++) {
			if (Character.isLetter(str.charAt(z))) {
				alpha++;
			} else if (Character.isDigit(str.charAt(z))) {
				num++;
			}

			if (alpha > 0 && num > 0) {
				return true;
			}
		}
		return false;
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

	private void setConfig() {
		setLayout(new BorderLayout());
		setBackground(new Color(51, 204, 255));
		setSize(550, 625);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Register Form");
	}

}
