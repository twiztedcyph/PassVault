/*
 * The visual part of the program....
 */
package passvault;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
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
            delSiteLabel;
    private JTextField addSiteInput, addUserInput, addPassInput, 
            searchSiteInput, delSiteInput;
    private JButton addButton, searchButton, delButton;
    
    public Frame()
    {
        try
        {
            boolean isAuth = false;
            int count = 4;
            StorageStuff s = new StorageStuff();
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
                isAuth = s.start(--count);
            }
        } catch (NoSuchPaddingException 
                | NoSuchAlgorithmException 
                | InvalidKeyException 
                | IllegalBlockSizeException 
                | BadPaddingException 
                | IOException 
                | HeadlessException e)
        {
            System.out.println(e);
        }
        JFrame frame = new JFrame("PassVault by Twizz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(500, 300));
        this.setLayout(null);
        
        frame.add(Frame.this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void setLabels()
    {
        addSiteLabel = new JLabel("Site:");
        addSiteLabel.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
        this.add(addSiteLabel);
        
        addUserLabel = new JLabel("Username:");
        addUserLabel.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
        this.add(addUserLabel);
        
        addPassLabel = new JLabel("Password:");
        addPassLabel.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
        this.add(addPassLabel);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        //draw stuff here.....
    }
}
