/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.hrmanagement.model.DAO;

import java.sql.ResultSet;

/**
 *
 * @author Administrator
 */
public interface IDatabaseConnector {
    int executeUpdate(String query);
    ResultSet executeQuery(String query);
    
}
