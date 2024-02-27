/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import com.mycompany.hrmanagement.model.Department;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Amin
 */
public class DepartmentDAO implements IDAO<Department> {
    
    private IDatabaseConnector databaseConnector;
    private int rowsAffected;
    
    public DepartmentDAO(IDatabaseConnector databaseConnector)
    {
        this.databaseConnector = databaseConnector;
    }
    
    public boolean create(Department department) {
        String sql = "INSERT INTO Tbl_Departments (Department_Name, Office_ID) VALUES ('" + department.getDepartmentName() + "', " + department.getOfficeID() + ")";
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Department added successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to add Department.");
                    return false;
                }
    }
    
    public boolean update(Department department) throws SQLException {
        String sql = "UPDATE Tbl_Departments SET Department_Name = '" + department.getDepartmentName() + "' WHERE Department_ID = " + department.getDepartmentID();
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Department's name changed successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to update Department.");
                    return false;
                }
    }
    
    public boolean archive(int departmentID) throws SQLException {
        String sql = "UPDATE Tbl_Departments SET Deleted = " + 1 + " WHERE Department_ID = " + departmentID;
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("office archived successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to archive office.");
                    return false;
                }
    }
    
    public List<Department> get(int office_ID)throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Tbl_Departments WHERE Deleted = 0 AND Office_ID = " + office_ID;
        ResultSet resultSet = databaseConnector.executeQuery(sql);
        if(resultSet != null)
        {
            
        while (resultSet.next()) {
             int departmentID = resultSet.getInt("Department_ID");
             String departmentName = resultSet.getString("Department_Name");
             int officeID = resultSet.getInt("Office_ID");
            departments.add(new Department(departmentID, departmentName, officeID));
            
        }
        return departments;
        }
        return null;
    }
    
}
