/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.view;

import com.mycompany.hrmanagement.model.DAO.*;
import com.mycompany.hrmanagement.model.*;
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
public class OfficeListView extends JFrame{
    
     private List<Office> offices = new ArrayList<>();
    private JList<String> officeList;
    private JButton createButton;
    private JButton viewDepartments;
    private JButton editButton;
    private JButton archiveButton;
    private JButton backButton;
    private final IDAO officeDAO;
    private final IDAO departmentDAO;
    private final CompanyListGUI companyListGUI;
    private final Company company;
    private Timer refreshTimer;
    

    public OfficeListView(IDAO officeDAO,IDAO departmentDAO, Company company, CompanyListGUI companyListGUI) {
        this.officeDAO = officeDAO;
        this.departmentDAO = departmentDAO;
        this.company = company;
        this.companyListGUI = companyListGUI;
        updateOfficesList(0);
        int refreshRateMillis = 60000; // 5 seconds
        refreshTimer = new Timer(refreshRateMillis, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                
                updateOfficesList(1);
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
        for (var office : offices) {
            listModel.addElement(office.getOfficeName());
        }

        officeList = new JList<>(listModel);
        officeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(officeList);
        scrollPane.getViewport().setBackground(Color.BLACK);
        backButton = new JButton("Back");
        //officesButton.setVisible(false);
        createButton = new JButton("Create");
        editButton = new JButton("Edit");
        archiveButton = new JButton("Archive");
        viewDepartments = new JButton("view departments");
        editButton.setEnabled(false);
        archiveButton.setEnabled(false);
        viewDepartments.setEnabled(false);

        officeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelectionEmpty = officeList.isSelectionEmpty();
                editButton.setEnabled(!isSelectionEmpty);
                archiveButton.setEnabled(!isSelectionEmpty);
                viewDepartments.setEnabled(!isSelectionEmpty);
            }
        });
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOffice();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedOffice();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToCompany();
            }
        });

        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archiveSelectedOffice();
            }
        });
        
        viewDepartments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDepartments();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(archiveButton);
        buttonPanel.add(backButton);
        buttonPanel.add(viewDepartments);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }
    
   
    private void updateOfficesList(int flag)
    {
        try {
            offices = officeDAO.get(company.getID());
            if(flag != 0)
            {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (var office : offices) {
                listModel.addElement(office.getOfficeName());
            }
        
            officeList.setModel(listModel);}
        } catch (SQLException ex) {
            Logger.getLogger(CompanyListGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void createOffice()
    {
        Office office = new Office();
        JTextField textField1 = new JTextField();
         JTextField textField2 = new JTextField();
         JTextField textField3 = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("New Office name*"));
        panel.add(textField1);
        panel.add(new JLabel("New Office address*"));
        panel.add(textField2);
        panel.add(new JLabel("New Office Telephone(optional)"));
        panel.add(textField3);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit office's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
             office.setOfficeName(textField1.getText());
             office.setOfficeAddress(textField2.getText());
             office.setOfficeTelephone(textField3.getText());
             office.setCompanyID(company.getID());
             officeDAO.create(office);
            
        }
             //companyController.update(offices.get(selectedIndex), newCompanyName);
             updateOfficesList(1);
        
    }
     
     
    private void editSelectedOffice() {
        int selectedIndex = officeList.getSelectedIndex();
        Office office = offices.get(selectedIndex);
        if (selectedIndex != -1) {
             JTextField textField1 = new JTextField(office.getOfficeName());
             JTextField textField2 = new JTextField(office.getOfficeAddress());
             JTextField textField3 = new JTextField(office.getOfficeTelephone());

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("New Office name*"));
            panel.add(textField1);
            panel.add(new JLabel("New Office address*"));
            panel.add(textField2);
            panel.add(new JLabel("New Office Telephone(optional)"));
            panel.add(textField3);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit office's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
             office.setOfficeName(textField1.getText());
             office.setOfficeAddress(textField2.getText());
             office.setOfficeTelephone(textField3.getText());
                 try {
                     officeDAO.update(office);
                 } catch (SQLException ex) {
                     Logger.getLogger(OfficeListView.class.getName()).log(Level.SEVERE, null, ex);
                 }
            
            
        }
             //companyController.update(offices.get(selectedIndex), newCompanyName);
             updateOfficesList(1);
        }
    }

    private void archiveSelectedOffice() {
        int selectedIndex = officeList.getSelectedIndex();
        Office office = offices.get(selectedIndex);
        if (selectedIndex != -1) {
            try {
                officeDAO.archive(office.getOfficeID());
            } catch (SQLException ex) {
                Logger.getLogger(OfficeListView.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateOfficesList(1);
        
    }
    }
    
    private void returnToCompany()
    {
        companyListGUI.setVisible(true);
        dispose();
    }
    
    private void viewDepartments()
    {
        int selectedIndex = officeList.getSelectedIndex();
        if (selectedIndex != -1) {
            DepartmentListView departmentListView = new DepartmentListView(departmentDAO, offices.get(selectedIndex), this);
            departmentListView.setVisible(true);
            setVisible(false);
            //dispose();
        }
    }
    
}
