/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import com.mycompany.hrmanagement.model.Company;
import com.mycompany.hrmanagement.model.Office;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Amin
 */
public interface IDAO <T> {
    
     public boolean create(T object);
     public List<T> get(int object_ID)throws SQLException;
     public boolean update(T object)throws SQLException;
     public boolean archive(int officeID) throws SQLException;
    
}
