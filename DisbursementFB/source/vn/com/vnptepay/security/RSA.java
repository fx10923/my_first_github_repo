package vn.com.vnptepay.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSA {
	public static String signData(String data, String privateKey) throws Exception {
		try {
			byte[] bprivateKey = Base64Utils.base64Decode(privateKey.replace(" ", ""));
			Security.addProvider(new BouncyCastleProvider());
			PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate((new PKCS8EncodedKeySpec(bprivateKey)));
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(key);
			signer.update(data.getBytes());
			return (Base64Utils.base64Encode(signer.sign()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean verify(String data, String sign, String key_public){
		try {
			byte[] publicKeyBytes = Base64Utils.base64Decode(key_public);
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(
					new X509EncodedKeySpec(publicKeyBytes));
			Signature rsa = Signature.getInstance("SHA1withRSA");
			rsa.initVerify(publicKey);
			rsa.update(data.getBytes());
			byte[] signByte = Base64Utils.base64Decode(sign);
			return (rsa.verify(signByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
