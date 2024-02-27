/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import com.mycompany.hrmanagement.model.Company;
import com.mycompany.hrmanagement.model.Office;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amin
 */
public class OfficeDAO implements IDAO<Office> {
    
     private IDatabaseConnector databaseConnector;
     private int rowsAffected;
    public OfficeDAO(IDatabaseConnector databaseConnector)
    {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public boolean create(Office office) {
        String sql = "INSERT INTO Tbl_Offices (Office_Name, Office_Address, Office_Telephone, Company_ID) VALUES ('" + office.getOfficeName() + "','" + office.getOfficeAddress() + "','" + office.getOfficeTelephone()+ "', " + office.getCompanyID() + ")";
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Office added successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to add Office.");
                    return false;
                }
    }

    @Override
    public List<Office> get(int Company_ID)throws SQLException {
        List<Office> Offices = new ArrayList<>();
        String sql = "SELECT * FROM Tbl_Offices WHERE Deleted = 0 AND Company_ID = " + Company_ID;
        ResultSet resultSet = databaseConnector.executeQuery(sql);
        if(resultSet != null)
        {
            
        while (resultSet.next()) {
             int officeID = resultSet.getInt("Office_ID");
             String officeName = resultSet.getString("Office_Name");
             String officeAddress = resultSet.getString("Office_Address");
             String officeTelephone = resultSet.getString("Office_Telephone");
             int companyID = resultSet.getInt("Company_ID");
            Offices.add(new Office(officeID, officeName, officeAddress, officeTelephone, companyID));
            
        }
        return Offices;
        }
        return null;
    }

    @Override
    public boolean update(Office office) throws SQLException {
        String sql = "UPDATE Tbl_Offices SET Office_Name = '" + office.getOfficeName() + "', Office_Address = '" + office.getOfficeAddress() + "', Office_Telephone = '" + office.getOfficeTelephone() + "' WHERE Office_ID = " + office.getOfficeID();
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Office's name changed successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to update office.");
                    return false;
                }
    }

    @Override
    public boolean archive(int officeID) throws SQLException {
        String sql = "UPDATE Tbl_Offices SET Deleted = " + 1 + " WHERE Office_ID = " + officeID;
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
    
}
