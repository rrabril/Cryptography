package criptografia;

import java.security.*;
import javax.crypto.*;
import java.io.*;
import java.util.Base64;
import java.util.Scanner;

public class firma {

	public static KeyPair generar_par() throws NoSuchAlgorithmException {
		KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
		SecureRandom aleatorio = new SecureRandom();
		generador.initialize(2048, aleatorio);
		KeyPair par = generador.generateKeyPair();
		return par;
	}
	
	public static String encriptar (String texto_plano, PublicKey publica) throws Exception {
		Cipher encripta;
		encripta = Cipher.getInstance("RSA");
		encripta.init(Cipher.ENCRYPT_MODE,publica);
		byte [] bytes_cifrado;
		bytes_cifrado = encripta.doFinal(texto_plano.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(bytes_cifrado);
	}
	
	public static String desencriptar (String cifrado, PrivateKey privada) throws Exception {
		byte [] bytes_cifrado = Base64.getDecoder().decode(cifrado);
		Cipher desencripta;
		desencripta = Cipher.getInstance("RSA");
		desencripta.init(Cipher.DECRYPT_MODE, privada);
		byte [] bytes_texto;
		bytes_texto = desencripta.doFinal(bytes_cifrado);
		return new String (bytes_texto, "UTF-8");
	}
	
	public static String firmar (String texto, PrivateKey privada) throws Exception {
		Signature firmante = Signature.getInstance("SHA256withRSA");
		firmante.initSign(privada);
		firmante.update(texto.getBytes("UTF-8"));
		byte [] firma;
		firma = firmante.sign();
		return Base64.getEncoder().encodeToString(firma);		
	}
	
	public static boolean verificar (String texto_firmado, String firma, PublicKey publica) throws Exception {
		Signature verifica = Signature.getInstance("SHA256withRSA");
		verifica.initVerify(publica);
		verifica.update(texto_firmado.getBytes("UTF-8"));
		byte [] bytes_firma;
		bytes_firma = Base64.getDecoder().decode(firma);
		return verifica.verify(bytes_firma);
	}
	
	public static void main (String[] args) throws Exception {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introduce el mensaje a cifrar:");
		String mensaje = entrada.nextLine();
		entrada.close();
		KeyPair par_claves = generar_par();
		PublicKey publica = par_claves.getPublic();
		PrivateKey privada = par_claves.getPrivate();
		String cifrado = encriptar(mensaje, publica);
		String texto_plano = desencriptar(cifrado, privada);
		System.out.println("Tu mensaje cifrado es:");
		System.out.println(cifrado);
//		System.out.println("Clave pública: " + publica.toString());
		System.out.println("Formato de la clave pública: " + publica.getFormat());
		System.out.println("Algoritmo de la clave pública: " + publica.getAlgorithm());
		System.out.println("Clave privada: " + privada.toString());
		System.out.println("Tu mensaje original es:");
		System.out.println(texto_plano);
		System.out.println("Procediendo a firmar digitalmente...");
		String firma = firmar(mensaje, privada);
		boolean firma_valida = verificar(mensaje, firma, publica);
		if (firma_valida) {
			System.out.println("Firma correcta:");
			System.out.println(firma);
		}else {
			System.out.println("Firma incorrecta");
		}
	}
	
}
