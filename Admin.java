package employeemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Admin {
    
    Admin(int username) throws Exception{
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
    
    int new_employee(int username) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Adding New Employee");
        
        System.out.println("First Name*: ");
        String f_name = sc.nextLine();
        System.out.println("Last NAme*: ");
        String l_name = sc.nextLine();
        System.out.println("DOB* (YYYY-MM-DD): ");
        String dob = sc.nextLine();
        int[] dept_id = new int[50];
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from department";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Department-id\tDepartment");
                int i =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                    dept_id[i] = rs.getInt(1);
                    i++;
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        System.out.println("Department ID*: ");
        int dep_id = sc.nextInt();
        int flag =0;
        while(flag!=1)
        {
            for(int i=0; i<dept_id.length; i++)
            {
                if(dep_id == dept_id[i])
                {
                    flag++;
                    break;
                }
            }
            if(flag==0)
            {
                System.out.println("Inavlid department id. Please choose from the list above.");
                System.out.println("Department ID*: ");
                dep_id = sc.nextInt();
            }
        }
        
        
        System.out.println("Email: ");
        String email = sc.nextLine();
        
        while(flag != 5)
        {
            if(f_name.isEmpty())
            {
                System.out.println("First Name*: ");
                f_name = sc.nextLine();
                if(!f_name.isEmpty())
                {
                    flag++;
                }
            }
            else
                flag++;
            if(l_name.isEmpty())
            {
                System.out.println("Last Name*: ");
                f_name = sc.nextLine();
                if(!l_name.isEmpty())
                {
                    flag++;
                }
            }
            else
                flag++;
            if(dob.isEmpty())
            {
                System.out.println("DOB* (YYYY-MM-DD): ");
                dob = sc.nextLine();
                if(!dob.isEmpty())
                {
                    flag++;
                }
            }
            else
                flag++;
            if(dep_id == 0)
            {
                System.out.println("Department ID*: ");
                dep_id = sc.nextInt();
                if(dep_id != 0)
                {
                    flag++;
                }
            }
            else
                flag++;
        }
        
        
        int yy = Integer.parseInt(dob.substring(0, 4));
        int mm = Integer.parseInt(dob.substring(5, 7));
        int dd = Integer.parseInt(dob.substring(8));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String cdate = ft.format(date);
        int cyy = Integer.parseInt(cdate.substring(0, 4));
        int cmm = Integer.parseInt(cdate.substring(5, 7));
        int cdd = Integer.parseInt(cdate.substring(8));
        int age = ((cyy-yy-2)*12 + (12-mm-1) + (cmm-1) + ((30-dd)+cdd)%30)/12;
        //System.out.println(age);
        if(age<24)
        {
            System.out.println("Employee is not old enough.");
            return(1);
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                //System.out.println("insert into employees (firstname,lastname,dob,email,department_id) values ('"+f_name+"','"+l_name+"','"+dob+"','"+email+"','"+dep_id+"')");
                String updateStatement ="insert into employees (firstname,lastname,dob,email,department_id) values ('"+f_name+"','"+l_name+"','"+dob+"','"+email+"','"+dep_id+"')";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
                updateStatement = "select empid from employees where email='"+email+"'";
                stmt=con.prepareStatement(updateStatement);
                ResultSet rs1=stmt.executeQuery();
                rs1.next();
                username = rs1.getInt(1);
                System.out.println("Usernme of the new employee is "+username);
                System.out.println("Enter Password for "+username+": ");
                String password = sc.next();
                System.out.println("Enter role for "+username+" (admin/user): ");
                String role = sc.next();
                while(!role.equals("admin")&& !role.equals("user"))
                {
                    System.out.println("Not a valid entry.");
                    System.out.println("Enter role for "+username+" (admin/user): ");
                    role = sc.next();
                }
                updateStatement = "insert into login_master (empid,password,role) values ("+username+",'"+password+"','"+role+"')";
                stmt=con.prepareStatement(updateStatement);
                rs = stmt.executeUpdate();
            }
        }
        catch(Exception e)
        { System.out.println(e);}
        
        return(1);
    }
    
    int view_employee(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from employees";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Emp-id\t| F-name\t| L-name\t| DOB\t\t| Email\t\t\t| Dept-id\t|");
                System.out.println("------------------------------------------------------------------------------------------------");
                while(rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t| "+rs.getString(2)+" \t| "+rs.getString(3)+" \t| "+rs.getString(4)+"\t| "+rs.getString(5)+" \t| "+rs.getInt(6)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);} 
        return(1);
    }
    
    int edit_employee(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from employees";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Emp-id\t| F-name\t| L-name\t| DOB\t\t| Email\t\t\t| Dept-id\t|");
                System.out.println("------------------------------------------------------------------------------------------------");
                while(rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t| "+rs.getString(2)+" \t| "+rs.getString(3)+" \t| "+rs.getString(4)+"\t| "+rs.getString(5)+" \t| "+rs.getInt(6)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Emp-id of the employee who's details you want to edit: ");
        int emp = sc.nextInt();
        String f_name = "";
        String l_name = "";
        String dob = "";
        String email = "";
        int dept_id = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from employees where empid= "+emp;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                while(rs.next()){
                    f_name = rs.getString(2);
                    l_name = rs.getString(3);
                    dob = rs.getString(4);
                    email = rs.getString(5);
                    dept_id = rs.getInt(6);
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        int flag=0;
        while(flag!=1)
        {
            System.out.println("What field do you want to edit?\n1.F-name  2.L-name  3.DOB  4.Email  5.Dept-id 6.Done");
            int i = sc.nextInt();
            switch(i)
            {
                case 1:
                    do{
                        System.out.print("Enter new First Name: ");
                        sc.nextLine();
                        f_name = sc.nextLine();
                    }while(f_name.isEmpty());
                    break;
                case 2:
                    do{
                        System.out.println("Enter new Last Name: ");
                        sc.nextLine();
                        l_name = sc.nextLine();
                    }while(l_name.isEmpty());
                    break;
                case 3:
                    do{
                        System.out.println("Enter new DOB: ");
                        sc.nextLine();
                        dob = sc.nextLine();
                    }while(dob.isEmpty());
                    break;
                case 4:
                    do{
                        System.out.println("Enter new Email: ");
                        sc.nextLine();
                        email = sc.nextLine();
                    }while(email.isEmpty());
                    break;
                case 5:
                    int[] dep_id = new int[50];
                    try{
                        Class.forName("com.mysql.jdbc.Driver");  
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                            String updateStatement ="select * from department";
                            PreparedStatement stmt=con.prepareStatement(updateStatement);
                            ResultSet rs=stmt.executeQuery();
                            System.out.println("Department-id\tDepartment");
                            int j =0;
                            while (rs.next())
                            {
                                System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                                dep_id[j] = rs.getInt(1);
                                j++;
                            }
                        }
                    }
                    catch(ClassNotFoundException | SQLException e)
                    { System.out.println(e);}
                    System.out.println("Enter new Department ID*: ");
                    dept_id = sc.nextInt();
                    int flag1 =0;
                    while(flag1!=1)
                    {
                        for(int k=0; k<dep_id.length; k++)
                        {
                            if(dept_id == dep_id[k])
                            {
                                flag1++;
                                break;
                            }
                        }
                        if(flag1==0)
                        {
                            System.out.println("Inavlid department id. Please choose from the list above.");
                            System.out.println("Department ID*: ");
                            dept_id = sc.nextInt();
                        }
                    }
                    break;
                case 6:
                    flag++;
                    break;
                default:
                    break; 
            }
            
        }
        
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                //System.out.println("update employees set firstname='"+f_name+"', lastname='"+l_name+"', dob='"+dob+"', email= '"+email+"', department_id= "+dept_id+" where empid='"+emp+"'");
                String updateStatement ="update employees set firstname='"+f_name+"', lastname='"+l_name+"', dob='"+dob+"', email= '"+email+"', department_id= "+dept_id+" where empid='"+emp+"'";
                
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);} 
        
        return(1);
    }
    
    int delete_employee(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from employees";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Emp-id\t| F-name\t| L-name\t| DOB\t\t| Email\t\t\t| Dept-id\t|");
                System.out.println("------------------------------------------------------------------------------------------------");
                while(rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t| "+rs.getString(2)+" \t| "+rs.getString(3)+" \t| "+rs.getString(4)+"\t| "+rs.getString(5)+" \t| "+rs.getInt(6)+"\t\t|");
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Emp-id of the employee who's details you want to delete: ");
        int emp = sc.nextInt();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="delete from login_master where empid="+emp;
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
                updateStatement ="delete from employees where empid="+emp;
                stmt=con.prepareStatement(updateStatement);
                rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);} 
        
        return(1);
    }
    
    int add_department(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from department";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Current Departments.\nDepartment-id\tDepartment");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
            { System.out.println(e);}
        Scanner sc = new Scanner(System.in);
        String name = "";
        while(name.isEmpty()){
            System.out.println("Enter new Department name: ");
            name = sc.nextLine();
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="insert into department (department_name) values('"+name+"')";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from department";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Departments after adding "+name+".\nDepartment-id\tDepartment");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
            { System.out.println(e);}
        
        return(1);
    }
    
    int view_department(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from department";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Department-id\tDepartment");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
            { System.out.println(e);}
        
        return(1);
    }
    
    int create_rl(int username) throws Exception{
        Scanner sc = new Scanner(System.in);
        String type;
        do{
            System.out.println("RL Type(ABC/XYZ): ");
            type = sc.nextLine();
        }while(type.isEmpty() || (!type.equals("ABC") && !type.equals("XYZ")));
        
        String det;
        do{
            System.out.println("Details: ");
            det = sc.nextLine();
        }while(type.isEmpty());
        
        int[] dep_id = new int[50];
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from department";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                ResultSet rs=stmt.executeQuery();
                System.out.println("Department-id\tDepartment");
                int j =0;
                while (rs.next())
                {
                    System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2));
                    dep_id[j] = rs.getInt(1);
                    j++;
                }
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        System.out.println("Enter new Department ID*: ");
        int dept_id = sc.nextInt();
        int flag1 =0;
        while(flag1!=1)
        {
            for(int k=0; k<dep_id.length; k++)
            {
                if(dept_id == dep_id[k])
                {
                    flag1++;
                    break;
                }
            }
            if(flag1==0)
            {
                System.out.println("Inavlid department id. Please choose from the list above.");
                System.out.println("Department ID*: ");
                dept_id = sc.nextInt();
            }
        }
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="insert into compliance (rl_type,details,create_date,department_id) values ('"+type+"','"+det+"',CURDATE(),"+dept_id+")";
                PreparedStatement stmt=con.prepareStatement(updateStatement);
                int rs=stmt.executeUpdate();
            }
        }
        catch(ClassNotFoundException | SQLException e)
        { System.out.println(e);}
        
        return(1);
    }
    
    int view_rl(int username) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS","root","")) {
                String updateStatement ="select * from compliance";
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
}