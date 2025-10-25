package com.employee.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.Container;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Add Employee - Fixed to use auto-increment ID
 */
public class ADMIN_add_emp {
    
    // Theme colors
    private static final Color BLACK = Color.decode("#1a1a1a");
    private static final Color WHITE = Color.decode("#ffffff");
    private static final Color MUSTARD = Color.decode("#ffe380");
    
    private DashboardFrame parentFrame;
    private DatabaseManager dbManager;
    private String selectedPhotoPath = null;
    
    public ADMIN_add_emp(DashboardFrame parent) {
        this.parentFrame = parent;
        this.dbManager = new DatabaseManager();
    }
    
    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.add(createHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(WHITE);

        JPanel left = createLeftColumn();
        JPanel right = createRightColumn();

        content.add(left);
        content.add(right);

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrap.setBackground(WHITE);
        btnWrap.add(createSaveButton(left, right));

        panel.add(content, BorderLayout.CENTER);
        panel.add(btnWrap, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BLACK);
        header.setPreferredSize(new Dimension(100, 64));
        header.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel title = new JLabel("Add Employee");
        title.setForeground(MUSTARD);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title, BorderLayout.WEST);

        JButton close = new JButton("×");
        close.setFont(new Font("Arial", Font.BOLD, 22));
        close.setForeground(MUSTARD);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.setFocusPainted(false);
        close.addActionListener(e -> parentFrame.switchTo("Dashboard"));
        header.add(close, BorderLayout.EAST);

