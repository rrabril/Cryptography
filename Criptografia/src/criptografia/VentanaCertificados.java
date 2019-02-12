package criptografia;

import java.awt.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

import javax.swing.*;
import javax.swing.text.*;

import criptografia.LectorCertificados.ArchivoNoSeleccionado;

public class VentanaCertificados extends JFrame {
	X509Certificate certificado;
	
	VentanaCertificados(){
		try {
			File archivo = this.selector_archivos();
			CertificateFactory fabrica = CertificateFactory.getInstance("X.509");
			FileInputStream fis = new FileInputStream(archivo);
			certificado = (X509Certificate)fabrica.generateCertificate(fis);
			try {
				certificado.checkValidity();
				new Formulario2(certificado);
			}
			catch(CertificateExpiredException e) {
				JOptionPane.showMessageDialog(new JFrame(),"El certificado ha expirado.\n");
			}
			catch(CertificateNotYetValidException e) {
				JOptionPane.showMessageDialog(new JFrame(),"El certificado aún no es válido.\n");
			}
		}
		catch(ArchivoNoSeleccionado e) {
			JOptionPane.showMessageDialog(new JFrame(),"No has seleccionado ningún archivo");
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Archivo no encontrado");
		}
		catch(CertificateException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Certificado no válido");
		}
	}
	
	class GUI_Certificado extends JFrame{
		
		Font plana;
		JPanel superior;
		JPanel inferior;
		GridBagConstraints restricciones;
		
		GUI_Certificado(){
			JLabel algoritmo_emisor;
			Campo algoritmo_emisor1;
			JLabel firma_emisor;
			Campo firma_emisor1;
			JLabel version;
			Campo version1;
			JLabel nserie;
			Campo nserie1;
			JLabel algoritmo_firma;
			Campo algoritmo_firma1;
			JLabel emisor;
			Campo emisor1;
			JLabel validez;
			Campo validez1;
			JLabel algoritmo_clave;
			Campo algoritmo_clave1;
			JLabel clave, firma;
			Campo clave1, firma1;
			JSplitPane lamina;
			Container contenedor;
			JTextPane lamina_texto;
			JScrollPane escrol_texto;
			Font plana;
			Campo algoritmo1;
			setSize(600,400);
			setLocationRelativeTo(null);
			setTitle("Certificado X.509");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			contenedor = getContentPane();
			superior = new JPanel();//Inicio de la configuración de la lámina superior
			superior.setLayout(new GridBagLayout());
			GridBagConstraints restricciones = new GridBagConstraints();
			restricciones.gridwidth = GridBagConstraints.HORIZONTAL;
			restricciones.anchor = GridBagConstraints.WEST;
        	restricciones.weightx = 1.0;
        	superior.setBorder(
        			BorderFactory.createCompoundBorder(
        							BorderFactory.createTitledBorder("Certificado X.509"),
        							BorderFactory.createEmptyBorder(5,5,5,5)));
        	plana = new Font("Serif", Font.PLAIN, 14);
        	version = new JLabel (certificado.getVersion() + "\n");
        	etiqueta_valor("Versión: ", version, restricciones);
        	nserie = new JLabel (certificado.getSerialNumber() + "");
        	etiqueta_valor("Número de serie: ", nserie, restricciones);
        	algoritmo_firma = new JLabel(certificado.getSigAlgName() + "");
        	etiqueta_valor("Algoritmo de firma: ", algoritmo_firma, restricciones);
        	emisor = new JLabel(certificado.getIssuerX500Principal() + "");
        	etiqueta_valor("Emisor: ", emisor, restricciones);
        	superior.add(new JLabel("Validez: "), restricciones);
        	superior.add(new JLabel("Desde: "), restricciones);
        	algoritmo_clave = new JLabel(certificado.getSigAlgName() + "");
        	etiqueta_valor("Algoritmo de la clave: ", algoritmo_clave, restricciones);
        	clave = new JLabel(certificado.getPublicKey() + "");
        	etiqueta_valor("Clave pública: ", clave, restricciones);
        	firma = new JLabel(certificado.getSignature() + "");
        	etiqueta_valor("Firma: ", firma, restricciones);
        	
           	superior.setAutoscrolls(true);
        	inferior = new JPanel();//Inicio de la configuración de la lámina inferior
        	lamina_texto = new JTextPane();
        	escrol_texto = new JScrollPane(lamina_texto);
        	escrol_texto.setVerticalScrollBarPolicy(
                        	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	escrol_texto.setPreferredSize(new Dimension(250, 155));
        	escrol_texto.setMinimumSize(new Dimension(10, 10));
        	inferior.add(escrol_texto);
        	lamina = new JSplitPane(JSplitPane.VERTICAL_SPLIT,superior,inferior);
        	contenedor.add(lamina);
		
        	setVisible(true);
		}
		
		public void etiqueta_valor(String etiqueta, JLabel valor, GridBagConstraints restricciones) {
			valor.setFont(plana);
			restricciones.gridx=0;
			restricciones.gridwidth=2;
			superior.add(new JLabel(etiqueta), restricciones);
			restricciones.gridx=1;
			superior.add(valor, restricciones);
		}
		
		public class Campo extends JTextArea {
			Campo(String texto){
				this.setEditable(false);  
				this.setCursor(null);  
				this.setOpaque(false);  
				this.setFocusable(false);
				this.setLineWrap(true);
				this.setWrapStyleWord(true);
				this.setText(texto);
			}
		}
/*		
		JLabel algoritmo_emisor, tae;
	JLabel firma_emisor, tfe;
	JLabel version, tversion;
	JLabel nserie, tns;
	JLabel algoritmo_firma, taf;
	JLabel emisor, te;
	JLabel validez, tv;
	JLabel algoritmo_clave, tac;
	JLabel clave, tc;	
*/		
	}
	
