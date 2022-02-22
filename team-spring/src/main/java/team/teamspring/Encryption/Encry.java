package team.teamspring.Encryption;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

//RSA ECB 방식
public class Encry {
    private static String publicKey = "";
    private static String privateKey = "";

    private static String encStr;

    public static String getEncStr() {
        return encStr;
    }

    public static String Encryption(String vote) throws Exception{
        Encry.encStr = vote;;
        createRsaGenKey(); // key 생성


        encStr = rsaEnc(encStr);
        return Encry.encStr;
    }

    public static String Decryption(String vote) throws Exception{
        Encry.encStr = vote;

        encStr = rsaDec(vote);

        return Encry.encStr;
    }



    /**
     * "Java" 를 이용해 random 값을 추출하여 2048bit에 해당하는 키 생성
     * @throws Exception
     */
    private static void createRsaGenKey() throws Exception{

        String pubkey = "JavaProject";

        SecureRandom random = new SecureRandom(pubkey.getBytes());

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","SunJSSE"); // OK

        generator.initialize(512, random); // 여기에서는 2048 bit 키를 생성하였음

        KeyPair pair = generator.generateKeyPair();
        Key pubKey = pair.getPublic(); // Kb(pub) 공개키
        Key privKey = pair.getPrivate();// Kb(pri) 개인키


        System.out.println("pubKeyHex:\n" + byteArrayToHex(pubKey.getEncoded())+"\n");
        System.out.println("privKeyHex:\n" + byteArrayToHex(privKey.getEncoded())+"\n");

        publicKey = byteArrayToHex(pubKey.getEncoded());
        privateKey = byteArrayToHex(privKey.getEncoded());

    }

    /**
     * 암호화
     * @param encStr
     * @return byteArrayToHex(cipherText)
     * @throws Exception
     */
    private static String rsaEnc(String encStr) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE"); // 알고리즘 명 / Cipher 알고리즘 mode / padding

        X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(hexToByteArray(publicKey));

        KeyFactory ukeyFactory = KeyFactory.getInstance("RSA");

        PublicKey publickey = null;

        try {
            // PublicKey에 공용키 값 설정
            publickey = ukeyFactory.generatePublic(ukeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] input = encStr.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, publickey);

        byte[] cipherText = cipher.doFinal(input);

        return byteArrayToHex(cipherText);

    }

    /**
     * 복호화
     * @param // byteArrayToHex(cipherText) ==> decStr
     * @return
     * @throws Exception
     */
    private static String rsaDec(String decStr) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");

        PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(hexToByteArray(privateKey));
        KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 복호
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = cipher.doFinal(hexToByteArray(decStr));

        String returnStr = new String(plainText);

        return returnStr;
    }


    // hex string to byte[]
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    // byte[] to hex sting
    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

}