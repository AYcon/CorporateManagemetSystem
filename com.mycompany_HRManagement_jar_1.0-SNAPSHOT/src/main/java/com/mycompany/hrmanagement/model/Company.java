/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model;

import com.mycompany.hrmanagement.model.DAO.IDatabaseConnector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Company {
    
    
    private int Company_ID;
    private String Company_Name;
    private List <Office> officeList;
    
    public Company()
    {
        
    }
    public Company(int Company_ID, String Company_Name)
    {
        this.Company_ID = Company_ID;
        this.Company_Name = Company_Name;
    }
    
    public String getName()
    {
        return Company_Name;
    }
    public void setName(String Company_Name)
    {
        this.Company_Name = Company_Name;
    }
    
    public int getID()
    {
        return Company_ID;
    }
    
    public void setID(int Company_ID)
    {
        this.Company_ID = Company_ID;
    }
    
    public void setofficeList (List <Office> officeList)
    {
        this.officeList = officeList;
    }
    
    public List <Office> getofficeList()
    {
        return officeList;
    }
    
}
