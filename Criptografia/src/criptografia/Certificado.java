package criptografia;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import javax.crypto.*;
import java.io.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public class Certificado extends JFrame {
	
	Container contenedor;
	JPanel panelcentral;
	JTextArea texto;
	
	private Certificate[] tryParsePKIPathChain(File chainFile)
	        throws IOException, FileNotFoundException, CertificateException {

	    Certificate[] internalCertificateChain = null;
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");

	    try (FileInputStream inputStream = new FileInputStream(chainFile)) {
	        CertPath certPath = cf.generateCertPath(inputStream);
	        List<? extends Certificate> certList = certPath.getCertificates();
	        internalCertificateChain = certList.toArray(new Certificate[]{});
	    } catch (CertificateException e){
//	        System.out.println("Tried and failed to parse file as a PKI :" + chainFile.getName(), e);
	    }

	    return internalCertificateChain;
	}
	
	Certificado(){
		setSize(600,400);
		setTitle("Lector de certificados electrónicos");
		setLocationRelativeTo(null);
		setVisible(true);
		panelcentral.add(texto);
		contenedor = getContentPane();
		contenedor.add(panelcentral);
		
	}
	
	public static void main(String[] args) {
		Certificado lector = new Certificado();
	}

}
