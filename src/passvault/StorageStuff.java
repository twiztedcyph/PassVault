/*
 * All file handling including creation, deletion, 
 * reading and writing done here....
 */
package passvault;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author Cypher
 */
public class StorageStuff
{
    private Properties prop;
    private final String progKey = "thisisaboringkey"; //Feel free to change.
    private final String FILE_NAME = "properties.config";
    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private String myKey;
    private EncDec crypt;
    private File file;
    
    public StorageStuff()
    {
        file = new File(FILE_NAME);
    }
    
    public boolean start(int tries) throws NoSuchPaddingException
            , NoSuchAlgorithmException
            , InvalidKeyException
            , IllegalBlockSizeException
            , BadPaddingException
            , FileNotFoundException
            , IOException
    {
        boolean result = false;
        if(file.exists())
        {
            prop = new Properties();
            this.setEncKey(0, tries);
            crypt = new EncDec(progKey);
            this.loadProp();
            if(myKey.equals(crypt.decrypt(prop.getProperty(
                    crypt.encrypt("$$$userKey$$$")))))
            {
                result = true;
            }
        }else
        {
            prop = new Properties();
            this.setEncKey(1, 0);
            crypt = new EncDec(progKey);
            
            this.setProp("$$$userKey$$$", myKey);
            this.saveProp();
            result = true;
        }
        System.out.println(result);
        return result;
    }
    
    public void setProp(String key, String value) throws InvalidKeyException
            , IllegalBlockSizeException
            , BadPaddingException
    {
        prop.setProperty(crypt.encrypt(key), crypt.encrypt(value));
    }
    
    public String getProp(String key) throws InvalidKeyException
            , IllegalBlockSizeException
            , BadPaddingException
    {
        if(prop.getProperty(crypt.encrypt(key)) != null)
        {
            return crypt.decrypt(prop.getProperty(crypt.encrypt(key)));
        }
        return null;
    }
    
    public void saveProp() throws FileNotFoundException, IOException
    {
        fileOut = new FileOutputStream(FILE_NAME);
        prop.store(fileOut, null);
    }
    
    public void loadProp() throws FileNotFoundException, IOException
    {
        fileIn = new FileInputStream(FILE_NAME);
        prop.load(fileIn);
    }
    
    private void setEncKey(int startType, int tries)
    {
        switch(startType)
        {
            case 0:
                myKey = JOptionPane.showInputDialog(null
                        , "<html>Please enter your pass phrase. <br />"
                        + tries + " attempts remaining.<html>"
                        , "Pass phrase"
                        , JOptionPane.INFORMATION_MESSAGE);
                if(myKey == null || myKey.isEmpty())
                {
                    JOptionPane.showMessageDialog(null
                            , "Pass phrase required. Exiting.");
                    System.exit(0);
                }
                break;
            case 1:
                myKey = JOptionPane.showInputDialog(null
                        ,"<html>Please enter a pass phrase."
                        + "<br />It will be used to encrypt your data."
                        + "<br />A lost pass phrase == lost data.</html>"
                        , "First run setup.", JOptionPane.INFORMATION_MESSAGE);
                if(myKey == null || myKey.isEmpty())
                {
                    JOptionPane.showMessageDialog(null
                            , "Pass phrase required. Exiting.");
                    System.exit(0);
                }
                break;
            default:
                //This option should never be reached..... 
                //Including it just incase though...
                System.exit(0);
                break;
        }
    }
}
