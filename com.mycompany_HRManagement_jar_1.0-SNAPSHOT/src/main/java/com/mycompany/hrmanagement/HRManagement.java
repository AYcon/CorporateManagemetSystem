/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hrmanagement;
import com.mycompany.hrmanagement.model.DAO.*;
import com.mycompany.hrmanagement.view.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */
public class HRManagement {

    private static final String URL = "jdbc:sqlserver://WIN-HED6UGBFS6B\\SQLEXPRESS;databasename=HALA;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "nimda@1234";
    private static final IDatabaseConnector databaseConnector = new MSSQLConnector(URL, USER, PASSWORD);
    private static final IDAO companyDAO = new CompanyDAO(databaseConnector);
    private static final IDAO officeDAO = new OfficeDAO(databaseConnector);
    private static final DepartmentDAO departmentDAO = new DepartmentDAO(databaseConnector);
    
    

    public static void main(String[] args) {
        
        
        //do{
            SwingUtilities.invokeLater(() -> new CompanyListGUI(companyDAO, officeDAO,departmentDAO).setVisible(true));
            /*controller  = IO_Controller.runApp();
            if(controller != null)
                controller.runController();
        }while(controller != null);*/
        
    }
}

