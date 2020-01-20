import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Add_records {

	private JFrame frame;
	private JTextField textName;
	private JTextField txtPhone;
	private JTextField txtDOB;
	private JTextField textEmail;
	private JTextField txtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_records window = new Add_records();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Add_records() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 361);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textName = new JTextField();
		textName.setBounds(130, 28, 231, 19);
		frame.getContentPane().add(textName);
		textName.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(130, 59, 231, 19);
		frame.getContentPane().add(txtPhone);
		txtPhone.setColumns(10);
		
		txtDOB = new JTextField();
		txtDOB.setBounds(130, 93, 231, 19);
		frame.getContentPane().add(txtDOB);
		txtDOB.setColumns(10);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(140, 119, 61, 21);
		frame.getContentPane().add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(229, 119, 72, 21);
		frame.getContentPane().add(rdbtnFemale);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(27, 31, 72, 13);
		frame.getContentPane().add(lblName);
		
		JLabel lblPhone = new JLabel("Phone Number");
		lblPhone.setBounds(27, 62, 93, 13);
		frame.getContentPane().add(lblPhone);
		
		JLabel lblDOB = new JLabel("DOB");
		lblDOB.setBounds(27, 96, 46, 13);
		frame.getContentPane().add(lblDOB);
		
		JButton btnAdd = new JButton("ADD DATA");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String name=textName.getText().toString().trim();
				String phone=txtPhone.getText().toString().trim();
				String dob=txtDOB.getText().toString().trim();
				String email=textEmail.getText().toString().trim();
				String id=txtID.getText().toString().trim();
				int ID=Integer.parseInt(id);
				String gender=null;
				if(rdbtnMale.isSelected())
				{
					gender="M";
				}
				if(rdbtnFemale.isSelected())
				{
					gender="F";
				}
				try 
				{
					if(idIsPresent(ID))
					{
						JOptionPane.showMessageDialog(null, "ID alredy exists");
					}
					else
					{
						addData(name,phone,dob,email,gender,ID);
					}
				} catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private boolean idIsPresent(int ID) throws SQLException 
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shreyas","root","1962");  
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select * from student where id="+ID);
				if(rs.next())
				{
					return true;
				}
				else
				{
					return false;
				}
				
			}

			private void addData(String name, String phone, String dob, String email, String gender,int ID) throws SQLException
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shreyas","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();
				stmt.executeUpdate("insert into student values("+ID+","+"'"+name+"','"+phone+"','"+dob+"','"+gender+"','"+email+"')");
				JOptionPane.showMessageDialog(null, "Data inserted into database");
			
			}
		});
		btnAdd.setBounds(130, 231, 190, 21);
		frame.getContentPane().add(btnAdd);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(27, 119, 46, 21);
		frame.getContentPane().add(lblGender);
		
		textEmail = new JTextField();
		textEmail.setBounds(130, 157, 231, 19);
		frame.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email ID");
		lblEmail.setBounds(27, 160, 46, 13);
		frame.getContentPane().add(lblEmail);
		
		JButton btnDelete = new JButton("Delete Student");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String name=JOptionPane.showInputDialog("Enter student ID");
				int ID=Integer.parseInt(name);
				try {
					deleteStudent(ID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void deleteStudent(int ID) throws SQLException 
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shreyas","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();
				stmt.executeUpdate("delete from student where id="+ID);
				JOptionPane.showMessageDialog(null, "Student data deleted");
				
			}
		});
		btnDelete.setBounds(130, 293, 190, 21);
		frame.getContentPane().add(btnDelete);
		
		JButton btnSearch = new JButton("Search Student");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String name=JOptionPane.showInputDialog("Enter Student ID");
				int ID=Integer.parseInt(name);
				try
				{
					showData(ID);
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
			}

			private void showData(int ID) throws SQLException 
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shreyas","root","1962"); 
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select * from student where id="+ID);
				if(rs.next())
				{
					String info="Student Id "+rs.getInt(1)+"\n Student Name "+rs.getString(2)+"\n Phone Number "+rs.getString(3)+"\n DOB:"+rs.getString(4)+"\n Gender:"+rs.getString(5)+"\n Email:"+rs.getString(6);
					JOptionPane.showMessageDialog(null, info);
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No matches found");
				}
			
			}
		});
		btnSearch.setBounds(130, 262, 192, 21);
		frame.getContentPane().add(btnSearch);
		
		txtID = new JTextField();
		txtID.setBounds(130, 202, 219, 19);
		frame.getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblID = new JLabel("Student ID");
		lblID.setBounds(27, 205, 72, 13);
		frame.getContentPane().add(lblID);
	}
}
