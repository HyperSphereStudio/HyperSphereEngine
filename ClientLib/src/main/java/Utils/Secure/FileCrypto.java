package Utils.Secure;
import Manager.Control;
import com.badlogic.gdx.files.FileHandle;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

public class FileCrypto {

    public SecretKey secretKey;
    public Cipher cipher;
    byte[] array = Control.getSecret16();


    public FileCrypto() throws NoSuchPaddingException, NoSuchAlgorithmException {
            this.secretKey = new SecretKeySpec(array, "AES");
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }


    public String decrypt(FileHandle fileHandle) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            return new String(cipher.doFinal(fileHandle.readBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String contents){
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            return new String(cipher.doFinal(contents.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decryptInput(byte[] input){
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void encrypt(String content, String fileName) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();

            try (FileOutputStream fileOut = new FileOutputStream(fileName);
                 CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
                fileOut.write(iv);
                cipherOut.write(content.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

