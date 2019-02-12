package criptografia;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.security.cert.*;
import java.security.*;
import javax.crypto.*;
import javax.security.auth.x500.X500Principal;
import java.math.*;
import java.util.*;

public class LectorCertificados extends JFrame implements ActionListener {
	
	JPanel panelcentral;
	Container contenedor;
	JTextArea texto;
	JScrollPane escrol;
	JMenuBar barramenu;
	JMenu archivo;
	JMenuItem seleccionar;
	
	LectorCertificados(){
		setSize(600,400);
		setTitle("Lector de certificados electrónicos");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		texto = new JTextArea(15,50);
		texto.setLineWrap(true);;
		texto.setEditable(false);
		panelcentral=new JPanel(new FlowLayout(FlowLayout.LEADING));
		escrol = new JScrollPane(texto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelcentral.add(escrol);
		contenedor = getContentPane();
		contenedor.add(panelcentral,BorderLayout.CENTER);
		barramenu = new JMenuBar();
		setJMenuBar(barramenu);
		archivo = new JMenu("Archivo");
		barramenu.add(archivo);
		seleccionar = new JMenuItem("Seleccionar Archivo");
		archivo.add(seleccionar);
		
		setVisible(true);
		
	}
	
	public void actionPerformed (ActionEvent e) {
		
	}
	
	public class ArchivoNoSeleccionado extends Exception{
		ArchivoNoSeleccionado(){
			System.out.println("Archivo no seleccionado");
		}		
	}
	
	private File selector_archivos() throws ArchivoNoSeleccionado {
		File archivoseleccionado;
		JFileChooser selector = new JFileChooser("C:/Users/ASES171212/Desktop/certificados");
		int resultado = selector.showOpenDialog(null);
		if (resultado ==0) {
			archivoseleccionado = selector.getSelectedFile();
		}else{
			throw new ArchivoNoSeleccionado();
		}
		return archivoseleccionado;
	}
	
	private void escribir_texto(String cadena) {
		texto.append(cadena);
	}
	
	public static void main(String[] args) throws Exception {
		LectorCertificados lector = new LectorCertificados();
		try{
			File archivo = lector.selector_archivos();
			System.out.println(archivo.getAbsolutePath());
			CertificateFactory fabrica = CertificateFactory.getInstance("X.509");
			FileInputStream fis = new FileInputStream(archivo);
			X509Certificate certificado = (X509Certificate)fabrica.generateCertificate(fis);
			try {
				certificado.checkValidity();
				lector.escribir_texto("El certificado es válido.\n");
			}
			catch(CertificateExpiredException e) {
				lector.escribir_texto("El certificado ha expirado.\n");
			}
			catch(CertificateNotYetValidException e) {
				lector.escribir_texto("El certificado aún no es válido.\n");
			}
			int limites_basicos = certificado.getBasicConstraints(); //Límite de longitud máxima de la cadena de certificados
			lector.escribir_texto("Número máximo de certificados derivados: " + limites_basicos + ".\n");
			byte [] certificado_codificado = certificado.getEncoded(); //Certificado codificado en ASN.1 DER
			int version = certificado.getVersion(); //Version del expedidor
			lector.escribir_texto("Versión: " + version + ".\n");
			X500Principal emisor = certificado.getIssuerX500Principal(); //Nombre distinguido del expedidor
			lector.escribir_texto("Nombre distinguido del emisor del certificado: " + emisor + ".\n");
			BigInteger nserie = certificado.getSerialNumber(); //Número de serie
			lector.escribir_texto("Número de serie: " + nserie + ".\n");
			Date inicio = certificado.getNotBefore(); //Inicio de su validez
			lector.escribir_texto("Válido desde: " + inicio.toString() + ".\n");
			Date caducidad = certificado.getNotAfter();//Caducidad
			lector.escribir_texto("Válido hasta: " + caducidad.toString() + ".\n");
			certificado.getNonCriticalExtensionOIDs();//Extensiones OID
			certificado.getSubjectUniqueID();
			certificado.getSigAlgOID();
			certificado.getIssuerUniqueID();
			PublicKey clave = certificado.getPublicKey(); //Clave pública
			lector.escribir_texto("Clave pública: " + clave.toString() +".\n");
			certificado.getTBSCertificate();//Certificado TBS ("to be signed")
			certificado.getSigAlgName();//Nombre del algoritmo
			certificado.getSignature(); //Firma
		}
		catch(ArchivoNoSeleccionado e){
			System.out.println("Efectivamente, no has seleccionado ningún archivo");
		}
		
		
	}

}

/*

    CertificateFactory fact = CertificateFactory.getInstance("X.509");
    FileInputStream is = new FileInputStream (args[0]);
    X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
    PublicKey key = cer.getPublicKey(); 
 
 
 public class X509Read {
private static String hex(String  binStr) {
    String newStr = new String();
        try {
            String hexStr = "0123456789ABCDEF";
            byte [] p = binStr.getBytes();
            for(int k=0; k < p.length; k++ ){
                int j = ( p[k] >> 4 )&0xF;
                newStr = newStr + hexStr.charAt( j );
                j = p[k]&0xF;
                newStr = newStr + hexStr.charAt( j ) + " ";
            }   
        } catch (Exception e) {
            System.out.println("Failed to convert into hex values: " + e);
        } 
        return newStr;
}
public static void main(String[] args) {
    String CA_Data[]=new String [15];
    String field;
    try{
        InputStream inStream = new FileInputStream("C:\\123.cer");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = 
            (X509Certificate)cf.generateCertificate(inStream);
        field=cert.getType().toString();
        CA_Data[0]=field;
        System.out.println("Type : "+field);
        field=Integer.toString(cert.getVersion());
        CA_Data[1]=field;
        System.out.println("Version : "+field);
        field=cert.getSubjectX500Principal().toString();
        CA_Data[2]=field;
        System.out.println("Name : "+field);
        field=cert.getSerialNumber().toString(16);
        CA_Data[3]=field;
        System.out.println("SerialNumber : "+field);
        field=cert.getSubjectAlternativeNames().toString();
        CA_Data[4]=field;
        System.out.println("SubjectAlternativeNames : "+field);
        field=cert.getNotBefore().toString();
        CA_Data[5]=field;
        System.out.println("NotBefore : "+field);
        field=cert.getNotAfter().toString();
        CA_Data[6]=field;
        System.out.println("NotAfter : "+field); 
        field=cert.getIssuerX500Principal().toString();
        CA_Data[7]=field;
        System.out.println("IssuerDN : "+field);
        field=cert.getSigAlgName().toString();
        CA_Data[8]=field;
        System.out.println("SigAlgName : "+field);
        byte [] tempPub = null;
        String sPub = null;
        RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
        tempPub = pubkey.getEncoded();
        sPub = new String( tempPub );
        field=cert.getPublicKey().getAlgorithm();
        CA_Data[9]=field;
        System.out.println("Public Key Algorithm : " + field);
        field=hex(sPub);
        CA_Data[10]=field;
        System.out.println("Public key : \n" + field );        
        inStream.close();   
        }catch(Exception exception){ 
            exception.printStackTrace();
        }
    }
}
 
 
 
  CertificateFactory cf = null;
 X509CRL crl = null;
 try (InputStream inStrm = new FileInputStream("DER-encoded-CRL")) {
     cf = CertificateFactory.getInstance("X.509");
     crl = (X509CRL)cf.generateCRL(inStrm);
 }

 byte[] certData = <DER-encoded certificate data>
 ByteArrayInputStream bais = new ByteArrayInputStream(certData);
 X509Certificate cert = (X509Certificate)cf.generateCertificate(bais);
 X509CRLEntry badCert =
              crl.getRevokedCertificate(cert.getSerialNumber());

 if (badCert != null) {
     Set<String> nonCritSet = badCert.getNonCriticalExtensionOIDs();
     if (nonCritSet != null)
         for (String oid : nonCritSet) {
             System.out.println(oid);
         }
 }
 
 
 */
