/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.view;

import com.mycompany.hrmanagement.model.*;
import com.mycompany.hrmanagement.model.DAO.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Amin
 */
public class DepartmentListView extends JFrame {
    
     private List<Department> deparments = new ArrayList<>();
    private JList<String> departmentList;
    private JButton createButton;
    private JButton editButton;
    private JButton archiveButton;
    private JButton backButton;
    private final IDAO departmentDAO;
    private final OfficeListView officeListView;
    private final Office office;
    private Timer refreshTimer;
    

    public DepartmentListView(IDAO departmentDAO, Office office, OfficeListView officeListView) {
        this.departmentDAO = departmentDAO;
        this.office = office;
        this.officeListView = officeListView;
        updateDepartmentList(0);
        int refreshRateMillis = 60000; // 5 seconds
        refreshTimer = new Timer(refreshRateMillis, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                
                updateDepartmentList(1);
            }
        });
        refreshTimer.start();
        initComponents();
    }

    private void initComponents() {
        setTitle("Office List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(573, 340);
        setLocationRelativeTo(null);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (var department : deparments) {
            listModel.addElement(department.getDepartmentName());
        }

        departmentList = new JList<>(listModel);
        departmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(departmentList);
        scrollPane.getViewport().setBackground(Color.BLACK);
        backButton = new JButton("Back");
        //officesButton.setVisible(false);
        createButton = new JButton("Create");
        editButton = new JButton("Edit");
        archiveButton = new JButton("Archive");
        editButton.setEnabled(false);
        archiveButton.setEnabled(false);

        departmentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelectionEmpty = departmentList.isSelectionEmpty();
                editButton.setEnabled(!isSelectionEmpty);
                archiveButton.setEnabled(!isSelectionEmpty);
            }
        });
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDepartment();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedDepartment();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToOffice();
            }
        });

        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archiveSelectedDepartment();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(archiveButton);
        buttonPanel.add(backButton);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }
    
   
    private void updateDepartmentList(int flag)
    {
        try {
            deparments = departmentDAO.get(office.getOfficeID());
            if(flag != 0)
            {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (var department : deparments) {
                listModel.addElement(department.getDepartmentName());
            }
        
            departmentList.setModel(listModel);}
        } catch (SQLException ex) {
            Logger.getLogger(CompanyListGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void createDepartment()
    {
        Department department = new Department();
        JTextField textField1 = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("New Department name*"));
        panel.add(textField1);

        int result = JOptionPane.showConfirmDialog(null, panel, "Fill Department's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
             department.setDepartmentName(textField1.getText());
             department.setOfficeID(office.getOfficeID());
             departmentDAO.create(department);
            
        }
             //companyController.update(offices.get(selectedIndex), newCompanyName);
             updateDepartmentList(1);
        
    }
     
     
    private void editSelectedDepartment() {
        int selectedIndex = departmentList.getSelectedIndex();
        Department department = deparments.get(selectedIndex);
        if (selectedIndex != -1) {
             JTextField textField1 = new JTextField(department.getDepartmentName());

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("New Department name*"));
            panel.add(textField1);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Department's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            department.setDepartmentName(textField1.getText());
                 try {
                     departmentDAO.update(department);
                 } catch (SQLException ex) {
                     Logger.getLogger(OfficeListView.class.getName()).log(Level.SEVERE, null, ex);
                 }
            
            
        }
             //companyController.update(offices.get(selectedIndex), newCompanyName);
             updateDepartmentList(1);
        }
    }

    private void archiveSelectedDepartment() {
        int selectedIndex = departmentList.getSelectedIndex();
        Department department = deparments.get(selectedIndex);
        if (selectedIndex != -1) {
            try {
                departmentDAO.archive(department.getDepartmentID());
            } catch (SQLException ex) {
                Logger.getLogger(OfficeListView.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateDepartmentList(1);
        
    }
    }
    
    private void returnToOffice()
    {
        officeListView.setVisible(true);
        dispose();
    }
    
}
