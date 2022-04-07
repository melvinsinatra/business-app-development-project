package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import objects.Users;
import util.MySQLConnect;

public class EditProfileForm extends JInternalFrame {

	GridBagConstraints gbc = new GridBagConstraints();
	JLabel lblUpdateProfile, lblUsername, lblEmail, lblPhone, lblAddress, lblGender, lblChangePass, lblOldPass,
			lblNewPass, lblConfirmPass;
	JPanel pnlGenderButtons, pnlLblUpdateProfile, pnlLblChangePass, pnlBtnUpdateProfile, pnlBtnChangePass;
	JTextField txtUserName, txtEmail, txtPhone;
	JPasswordField txtNewPass, txtConfirmPass, txtOldPass;
	JTextArea txtAddress;
	JButton btnUpdateProfile, btnChangePass;
	JRadioButton rbMale, rbFemale;
	ButtonGroup bg = new ButtonGroup();
	MySQLConnect con = new MySQLConnect();
	Statement stmt;

	public EditProfileForm() {
		setConfig();
		setForm();

		setVisible(true);
	}

	private void setForm() {
		gbc.insets = new Insets(10, 100, 10, 10);

		// ===Update Profile===
		lblUpdateProfile = new JLabel("Update Profile", SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(lblUpdateProfile, gbc);

		// Username Label
		lblUsername = new JLabel("Username", SwingConstants.LEFT);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(lblUsername, gbc);

		// Username TextField
		txtUserName = new JTextField();
		txtUserName.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(txtUserName, gbc);

		// User Email Label
		lblEmail = new JLabel("User Email", SwingConstants.LEFT);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lblEmail, gbc);

		// User Email TextField
		txtEmail = new JTextField();
		txtEmail.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(txtEmail, gbc);

		// User Phone Label
		lblPhone = new JLabel("User Phone", SwingConstants.LEFT);
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lblPhone, gbc);

		// User Phone TextField
		txtPhone = new JTextField();
		txtPhone.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(txtPhone, gbc);

		// User Address Label
		lblAddress = new JLabel("User Address", SwingConstants.LEFT);
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblAddress, gbc);

		// User Address TextArea
		txtAddress = new JTextArea();
		txtAddress.setPreferredSize(new Dimension(250, 100));
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(txtAddress, gbc);

		// User Gender Label
		lblGender = new JLabel("Gender", SwingConstants.LEFT);
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(lblGender, gbc);

		// Gender RadioButton
		pnlGenderButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlGenderButtons.setBackground(new Color(51, 204, 255));
		rbMale = new JRadioButton("Male");
		rbMale.setBackground(new Color(51, 204, 255));
		rbFemale = new JRadioButton("Female");
		rbFemale.setBackground(new Color(51, 204, 255));
		bg.add(rbMale);
		bg.add(rbFemale);
		pnlGenderButtons.add(rbMale);
		pnlGenderButtons.add(rbFemale);
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(pnlGenderButtons, gbc);

		// Update Profile Button
		pnlBtnUpdateProfile = new JPanel();
		pnlBtnUpdateProfile.setBackground(new Color(51, 204, 255));
		btnUpdateProfile = new JButton("Update Profile");
		btnUpdateProfile.setPreferredSize(new Dimension(150, 75));

		btnUpdateProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Validasi Edit Profile
				if (userNameValid() == true && emailValid() == true && phoneNumberValid() == true
						&& addressValid() == true && genderValid() == true) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to update profile?",
							"Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						String query = String.format(
								"UPDATE users SET UserName = '%s', UserEmail = '%s', UserPhone = '%s', UserAddress = '%s', UserGender = '%s' WHERE UserID = '%s'",
								txtUserName.getText(), txtEmail.getText(), txtPhone.getText(), txtAddress.getTabSize(),
								genderChoice(), Users.getUserId());
						con.executeUpdate(query);
					}
				}
				if (userNameValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid name!");
				}
				if (emailValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid email!");
				}
				if (phoneNumberValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid phone number!");
				}
				if (addressValid() == false) {
					JOptionPane.showMessageDialog(null, "Please fill with valid address!");
				}
				if (genderValid() == false) {
					JOptionPane.showMessageDialog(null, "Please select a gender");
				}

			}
		});

		pnlBtnUpdateProfile.add(btnUpdateProfile);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBtnUpdateProfile, gbc);

		// ===Change Password===

		lblChangePass = new JLabel("Change Password", SwingConstants.CENTER);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(lblChangePass, gbc);

		// Old Password Label
		lblOldPass = new JLabel("Old Password", SwingConstants.LEFT);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(lblOldPass, gbc);

		// Old Password PasswordField
		txtOldPass = new JPasswordField();
		txtOldPass.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 1;
		add(txtOldPass, gbc);

		// New Password Label
		lblNewPass = new JLabel("New Password", SwingConstants.LEFT);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(lblNewPass, gbc);

		// New Password PasswordField
		txtNewPass = new JPasswordField();
		txtNewPass.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 2;
		add(txtNewPass, gbc);

		// ConfirmPassword Label
		lblConfirmPass = new JLabel("Confirm New Password", SwingConstants.LEFT);
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(lblConfirmPass, gbc);

		// Confirm Password PasswordField
		txtConfirmPass = new JPasswordField();
		txtConfirmPass.setPreferredSize(new Dimension(250, 50));
		gbc.gridx = 3;
		gbc.gridy = 3;
		add(txtConfirmPass, gbc);

		// Change Password Button
		btnChangePass = new JButton("Change Password");

		btnChangePass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String oldPass = new String(txtOldPass.getPassword());
				String currentPass = Users.getUserPassword();
				String newPass = new String(txtNewPass.getPassword());
				String confirmPass = new String(txtConfirmPass.getPassword());

				if (oldPass.equals(currentPass) && (newPass.length() >= 5 || newPass.length() <= 30)
						&& isAlphaNumeric(newPass) == true && confirmPass.equals(newPass)) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to change password?",
							"Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						String query = String.format("UPDATE users SET UserPassword = '%s' WHERE UserID = '%s'",
								newPass, Users.getUserId());
						con.executeUpdate(query);
					}
				} else if (!oldPass.equals(currentPass)) {
					JOptionPane.showMessageDialog(null, "Your Password did not match our records");
				} else {
					JOptionPane.showMessageDialog(null, "Your new Passwords did not match");
				}

			}
		});

		pnlBtnChangePass = new JPanel();
		pnlBtnChangePass.setBackground(new Color(51, 204, 255));
		btnChangePass.setPreferredSize(new Dimension(150, 75));
		pnlBtnChangePass.add(btnChangePass);
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBtnChangePass, gbc);

	}

	public boolean newPasswordValid() {

		String password = new String(txtNewPass.getPassword());

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

		// Character '@' must not be next to '.'\
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
		setSize(1440, 900);
		setLayout(new GridBagLayout());
		setBackground(new Color(51, 204, 255));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setClosable(true);
		setMaximizable(true);
	}

}
