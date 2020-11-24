package Utilities.Files;

import Manager.Control;
import com.mchange.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

public class FileCrypto {

    public SecretKey secretKey;
    public Cipher cipher;
    byte[] array = Control.serverSettings.getSecret16();

    public FileCrypto() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = new SecretKeySpec(array, "AES");
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }


    public String decrypt(String fileName) {
        String content = null;
        try {
            byte[] bytes = FileUtils.getBytes(new File(fileName));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            return new String(cipher.doFinal(bytes), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


    public void encrypt(String content, String fileName) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            byte[] array = cipher.doFinal(content.getBytes("UTF-8"));
            new File(fileName).delete();
            OutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(array);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

