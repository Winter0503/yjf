package cn.ygyg.cloudpayment.utils;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */


import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtils {

    private static final String CHAR_ENCODE = "UTF-8";
    private static final String CIPHER_PADDING = "RSA/ECB/PKCS1Padding";

    /**
     * 公钥加密
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int maxBlockSize = publicKey.getModulus().bitLength() / 8  - 11;
        // 加密数据长度 <= 模长-11, 如果明文长度大于模长-11则要分组加密
        byte[] plantText = data.getBytes(Charset.forName(CHAR_ENCODE));
        int inputLen = plantText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxBlockSize) {
                out.write(cipher.doFinal(plantText, offSet, maxBlockSize));
            } else {
                out.write(cipher.doFinal(plantText, offSet, inputLen - offSet));
            }
            i++;
            offSet = i * maxBlockSize;
        }
        return Base64Encoder(out.toByteArray());
    }

    /**
     * 从文件中输入流中加载公钥
     */
    private static String loadPublicKeyFromFile(InputStream in) throws Exception{
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String readLine= null;
            StringBuilder sb= new StringBuilder();
            while((readLine= br.readLine())!=null){
                if(readLine.charAt(0)=='-'){
                    continue;
                }else{
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }


    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception{
        try {
            byte[] buffer= Base64Decoder(publicKeyStr);
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    private static String Base64Encoder(byte[] data) {
        return android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP);
    }

    private static byte[] Base64Decoder(String data){
        try{
            byte[] decode = android.util.Base64.decode(data, android.util.Base64.NO_WRAP);

            return decode;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}


