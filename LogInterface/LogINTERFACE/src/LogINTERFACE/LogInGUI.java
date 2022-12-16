package LogINTERFACE;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.*;
import java.awt.Color;


public class LogInGUI extends JFrame {
  final private Font mainFont = new Font("Segeo print", Font.BOLD, 18);
  //final private Font secondaryFont = new Font("")//

  JTextField tfEmail;
  JTextField lbLogInGUI;
  JTextField pfPassword;
  JPanel jpBackground;


  public void initialize() {
    /********************FORM PANEL HERE! *****************/
    JLabel lbLogInGUI = new JLabel("Login", SwingConstants.CENTER);
    lbLogInGUI.setFont(mainFont);

    JLabel lbEmail = new JLabel("Email");
    lbEmail.setFont(mainFont);

    tfEmail = new JTextField();
    tfEmail.setFont(mainFont);

    JLabel lbPassword = new JLabel("Password");
    lbPassword.setFont(mainFont);

    pfPassword = new JPasswordField();
    pfPassword.setFont(mainFont);

    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridLayout(0, 1, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
    formPanel.add(lbLogInGUI);
    formPanel.add(lbEmail);
    formPanel.add(tfEmail);
    formPanel.add(lbPassword);
    formPanel.add(pfPassword);

  /****************BUTTON PANEL ***************/
    JButton btnLogin = new JButton("Login");
    btnLogin.setFont(mainFont);
    btnLogin.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String email = tfEmail.getText();
        String password = String.valueOf(((JPasswordField) pfPassword).getPassword());
        
        User user = getAuthenticatedUsers(email, password);

        if (user !=null) {
          MainFrame mainFrame = new MainFrame();
          mainFrame.initialize(user);
          dispose();
          
        }
        else{
            JOptionPane.showMessageDialog(LogInGUI.this,
              "Email or Password is Incorrect",
              "Try Again",
              JOptionPane.ERROR
            );
        }
      }
    });
      
      JButton btnCreate = new JButton("Create Account");
      btnCreate.setFont(mainFont);
      btnCreate.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
          // TODO Auto-generated method stub
          dispose();
        }
      });

      JPanel buttonsPanel = new JPanel();
      buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
      buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
      buttonsPanel.add(btnLogin);
      buttonsPanel.add(btnCreate);


  /******************PANEL********************/
      
    add(formPanel, BorderLayout.NORTH);
    add(buttonsPanel, BorderLayout.SOUTH);

    setTitle("Jusay Fishport");
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(800, 400);
    setMinimumSize(new Dimension(350, 350));
    setBackground(Color.cyan);
    setLocationRelativeTo(null);
    setVisible(true);

  }

  protected User getAuthenticatedUsers(String email, String password) {
    User user = null;

    final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    try{
      Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
      // Connected to database sucessfully....

      String sql = "SELECT * FROM users WHERE email=! AND password=!";
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        user = new User();
          user.name = resultSet.getString("name");
          user.email = resultSet.getString("email");
          user.phone = resultSet.getString("phone");
          user.address = resultSet.getString("address");
          user.password = resultSet.getString("password");
      }
      
        preparedStatement.close();
        conn.close();
    } catch(Exception e){
        System.out.println("Database connection failed teribly!");
    }

    return user;

  } 
    public static void main(String[] args){
      LogInGUI logInGUI = new LogInGUI();
      logInGUI.initialize();
    }
}