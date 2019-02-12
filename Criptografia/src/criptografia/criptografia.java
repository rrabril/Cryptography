package criptografia;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.security.*;
import javax.crypto.*;
import java.util.Base64;

public class criptografia {
	
	public static void main (String[] args) {
		System.out.println("Introduce el mensaje a cifrar");
		Scanner entrada = new Scanner(System.in);
		String mensaje = entrada.nextLine();
		entrada.close();
		byte[] Datos = mensaje.getBytes();
		try {
			MessageDigest resumen = MessageDigest.getInstance("SHA-256");
			byte[] digest = resumen.digest(Datos);
			String cifrado = new String (digest);
			System.out.println(cifrado);
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}

/*
 byte[] encodedBytes = Base64.getEncoder().encode("Test".getBytes());
System.out.println("Bytes codificados " + new String(encodedBytes));
byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
System.out.println("Bytes descodificados " + new String(decodedBytes));


MessageDigest resumen = MessageDigest.getInstance("SHA-256");
byte[] encodedhash = resumen.digest(
  originalString.getBytes(StandardCharsets.UTF_8));
  
  private static String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
    String hex = Integer.toHexString(0xff & hash[i]);
    if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}

MessageDigest resumen = MessageDigest.getInstance("SHA-256");
byte[] hash = resumen.digest(
  originalString.getBytes(StandardCharsets.UTF_8));
String sha256hex = new String(Hex.encode(hash));


KeyGenerator generador = KeyGenerator.getInstance("AES");
SecureRandom aleatorio = new SecureRandom(); // cryptograph. secure random 
generador.init(aleatorio); 
SecretKey clave = generador.generateKey();


KeyGenerator generador = KeyGenerator.getInstance("AES");
generador.init(256); // for example
SecretKey clave = generador.generateKey();


Key key;
SecureRandom aleatorio = new SecureRandom();
KeyGenerator generador = KeyGenerator.getInstance("AES");
generator.init(256, aleatorio);
clave = generador.generateKey();

SecretKey to String:

// crear nueva clave
SecretKey clave = KeyGenerator.getInstance("AES").generateKey();
// crear la versión codificada en Base64 de la clave
String clavecodificada = Base64.getEncoder().encodeToString(clave.getEncoded());

De String a SecretKey:

// Descodificar la cadena codificada en Base64
byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
// Reconstruir la clave utilizando SecretKeySpec
SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 



 */