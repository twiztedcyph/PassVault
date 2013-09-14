/*
 * The visual part of the program....
 */
package passvault;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Cypher
 */
public class Frame extends JPanel
{
    private JLabel addSiteLabel, addUserLabel, addPassLabel, searchSiteLabel,
            delSiteLabel, addBySiteLabel, searchBySiteLabel, delBySiteLabel;
    private JTextField addSiteInput, addUserInput, addPassInput, 
            searchSiteInput, delSiteInput;
    private JButton addButton, searchButton, delButton, closeButton;
    private StorageStuff storage;
    
    public Frame()
    {
        try
        {
            boolean isAuth = false;
            int count = 4;
            storage = new StorageStuff();
            while(!isAuth)
            {
                if(count <= 1)
                {
                    JOptionPane.showMessageDialog(null
                            , "The pass phrase entered was incorrect. Exiting."
                            , "Incorrect pass phrase"
                            , JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                isAuth = storage.start(--count);
            }
        } catch (NoSuchPaddingException 
                | NoSuchAlgorithmException 
                | InvalidKeyException 
                | IllegalBlockSizeException 
                | BadPaddingException 
                | IOException 
                | HeadlessException e)
        {
            JOptionPane.showMessageDialog(null, "Fatal error. Exiting", "Error"
                    , JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        JFrame frame = new JFrame("PassVault by Twizz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(500, 300));
        this.setLayout(null);
        
        setLabels();
        setFields();
        setButtons();
        
        frame.add(Frame.this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void setLabels()
    {
        addSiteLabel = new JLabel("Site:");
        addSiteLabel.setBounds(45, 40, 100, 20);
        this.add(addSiteLabel);
        
        addUserLabel = new JLabel("Username:");
        addUserLabel.setBounds(45, 100, 100, 20);
        this.add(addUserLabel);
        
        addPassLabel = new JLabel("Password:");
        addPassLabel.setBounds(45, 160, 100, 20);
        this.add(addPassLabel);
        
        searchSiteLabel = new JLabel("Site:");
        searchSiteLabel.setBounds(300, 40, 100, 20);
        this.add(searchSiteLabel);
        
        delSiteLabel = new JLabel("Site:");
        delSiteLabel.setBounds(300, 160, 100, 20);
        this.add(delSiteLabel);
        
        addBySiteLabel = new JLabel("Add new");
        searchBySiteLabel = new JLabel("Search by site");
        delBySiteLabel = new JLabel("Delete by site");
    }
    
    private void setFields()
    {
        addSiteInput = new JTextField();
        addSiteInput.setBounds(45, 60, 150, 20);
        this.add(addSiteInput);
        
        addUserInput = new JTextField();
        addUserInput.setBounds(45, 120, 150, 20);
        this.add(addUserInput);
        
        addPassInput = new JTextField();
        addPassInput.setBounds(45, 180, 150, 20);
        this.add(addPassInput);
        
        searchSiteInput = new JTextField();
        searchSiteInput.setBounds(300, 60, 150, 20);
        this.add(searchSiteInput);
        
        delSiteInput = new JTextField();
        delSiteInput.setBounds(300, 180, 150, 20);
        this.add(delSiteInput);
    }
    
    private void setButtons()
    {
        addButton = new JButton("Add");
        addButton.setBounds(68, 210, 100, 30);
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!addSiteInput.getText().isEmpty() 
                        && !addUserInput.getText().isEmpty() 
                        && !addPassInput.getText().isEmpty())
                {
                    String key = addSiteInput.getText();
                    String value = addUserInput.getText() + "%$%$%" 
                            + addPassInput.getText();
                    try
                    {
                        storage.setProp(key, value);
                        storage.saveProp();
                        storage.loadProp();
                    } catch (IOException | InvalidKeyException 
                            | IllegalBlockSizeException 
                            | BadPaddingException ex)
                    {
                        System.out.println(ex);
                    }
                }
            }
        });
        this.add(addButton);
        
        searchButton = new JButton("Search");
        searchButton.setBounds(325, 90, 100, 30);
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!searchSiteInput.getText().isEmpty())
                {
                    String key = searchSiteInput.getText();
                    try
                    {
                        String result = storage.getProp(key);
                        if(result != null)
                        {
                            String site = "    Site: " + key;
                            String user = "Username: " 
                                    + result.split("\\%\\$\\%\\$\\%")[0];
                            String pass = "Password: " 
                                    + result.split("\\%\\$\\%\\$\\%")[1];
                            String output = "<html><pre>" + site + "<br />" 
                                    + user + "<br />" + pass + "</pre></html>";
                            JOptionPane.showMessageDialog(null, output
                                    , "Results"
                                    , JOptionPane.INFORMATION_MESSAGE);
                        }else
                        {
                            String output = "No record for " + key + " found.";
                            JOptionPane.showMessageDialog(null, output
                                    , "Results"
                                    , JOptionPane.INFORMATION_MESSAGE);
                            searchSiteInput.requestFocus();
                        }
                    } catch (InvalidKeyException 
                            | IllegalBlockSizeException 
                            | BadPaddingException ex)
                    {
                        System.out.println(ex);
                    }
                    searchSiteInput.setText("");
                }else
                {
                    searchSiteInput.requestFocus();
                }
            }
        });
        this.add(searchButton);
        
        delButton = new JButton("Delete");
        delButton.setBounds(325, 210, 100, 30);
        delButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });
        this.add(delButton);
        
        closeButton = new JButton("Close");
        closeButton.setBounds(198, 260, 100, 30);
        closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });
        this.add(closeButton);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(40, 40, 160, 165);
        g2d.drawRect(295, 40, 160, 45);
        g2d.drawRect(295, 160, 160, 45);
    }
}
