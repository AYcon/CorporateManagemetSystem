/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.model;

/**
 *
 * @author Amin
 */
public class Department {
    
    private int departmentID;
    private String departmentName;
    private int officeID;
    
    public Department()
    {
        
    }
    public Department(int departmentID, String departmentName, int officeID) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.officeID = officeID;
    }
    
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    // Getter for departmentID
    public int getDepartmentID() {
        return departmentID;
    }

    // Setter for departmentName
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // Getter for departmentName
    public String getDepartmentName() {
        return departmentName;
    }

    // Setter for officeID
    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    // Getter for officeID
    public int getOfficeID() {
        return officeID;
    }
}
