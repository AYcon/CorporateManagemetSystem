/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import com.mycompany.hrmanagement.model.Company;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amin
 */
public class CompanyDAO implements IDAO <Company> {
    private IDatabaseConnector databaseConnector;
    private int rowsAffected;
    public CompanyDAO(IDatabaseConnector databaseConnector)
    {
        this.databaseConnector = databaseConnector;
    }
    
    @Override
    public boolean create(Company company)
    {
        String sql = "INSERT INTO Tbl_Company (Company_Name) VALUES ('" + company.getName() + "')";
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Company added successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to add company.");
                    return false;
                }
    }
    
    public boolean update(Company company)throws SQLException {
        String sql = "UPDATE Tbl_Company SET Company_Name = '" + company.getName() + "' WHERE Company_ID = " + company.getID();
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Company's name changed successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to update company.");
                    return false;
                }
    }
    
    public boolean archive(int companyId) throws SQLException {
        String sql = "UPDATE Tbl_Company SET Deleted = " + 1 + " WHERE Company_ID = " + companyId;
        rowsAffected = databaseConnector.executeUpdate(sql);
        if(rowsAffected > 0)
        {
            System.out.println("Company archived successfully.");
            return true;
                } 
        else {
                    System.out.println("Failed to archive company.");
                    return false;
                }
    }
    
    @Override
    public List<Company> get(int i) throws SQLException  {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM Tbl_Company WHERE Deleted = 0";
        ResultSet resultSet = databaseConnector.executeQuery(sql);
        if(resultSet != null)
        {
            
        while (resultSet.next()) {
            int companyId = resultSet.getInt("Company_ID");
            String companyName = resultSet.getString("Company_Name");
            companies.add(new Company(companyId, companyName));
            
        }
        return companies;
        }
        return null;
    }
    
    public Company getCompanyByID(int Company_ID) throws SQLException  {
        String sql = "SELECT * FROM Tbl_Company WHERE Company_ID = " + Company_ID + " AND Deleted = 0";
        ResultSet resultSet = databaseConnector.executeQuery(sql);
        Company company = null;
        if(resultSet != null)
        {
            
        while (resultSet.next()) {
            int companyId = resultSet.getInt("Company_ID");
            String companyName = resultSet.getString("Company_Name");
            company = new Company(companyId, companyName);
            
        }
        return company;
        }
        return null;
    }
    
    
    
}