	public class ArchivoNoSeleccionado extends Exception{
		ArchivoNoSeleccionado(){
			System.out.println("Archivo no seleccionado");
		}		
	}
	
	public File selector_archivos() throws ArchivoNoSeleccionado {
		File archivoseleccionado;
		JFileChooser selector = new JFileChooser("C:\\Users\\USUARIO\\Desktop\\Ethereum\\Certificados");
		int resultado = selector.showOpenDialog(null);
		if (resultado ==0) {
			archivoseleccionado = selector.getSelectedFile();
		}else{
			throw new ArchivoNoSeleccionado();
		}
		return archivoseleccionado;
	}
	
	public static void main(String[] args) {
		VentanaCertificados ventanita = new VentanaCertificados();
	}
}

/*

label = new JLabel("A label");
label.setFont(new Font("Serif", Font.PLAIN, 14));


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              editorScrollPane,
                                              paneScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        JPanel rightPane = new JPanel(new GridLayout(1,0));
        rightPane.add(splitPane);
        rightPane.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Styled Text"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));


Certificate:
   Data:
       Version: 1 (0x0)
       Serial Number: 7829 (0x1e95)
       Signature Algorithm: md5WithRSAEncryption
       Issuer: C=ZA, ST=Western Cape, L=Cape Town, O=Thawte Consulting cc,
               OU=Certification Services Division,
               CN=Thawte Server CA/Email=server-certs@thawte.com
       Validity
           Not Before: Jul  9 16:04:02 1998 GMT
           Not After : Jul  9 16:04:02 1999 GMT
       Subject: C=US, ST=Maryland, L=Pasadena, O=Brent Baccala,
                OU=FreeSoft, CN=www.freesoft.org/Email=baccala@freesoft.org
       Subject Public Key Info:
           Public Key Algorithm: rsaEncryption
           RSA Public Key: (1024 bit)
               Modulus (1024 bit):
                   00:b4:31:98:0a:c4:bc:62:c1:88:aa:dc:b0:c8:bb:
                   33:35:19:d5:0c:64:b9:3d:41:b2:96:fc:f3:31:e1:
                   66:36:d0:8e:56:12:44:ba:75:eb:e8:1c:9c:5b:66:
                   70:33:52:14:c9:ec:4f:91:51:70:39:de:53:85:17:
                   16:94:6e:ee:f4:d5:6f:d5:ca:b3:47:5e:1b:0c:7b:
                   c5:cc:2b:6b:c1:90:c3:16:31:0d:bf:7a:c7:47:77:
                   8f:a0:21:c7:4c:d0:16:65:00:c1:0f:d7:b8:80:e3:
                   d2:75:6b:c1:ea:9e:5c:5c:ea:7d:c1:a1:10:bc:b8:
                   e8:35:1c:9e:27:52:7e:41:8f
               Exponent: 65537 (0x10001)
   Signature Algorithm: md5WithRSAEncryption
       93:5f:8f:5f:c5:af:bf:0a:ab:a5:6d:fb:24:5f:b6:59:5d:9d:
       92:2e:4a:1b:8b:ac:7d:99:17:5d:cd:19:f6:ad:ef:63:2f:92:
       ab:2f:4b:cf:0a:13:90:ee:2c:0e:43:03:be:f6:ea:8e:9c:67:
       d0:a2:40:03:f7:ef:6a:15:09:79:a9:46:ed:b7:16:1b:41:72:
       0d:19:aa:ad:dd:9a:df:ab:97:50:65:f5:5e:85:a6:ef:19:d1:
       5a:de:9d:ea:63:cd:cb:cc:6d:5d:01:85:b5:6d:c8:f3:d9:f7:
       8f:0e:fc:ba:1f:34:e9:96:6e:6c:cf:f2:ef:9b:bf:de:b5:22:
       68:9f

*/