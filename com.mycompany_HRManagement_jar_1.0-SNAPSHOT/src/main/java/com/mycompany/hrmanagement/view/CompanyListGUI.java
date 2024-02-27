/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hrmanagement.view;

/**
 *
 * @author Amin
 */

import com.mycompany.hrmanagement.model.DAO.*;
import com.mycompany.hrmanagement.model.*;
import javax.swing.*;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CompanyListGUI extends JFrame {
    private List<Company> companies = new ArrayList<>();
    private JList<String> companyList;
    private JButton createButton;
    private JButton editButton;
    private JButton archiveButton;
    private JButton officesButton;
    private final IDAO companyDAO;
    private final IDAO officeDAO;
    private final DepartmentDAO departmentDAO;
    private final Timer refreshTimer;
    

    public CompanyListGUI(IDAO companyDAO, IDAO officeDAO, DepartmentDAO departmentDAO) {
        this.companyDAO = companyDAO;
        this.officeDAO = officeDAO;
        this.departmentDAO = departmentDAO;
        updateCompaniesList(0);
        int refreshRateMillis = 60000; // 5 seconds
        refreshTimer = new Timer(refreshRateMillis, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                
                updateCompaniesList(1);
            }
        });
        refreshTimer.start();
        initComponents();
    }

    private void initComponents() {
        setTitle("Company List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(573, 340);
        setLocationRelativeTo(null);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Company company : companies) {
            listModel.addElement(company.getName());
        }

        companyList = new JList<>(listModel);
        companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(companyList);
        scrollPane.getViewport().setBackground(Color.BLACK);
        officesButton = new JButton("View Offices");
        createButton = new JButton("Create");
        editButton = new JButton("Edit");
        archiveButton = new JButton("Archive");
        editButton.setEnabled(false);
        archiveButton.setEnabled(false);
        officesButton.setEnabled(false);

        companyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelectionEmpty = companyList.isSelectionEmpty();
                editButton.setEnabled(!isSelectionEmpty);
                officesButton.setEnabled(!isSelectionEmpty);
                archiveButton.setEnabled(!isSelectionEmpty);
            }
        });
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCompany();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedCompany();
            }
        });
        
        
        officesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOffices();
            }
        });

        archiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archiveSelectedCompany();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(archiveButton);
        buttonPanel.add(officesButton);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }
    
    private void createCompany() {
                Company company = new Company();
                JTextField textField1 = new JTextField();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("New Company name*"));
                panel.add(textField1);
                
                int result = JOptionPane.showConfirmDialog(null, panel, "Fill Company's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                     company.setName(textField1.getText());
                     companyDAO.create(company);

                }
                updateCompaniesList(1);
            }
    private void updateCompaniesList(int flag)
    {
        try {
            companies = companyDAO.get(0);
            if(flag != 0)
            {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Company company : companies) {
                listModel.addElement(company.getName());
            }
        
            companyList.setModel(listModel);}
        } catch (SQLException ex) {
            Logger.getLogger(CompanyListGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void editSelectedCompany() {
        int selectedIndex = companyList.getSelectedIndex();
        Company company = companies.get(selectedIndex);
        if (selectedIndex != -1) {
             JTextField textField1 = new JTextField(company.getName());

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("New Company name*"));
            panel.add(textField1);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Company's information(fields marked * cannot be empty)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            company.setName(textField1.getText());
                 try {
                     companyDAO.update(company);
                 } catch (SQLException ex) {
                     Logger.getLogger(OfficeListView.class.getName()).log(Level.SEVERE, null, ex);
                 }
            
            
        }
             updateCompaniesList(1);
        }
    }

    private void archiveSelectedCompany() {
        int selectedIndex = companyList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                //companyController.archive(companies.get(selectedIndex));
                companyDAO.archive(companies.get(selectedIndex).getID());
            } catch (SQLException ex) {
                Logger.getLogger(CompanyListGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateCompaniesList(1);
        }
    }
    
    private void viewOffices()
    {
        int selectedIndex = companyList.getSelectedIndex();
        if (selectedIndex != -1) {
            OfficeListView officeListView = new OfficeListView(officeDAO,departmentDAO, companies.get(selectedIndex), this);
            officeListView.setVisible(true);
            setVisible(false);
            //dispose();
        }
    }

    
}
