package employeemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
public class EmployeeManagementSystem {

    public static void main(String[] args) throws Exception{
        
        Scanner sc=new Scanner(System.in);
        int a = 0;

        while(a ==0)
        {
            System.out.println("Login");
            System.out.println("Username: ");
            int username = sc.nextInt();
            System.out.println("Password: ");
            String password = sc.next();
            try{
                Class.forName("com.mysql.jdbc.Driver");  
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                    String updateStatement ="select * from login_master WHERE empid = "+username;
                    PreparedStatement stmt=con.prepareStatement(updateStatement);
                    ResultSet rs=stmt.executeQuery();
                    int login = 0;
                    
                    while(rs.next())
                    {
                        if(username == rs.getInt(1))
                        {
                            login++;
                        }

                        if(password.equals(rs.getString(2)))
                        {
                            login++;
                        }
                        
                        if(login == 2)
                        {
                            if(rs.getString(3).equals("admin"))
                            {
                                Admin ad = new Admin(username);
                                int flag = 1;
                                while(flag == 1)
                                {
                                    System.out.println("Admin Operations:\n1. "
                                            + "Add Emplyee \n2. View Employee \n"
                                            + "3. Edit Employee \n4. Delete Employee "
                                            + "\n5. Add Department \n6. View Department "
                                            + "\n7. Create RL \n8. View RL \n9. Logout");
                                    //System.out.println("Admin");
                                    int k = sc.nextInt();

                                    switch(k)
                                    {
                                        case 1:
                                            flag = ad.new_employee(username);
                                            break;
                                        case 2:
                                            flag = ad.view_employee(username);
                                            break;
                                        case 3:
                                            flag = ad.edit_employee(username);
                                            break;
                                        case 4:
                                            flag = ad.delete_employee(username);
                                            break;
                                        case 5:
                                            flag = ad.add_department(username);
                                            break;
                                        case 6:
                                            flag = ad.view_department(username);
                                            break;
                                        case 7:
                                            flag = ad.create_rl(username);
                                            break;
                                        case 8:
                                            flag = ad.view_rl(username);
                                            break;
                                        case 9:
                                            ad = null;
                                            flag = 0;
                                            break;
                                        default:
                                            System.out.println("Invalid selection.");
                                            break;
                                    }
                                    a=1;
                                }
                            }
                            else if(rs.getString(3).equals("user"))
                            {
                                User u = new User(username);
                                int flag = 1;
                                while(flag==1)
                                {
                                    System.out.println("User Operations:\n1. View RL \n2. Add Comments \n3. Update Comments\n4. View Comments \n5. Logout");
                                    int k = sc.nextInt();
                                    switch(k)
                                    {
                                        case 1:
                                            flag = u.view_rl(username);
                                            break;
                                        case 2:
                                            flag = u.add_comment(username);
                                            break;
                                        case 3:
                                            flag = u.update_comment(username);
                                            break;
                                        case 4:
                                            flag = u.view_comment(username);
                                            break;
                                        case 5:
                                            u = null;
                                            flag = 0;
                                            break;
                                        default:
                                            System.out.println("Invalid selection.");
                                    }
                                    a=1;
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Wrong username or password");
                        }
                    }
                }
            }
            catch(Exception e)
            { System.out.println(e);}                         
        }
    }
}