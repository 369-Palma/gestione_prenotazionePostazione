//package com.palma.gestioneprenotazioni.auth.configuration;
//
//import javax.crypto.Cipher;
//import java.security.Key;
//import java.util.Base64;
//import jakarta.persistence.AttributeConverter;
//import javax.crypto.spec.SecretKeySpec;
//
//
//public class SecretCodeConverter implements AttributeConverter<String, String> {
//
//	private static final String ALGORITHM = "AES/CFB/PKCS5Padding";
//	private static final byte[] KEY = "MySUperSecretKey".getBytes();
//
//	
//	@Override
//	public String convertToDatabaseColumn(String attribute) {
//		Key key = new SecretKeySpec(KEY, "AES");
//		try {
//			Cipher c = Cipher.getInstance(ALGORITHM);
//			c.init(Cipher.ENCRYPT_MODE, key);
//			return Base64.getEncoder().encodeToString((c.doFinal(attribute.getBytes())));
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	public String convertToEntityAttribute(String dbData) {
//		Key key = new SecretKeySpec(KEY, "AES");
//		try {
//			Cipher c = Cipher.getInstance(ALGORITHM);
//			c.init(Cipher.DECRYPT_MODE, key);
//			return new String((c.doFinal(Base64.getDecoder().decode(dbData))));
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//}
