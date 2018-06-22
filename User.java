package employeemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    
    User(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems","root","Test%3.69")) {
                String updateStatement ="select * from login_master WHERE empid = "+username;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
            }
        }
        catch(Exception e)
        { System.out.println(e);}
    }

    int view_rl(int username) throws Exception{
        int dept_id = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select department_id from employees where empid= "+username ;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                rs.next();
                dept_id = rs.getInt(1);
            }
        }
        catch(Exception e)
        { System.out.println(e);}
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from compliance where department_id = "+dept_id;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Regulation/Legislation:\nCompliance-id\t| RL-type\t| Details\t\t\t\t\t| Create Date\t| Dept-id\t|");
                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t| "+rs.getString(2)+"\t\t| "+rs.getString(3)+"\t\t| "+rs.getDate(4)+"\t| "+rs.getInt(5)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        return(1);
    }

    int add_comment(int username) throws Exception{
        int dept_id = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select department_id from employees where empid= "+username ;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                rs.next();
                dept_id = rs.getInt(1);
            }
        }
        catch(Exception e)
        { System.out.println(e);}
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from compliance where department_id = "+dept_id;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Regulation/Legislation:\nCompliance-id\t| RL-type\t| Details\t\t\t\t\t| Create Date\t| Dept-id\t|");
                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t| "+rs.getString(2)+"\t\t| "+rs.getString(3)+"\t\t| "+rs.getDate(4)+"\t| "+rs.getInt(5)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the compliance-id of the regulation you want to add a comment to: ");
        int comp_id = sc.nextInt();
        String comment;
        do{
            System.out.println("Comment: ");
            sc.nextLine();
            comment = sc.nextLine();
        }while(comment.isEmpty());
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="insert into statusreport (compliance_id,empid,comments,create_date,department_id) values ('"+comp_id+"','"+username+"','"+comment+"',CURDATE(),"+dept_id+")";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        return(1);
    }
    
    int update_comment(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from statusreport where empid = "+username;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Your Comments:\nCompliance-id\t| Report-id\t| Comments\t\t| Create Date\t| Dept-id\t|");
                System.out.println("---------------------------------------------------------------------------------");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t| "+rs.getInt(2)+"\t\t| "+rs.getString(4)+"\t\t\t| "+rs.getDate(5)+"\t| "+rs.getInt(6)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Report id of the comment you want to change: ");
        int id = sc.nextInt();
        String comment;
        do{
            System.out.println("Enter new comment: ");
            sc.nextLine();
            comment = sc.nextLine();
        }while(comment.isEmpty());
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="Update statusreport set comments='"+comment+"' where status_report_id= "+id;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        return(1);
    }
    
    int view_comment(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from statusreport where empid = "+username;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Your Comments:\nCompliance-id\t| Comments\t\t| Create Date\t| Dept-id\t|");
                System.out.println("-----------------------------------------------------------------");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t| "+rs.getString(4)+"\t\t\t| "+rs.getDate(5)+"\t| "+rs.getInt(6)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        return(1);
    }
}