        return header;
    }
    
    private JPanel createLeftColumn() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(WHITE);
        
        JPanel personalInfo = createPersonalInfoPanel();
        personalInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel addressInfo = createAddressInfoPanel();
        addressInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel companyDetails = createCompanyDetailsPanel();
        companyDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        left.add(personalInfo);
        left.add(Box.createVerticalStrut(15));
        left.add(addressInfo);
        left.add(Box.createVerticalStrut(15));
        left.add(companyDetails);
        
        return left;
    }
    
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MUSTARD, 2), 
            "Personal Information"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 13));
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField firstNameFld = createLettersOnlyField();
        JTextField middleNameFld = createLettersOnlyField();
        JTextField lastNameFld = createLettersOnlyField();
        
        JDateChooser dobFld = new JDateChooser();
        dobFld.setDateFormatString("yyyy-MM-dd");
        dobFld.setFont(new Font("Arial", Font.PLAIN, 12));
        ((JTextField)dobFld.getDateEditor().getUiComponent()).setText("YYYY-MM-DD");
        
        JComboBox<String> genderFld = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        
        JTextField nationalityFld = createLettersOnlyField();
        JTextField contactFld = createNumberOnlyField(11);
        JTextField emergency1Fld = createNumberOnlyField(11);
        JTextField emergency2Fld = createNumberOnlyField(11);
        
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        photoPanel.setBackground(WHITE);
        JButton choosePhotoBtn = new JButton("Choose Photo");
        JLabel photoLabel = new JLabel("No file chosen");
        photoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        photoLabel.setForeground(Color.GRAY);
        photoPanel.add(choosePhotoBtn);
        photoPanel.add(Box.createHorizontalStrut(10));
        photoPanel.add(photoLabel);
        
        choosePhotoBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(parentFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedPhotoPath = selectedFile.getAbsolutePath();
                photoLabel.setText(selectedFile.getName());
            }
        });

        panel.putClientProperty("firstNameFld", firstNameFld);
        panel.putClientProperty("middleNameFld", middleNameFld);
        panel.putClientProperty("lastNameFld", lastNameFld);
        panel.putClientProperty("dobFld", dobFld);
        panel.putClientProperty("genderFld", genderFld);
        panel.putClientProperty("nationalityFld", nationalityFld);
        panel.putClientProperty("contactFld", contactFld);
        panel.putClientProperty("emergency1Fld", emergency1Fld);
        panel.putClientProperty("emergency2Fld", emergency2Fld);

        int y = 0;
        addLabelAndComponent(panel, gbc, y++, "First Name: *", firstNameFld);
        addLabelAndComponent(panel, gbc, y++, "Middle Name:", middleNameFld);
        addLabelAndComponent(panel, gbc, y++, "Last Name: *", lastNameFld);
        addLabelAndComponent(panel, gbc, y++, "Date of Birth: *", dobFld);
        addLabelAndComponent(panel, gbc, y++, "Gender: *", genderFld);
        addLabelAndComponent(panel, gbc, y++, "Nationality: *", nationalityFld);
        addLabelAndComponent(panel, gbc, y++, "Contact Number: *", contactFld);
        addLabelAndComponent(panel, gbc, y++, "Emergency Contact 1: *", emergency1Fld);
        addLabelAndComponent(panel, gbc, y++, "Emergency Contact 2: *", emergency2Fld);
        addLabelAndComponent(panel, gbc, y++, "Photo: *", photoPanel);

        return panel;
    }
    
    private JPanel createAddressInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MUSTARD, 2), 
            "Address Information"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 13));
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField houseNoFld = new JTextField();
        JTextField barangayFld = new JTextField();
        JTextField cityFld = new JTextField();
        JTextField provinceFld = new JTextField();
        JTextField zipCodeFld = createNumberOnlyField(4);

        panel.putClientProperty("houseNoFld", houseNoFld);
        panel.putClientProperty("barangayFld", barangayFld);
        panel.putClientProperty("cityFld", cityFld);
        panel.putClientProperty("provinceFld", provinceFld);
        panel.putClientProperty("zipCodeFld", zipCodeFld);

        int y = 0;
        addLabelAndComponent(panel, gbc, y++, "House No./Street: *", houseNoFld);
        addLabelAndComponent(panel, gbc, y++, "Barangay: *", barangayFld);
        addLabelAndComponent(panel, gbc, y++, "City: *", cityFld);
        addLabelAndComponent(panel, gbc, y++, "Province: *", provinceFld);
        addLabelAndComponent(panel, gbc, y++, "Zip Code: *", zipCodeFld);

        return panel;
    }
    
    private JPanel createCompanyDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MUSTARD, 2), 
            "Company Details"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 13));
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel empIdLabel = new JLabel("(Auto-generated)");
        empIdLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        empIdLabel.setForeground(Color.GRAY);
        
        JTextField positionFld = new JTextField();
        
        JDateChooser joiningFld = new JDateChooser();
        joiningFld.setDateFormatString("yyyy-MM-dd");
        joiningFld.setFont(new Font("Arial", Font.PLAIN, 12));
        ((JTextField)joiningFld.getDateEditor().getUiComponent()).setText("YYYY-MM-DD");
        
        JComboBox<String> statusFld = new JComboBox<>(new String[]{"Active", "Deactivated"});
        statusFld.setSelectedIndex(0);

        panel.putClientProperty("positionFld", positionFld);
        panel.putClientProperty("joiningFld", joiningFld);
        panel.putClientProperty("statusFld", statusFld);

        int r = 0;
        addLabelAndComponent(panel, gbc, r++, "Employee ID:", empIdLabel);
        addLabelAndComponent(panel, gbc, r++, "Position: *", positionFld);
        addLabelAndComponent(panel, gbc, r++, "Date of Joining: *", joiningFld);
        addLabelAndComponent(panel, gbc, r++, "Status: *", statusFld);

        return panel;
    }
    
    private JPanel createFinancialDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MUSTARD, 2), 
            "Financial Details"
        );
        border.setTitleFont(new Font("Arial", Font.BOLD, 13));
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField salaryFld = createDecimalOnlyField();
        panel.putClientProperty("salaryFld", salaryFld);

        addLabelAndComponent(panel, gbc, 0, "Basic Salary: *", salaryFld);

        return panel;
    }
    
    private JPanel createRightColumn() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(WHITE);
        
        JPanel accountLogin = new JPanel(new GridBagLayout());
        accountLogin.setBackground(WHITE);
        accountLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        TitledBorder loginBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(MUSTARD, 2), 
            "Account Login"
        );
        loginBorder.setTitleFont(new Font("Arial", Font.BOLD, 13));
        accountLogin.setBorder(loginBorder);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField emailFld = new JTextField();
        JTextField usernameFld = new JTextField();
        JPasswordField passFld = new JPasswordField();
        JPasswordField confirmFld = new JPasswordField();

        accountLogin.putClientProperty("emailFld", emailFld);
        accountLogin.putClientProperty("usernameFld", usernameFld);
        accountLogin.putClientProperty("passFld", passFld);
        accountLogin.putClientProperty("confirmFld", confirmFld);

        int r = 0;
        addLabelAndComponent(accountLogin, gbc, r++, "Email: *", emailFld);
        addLabelAndComponent(accountLogin, gbc, r++, "Username:", usernameFld);
        addLabelAndComponent(accountLogin, gbc, r++, "Password: *", passFld);
        addLabelAndComponent(accountLogin, gbc, r++, "Confirm Password: *", confirmFld);
        
        JPanel financialDetails = createFinancialDetailsPanel();
        financialDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        right.add(accountLogin);
        right.add(Box.createVerticalStrut(15));
        right.add(financialDetails);
        right.add(Box.createVerticalGlue());

        return right;
    }
    
    private JButton createSaveButton(JPanel leftPanel, JPanel rightPanel) {
        JButton save = new JButton("Add Employee");
        stylePrimaryButton(save);
        
        save.addActionListener(e -> {
            JTextField firstNameFld = (JTextField) getFieldFromColumn(leftPanel, "firstNameFld");
            JTextField middleNameFld = (JTextField) getFieldFromColumn(leftPanel, "middleNameFld");
            JTextField lastNameFld = (JTextField) getFieldFromColumn(leftPanel, "lastNameFld");
            JDateChooser dobFld = (JDateChooser) getFieldFromColumn(leftPanel, "dobFld");
            JComboBox<String> genderFld = (JComboBox<String>) getFieldFromColumn(leftPanel, "genderFld");
            JTextField nationalityFld = (JTextField) getFieldFromColumn(leftPanel, "nationalityFld");
            JTextField contactFld = (JTextField) getFieldFromColumn(leftPanel, "contactFld");
            JTextField emergency1Fld = (JTextField) getFieldFromColumn(leftPanel, "emergency1Fld");
            JTextField emergency2Fld = (JTextField) getFieldFromColumn(leftPanel, "emergency2Fld");
            
            JTextField houseNoFld = (JTextField) getFieldFromColumn(leftPanel, "houseNoFld");
            JTextField barangayFld = (JTextField) getFieldFromColumn(leftPanel, "barangayFld");
            JTextField cityFld = (JTextField) getFieldFromColumn(leftPanel, "cityFld");
            JTextField provinceFld = (JTextField) getFieldFromColumn(leftPanel, "provinceFld");
            JTextField zipCodeFld = (JTextField) getFieldFromColumn(leftPanel, "zipCodeFld");
            
            JTextField positionFld = (JTextField) getFieldFromColumn(leftPanel, "positionFld");
            JDateChooser joiningFld = (JDateChooser) getFieldFromColumn(leftPanel, "joiningFld");
            JComboBox<String> statusFld = (JComboBox<String>) getFieldFromColumn(leftPanel, "statusFld");
            
            JTextField emailFld = (JTextField) getFieldFromColumn(rightPanel, "emailFld");
            JTextField usernameFld = (JTextField) getFieldFromColumn(rightPanel, "usernameFld");
            JPasswordField passFld = (JPasswordField) getFieldFromColumn(rightPanel, "passFld");
            JPasswordField confirmFld = (JPasswordField) getFieldFromColumn(rightPanel, "confirmFld");
            JTextField salaryFld = (JTextField) getFieldFromColumn(rightPanel, "salaryFld");

            String firstName = firstNameFld.getText().trim();
            String middleName = middleNameFld.getText().trim();
            String lastName = lastNameFld.getText().trim();
            String houseNo = houseNoFld.getText().trim();
            String barangay = barangayFld.getText().trim();
            String city = cityFld.getText().trim();
            String province = provinceFld.getText().trim();
            String zipCode = zipCodeFld.getText().trim();
            String nationality = nationalityFld.getText().trim();
            String contact = contactFld.getText().trim();
            String emergency1 = emergency1Fld.getText().trim();
            String emergency2 = emergency2Fld.getText().trim();
            String position = positionFld.getText().trim();
            String status = (String) statusFld.getSelectedItem();
            String salary = salaryFld.getText().trim();
            String email = emailFld.getText().trim();
            String username = usernameFld.getText().trim();
            String pass = new String(passFld.getPassword());
            String conf = new String(confirmFld.getPassword());
            
            if (firstName.isEmpty()) { showError("First Name is required."); return; }
            if (!firstName.matches("[a-zA-Z\\s]+")) { showError("First Name must contain letters only."); return; }
            if (lastName.isEmpty()) { showError("Last Name is required."); return; }
            if (!lastName.matches("[a-zA-Z\\s]+")) { showError("Last Name must contain letters only."); return; }
            if (dobFld.getDate() == null) { showError("Date of Birth is required."); return; }
            if (houseNo.isEmpty()) { showError("House No./Street is required."); return; }
            if (barangay.isEmpty()) { showError("Barangay is required."); return; }
            if (city.isEmpty()) { showError("City is required."); return; }
            if (province.isEmpty()) { showError("Province is required."); return; }
            if (zipCode.isEmpty() || zipCode.length() != 4) { showError("Zip Code must be 4 digits."); return; }
            if (nationality.isEmpty()) { showError("Nationality is required."); return; }
            if (contact.isEmpty() || contact.length() != 11) { showError("Contact Number must be exactly 11 digits."); return; }
            if (emergency1.isEmpty() || emergency1.length() != 11) { showError("Emergency Contact 1 must be exactly 11 digits."); return; }
            if (emergency2.isEmpty() || emergency2.length() != 11) { showError("Emergency Contact 2 must be exactly 11 digits."); return; }
            if (selectedPhotoPath == null || selectedPhotoPath.isEmpty()) { showError("Photo is required."); return; }
            if (position.isEmpty()) { showError("Position is required."); return; }
            if (joiningFld.getDate() == null) { showError("Date of Joining is required."); return; }
            if (salary.isEmpty()) { showError("Basic Salary is required."); return; }
            if (email.isEmpty() || !email.contains("@")) { showError("Valid email is required."); return; }
            if (dbManager.emailExists(email)) { showError("This email is already registered."); return; }
            if (pass.isEmpty() || !pass.equals(conf)) { showError("Passwords do not match."); return; }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dobStr = sdf.format(dobFld.getDate());
            String joiningStr = sdf.format(joiningFld.getDate());

            Connection conn = null;
            try {
                conn = dbManager.getConnection();
                if (conn == null) {
                    showError("Database connection failed!");
                    return;
                }
                conn.setAutoCommit(false);
                
                
                
                // Let database auto-generate the ID
                String sql = """
                    INSERT INTO users 
                    (email, username, first_name, last_name, middle_name, password, role, 
                     birth_date, gender, nationality, house_no, barangay, city, province, zip_code, 
                     contact_no, emergency_no1, emergency_no2, position, joining_date, status, 
                     basic_salary, photo, created_at, updated_at)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
                """;
                
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, username.isEmpty() ? null : username);
                ps.setString(3, firstName);
                ps.setString(4, lastName);
                ps.setString(5, middleName.isEmpty() ? null : middleName);
                ps.setString(6, pass);
                ps.setString(7, "user");
                ps.setString(8, dobStr);
                ps.setString(9, (String) genderFld.getSelectedItem());
                ps.setString(10, nationality);
                ps.setString(11, houseNo);
                ps.setString(12, barangay);
                ps.setString(13, city);
                ps.setString(14, province);
                ps.setString(15, zipCode);
                ps.setString(16, contact);
                ps.setString(17, emergency1);
                ps.setString(18, emergency2);
                ps.setString(19, position);
                ps.setString(20, joiningStr);
                ps.setString(21, status);
                ps.setString(22, salary);
                ps.setString(23, selectedPhotoPath);
                ps.executeUpdate();
                
                // Get the auto-generated ID
                ResultSet generatedKeys = ps.getGeneratedKeys();
                String employeeId = "";
                if (generatedKeys.next()) {
                    employeeId = String.valueOf(generatedKeys.getInt(1));
                }
                generatedKeys.close();
                ps.close();
                
                conn.commit();
                
                JOptionPane.showMessageDialog(parentFrame, 
                    "Employee added successfully!\nEmployee ID: " + employeeId + "\nEmail: " + email);
                
                parentFrame.switchTo("Manage Employee");
                
            } catch (SQLException ex) {
                if (conn != null) {
                    try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
                }
                showError("Database Error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (SQLException ex) { ex.printStackTrace(); }
                }
            }
        });
        
        return save;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private Component getFieldFromColumn(Container container, String propertyName) {
        return searchPanelForProperty(container, propertyName);
    }
    
    private Component searchPanelForProperty(Container container, String propertyName) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Object property = panel.getClientProperty(propertyName);
                if (property != null) {
                    return (Component) property;
                }
                Component found = searchPanelForProperty(panel, propertyName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
    
    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component comp) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.4; gbc.weighty = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lbl, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 0.6;
        panel.add(comp, gbc);
    }
    
    private JTextField createLettersOnlyField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-Z ]+")) { super.insertString(fb, offset, string, attr); }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-Z ]+")) { super.replace(fb, offset, length, text, attrs); }
            }
        });
        return field;
    }
    
    private JTextField createNumberOnlyField(int maxLength) {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+") && (fb.getDocument().getLength() + string.length()) <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+") && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        return field;
    }
    
    private JTextField createDecimalOnlyField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9.]+") && isValidDecimal(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = current.substring(0, offset) + text + current.substring(offset + length);
                if (text.matches("[0-9.]*") && isValidDecimal(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            private boolean isValidDecimal(String text) {
                if (text.isEmpty()) return true;
                long dotCount = text.chars().filter(ch -> ch == '.').count();
                return dotCount <= 1;
            }
        });
        return field;
    }
    
    private void stylePrimaryButton(JButton b) {
        b.setBackground(MUSTARD);
        b.setForeground(BLACK);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(150, 35));
    }
}