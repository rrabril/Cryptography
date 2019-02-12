package criptografia;

import java.security.*;
import javax.crypto.*;
import java.util.*;

public class claves {
	
	public static void main(String[] args) {
		try {
			String algoritmo = "AES";
			int longitud = 192;
			SecretKey clave = clave_simetrica(algoritmo, longitud);
			String clavecodificada = Base64.getEncoder().encodeToString(clave.getEncoded());
			System.out.println("Aquí tienes tu clave " + algoritmo + ", de " + longitud + " bits de longitud:\n\n" 
			+ clavecodificada);
		}
		catch(NoSuchAlgorithmException e) {
			System.out.println("Algoritmo desconocido");
		}
	}
	
	public static SecretKey clave_simetrica (String algoritmo, int longitud) throws NoSuchAlgorithmException {
		KeyGenerator generador;
		SecretKey clave;
		generador = KeyGenerator.getInstance(algoritmo);
		SecureRandom aleatorio = new SecureRandom();
		generador.init(longitud, aleatorio);
		clave = generador.generateKey();
		return clave;
	}

}

/* CREAMOS UNA CLAVE AES, LA ENCRIPTAMOS Y LA DESENCRIPTAMOS
  
 public static SecretKey generateAESKey(int keysize)
        throws InvalidParameterException {
    try {
        if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
            // this may be an issue if unlimited crypto is not installed
            throw new InvalidParameterException("Key size of " + keysize
                    + " not supported in this runtime");
        }

        final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize);
        return keyGen.generateKey();
    } catch (final NoSuchAlgorithmException e) {
        // AES functionality is a requirement for any Java SE runtime
        throw new IllegalStateException(
                "AES should always be present in a Java SE runtime", e);
    }
}

public static SecretKey decodeBase64ToAESKey(final String encodedKey)
        throws IllegalArgumentException {
    try {
        // throws IllegalArgumentException - if src is not in valid Base64
        // scheme
        final byte[] keyData = Base64.getDecoder().decode(encodedKey);
        final int keysize = keyData.length * Byte.SIZE;

        // this should be checked by a SecretKeyFactory, but that doesn't exist for AES
        switch (keysize) {
        case 128:
        case 192:
        case 256:
            break;
        default:
            throw new IllegalArgumentException("Invalid key size for AES: " + keysize);
        }

        if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
            // this may be an issue if unlimited crypto is not installed
            throw new IllegalArgumentException("Key size of " + keysize
                    + " not supported in this runtime");
        }

        // throws IllegalArgumentException - if key is empty
        final SecretKeySpec aesKey = new SecretKeySpec(keyData, "AES");
        return aesKey;
    } catch (final NoSuchAlgorithmException e) {
        // AES functionality is a requirement for any Java SE runtime
        throw new IllegalStateException(
                "AES should always be present in a Java SE runtime", e);
    }
}

public static String encodeAESKeyToBase64(final SecretKey aesKey)
        throws IllegalArgumentException {
    if (!aesKey.getAlgorithm().equalsIgnoreCase("AES")) {
        throw new IllegalArgumentException("Not an AES key");
    }

    final byte[] keyData = aesKey.getEncoded();
    final String encodedKey = Base64.getEncoder().encodeToString(keyData);
    return encodedKey;
} 
  

CLAVE RSA

 
 
 // generate key pair

KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
keyPairGenerator.initialize(1024);
KeyPair keyPair = keyPairGenerator.genKeyPair();

// extract the encoded private key, this is an unencrypted PKCS#8 private key
byte[] encodedprivkey = keyPair.getPrivate().getEncoded();

// We must use a PasswordBasedEncryption algorithm in order to encrypt the private key, you may use any common algorithm supported by openssl, you can check them in the openssl documentation http://www.openssl.org/docs/apps/pkcs8.html
String MYPBEALG = "PBEWithSHA1AndDESede";
String password = "pleaseChangeit!";

int count = 20;// hash iteration count
SecureRandom random = new SecureRandom();
byte[] salt = new byte[8];
random.nextBytes(salt);

// Create PBE parameter set
PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
SecretKeyFactory keyFac = SecretKeyFactory.getInstance(MYPBEALG);
SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

Cipher pbeCipher = Cipher.getInstance(MYPBEALG);

// Initialize PBE Cipher with key and parameters
pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

// Encrypt the encoded Private Key with the PBE key
byte[] ciphertext = pbeCipher.doFinal(encodedprivkey);

// Now construct  PKCS #8 EncryptedPrivateKeyInfo object
AlgorithmParameters algparms = AlgorithmParameters.getInstance(MYPBEALG);
algparms.init(pbeParamSpec);
EncryptedPrivateKeyInfo encinfo = new EncryptedPrivateKeyInfo(algparms, ciphertext);

// and here we have it! a DER encoded PKCS#8 encrypted key!
byte[] encryptedPkcs8 = encinfo.getEncoded();
 
 
 
 
 
 OTRO EJEMPLO DE RSA
 
 https://gist.github.com/nielsutrecht/855f3bef0cf559d8d23e94e2aecd4ede
 https://solidity-es.readthedocs.io/es/latest/introduction-to-smart-contracts.html
 https://www.programcreek.com/java-api-examples/?api=java.security.cert.Certificate
 
 import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RsaExample {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    public static KeyPair getKeyPairFromKeyStore() throws Exception {
        //Generated with:
        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks

        InputStream ins = RsaExample.class.getResourceAsStream("/keystore.jks");

        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword =       //Key password
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    public static void main(String... argv) throws Exception {
        //First generate a public/private key pair
        KeyPair pair = generateKeyPair();
        //KeyPair pair = getKeyPairFromKeyStore();

        //Our secret message
        String message = "the answer to life the universe and everything";

        //Encrypt the message
        String cipherText = encrypt(message, pair.getPublic());

        //Now decrypt it
        String decipheredMessage = decrypt(cipherText, pair.getPrivate());

        System.out.println(decipheredMessage);

        //Let's sign our message
        String signature = sign("foobar", pair.getPrivate());

        //Let's check the signature
        boolean isCorrect = verify("foobar", signature, pair.getPublic());
        System.out.println("Signature correct: " + isCorrect);
    }
}
 
 
 
 
 
 */