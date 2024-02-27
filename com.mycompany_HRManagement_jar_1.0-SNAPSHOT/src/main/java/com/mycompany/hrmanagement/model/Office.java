/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model;

import java.util.List;

/**
 *
 * @author Amin
 */
public class Office {
    
    private int officeID;
    private String officeName;
    private String officeAddress;
    private String officeTelephone;
    private int companyID;
    private List <Department> departmentList;
    
    public Office()
    {
        
    }
    public Office(int officeID, String officeName, String officeAddress, String officeTelephone, int companyID) {
        this.officeID = officeID;
        this.officeName = officeName;
        this.officeAddress = officeAddress;
        this.officeTelephone = officeTelephone;
        this.companyID = companyID;
    }
    
    public void setDepartmentList (List <Department> departmentList)
    {
        this.departmentList = departmentList;
    }
    
    public List <Department> getDepartmentList()
    {
        return departmentList;
    }
    
    public int getOfficeID() {
        return officeID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }
    public int getCompanyID() {
        return companyID;
    }
    
    public void setOfficeName(String officeName) {
    this.officeName = officeName;
}

    public void setOfficeAddress(String officeAddress) {
    this.officeAddress = officeAddress;
}

    public void setOfficeTelephone(String officeTelephone) {
    this.officeTelephone = officeTelephone;
}
    public void setCompanyID(int companyID) {
    this.companyID = companyID;
}

    
    
}
