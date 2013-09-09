/*
 * All encyption and decryption done here..
 */
package passvault;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Cypher
 */
public class EncDec
{
    private Cipher cipher;
    private SecretKeySpec sks;
    
        
    public EncDec(String myKey) throws NoSuchAlgorithmException, 
            NoSuchPaddingException
    {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        sks = new SecretKeySpec(keyMaker(myKey), "AES");
    }
    
    public String encrypt(String inputText) throws InvalidKeyException, 
            IllegalBlockSizeException, 
            BadPaddingException
    {
        String result = null;
        
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        result = Base64.encode(cipher.doFinal(inputText.getBytes()));
        
        return result;
    }
    
    public String decrypt(String inputText) throws InvalidKeyException, 
            IllegalBlockSizeException, 
            BadPaddingException
    {
        String result = null;
        
        cipher.init(Cipher.DECRYPT_MODE, sks);
        result = new String(cipher.doFinal(Base64.decode(inputText)));
        
        return result;
    }
    
    private byte[] keyMaker(String phrase)
    {
        byte[] myKey = new byte[16];
        if(phrase != null)
        {
            int size = phrase.toCharArray().length;
            
        
            if(size >= 16)
            {
                for(int i = 0; i < 16; i++)
                {
                    myKey[i] = (byte) phrase.charAt(i);
                }
            }if(size < 16)
            {
                for(int i = 0; i < 16; i++)
                {
                    if(i < phrase.toCharArray().length)
                    {
                        myKey[i] = (byte) phrase.charAt(i);
                    }else
                    {
                        myKey[i] = (byte) i;
                    }
                }
            }
        }
        return myKey;
    }
}
