package criptografia;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.security.cert.*;
import java.awt.event.*;
//https://crypto.stackexchange.com/questions/18031/how-to-find-modulus-from-a-rsa-public-key

public class Formulario2 extends JFrame {
	
	Font plana = new Font("Serif", Font.PLAIN, 14);
	Font negrita = new Font("Serif", Font.BOLD, 14);
	JPanel panel = new JPanel();
	JPanel panel_exterior = new JPanel();
	JScrollPane escrol;
	private static int fila = 0;
	private static int filas = 10;
	
	Formulario2(X509Certificate certificado){
		setTitle("Formulario X509");
		setSize(700,800);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Border borde = panel.getBorder(); //Creando el margen
		Border margen = new EmptyBorder(10,10,10,10);
		CompoundBorder borde_compuesto = new CompoundBorder(borde, margen);
		panel.setBorder(borde_compuesto);

//		par_etiquetas("Hola", "Hola caracola");  Las etiquetas se reparten la mitad del espacio horizontal, quedan arriba del todo
		
		GridBagLayout parrilla = new GridBagLayout();
		parrilla.columnWidths = new int[] { 86, 86, 12 };
		parrilla.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		parrilla.rowHeights = new int[filas+1];
			for (int i=0;i<filas;i++) {
				parrilla.rowHeights[i] = 20; //Altura de las filas
			}
		parrilla.rowWeights = new double[filas+1];
			for (int i=0;i<(filas);i++) {
				parrilla.rowWeights[i] = 0.0; //Peso de las filas
			}
		parrilla.rowWeights[filas] = Double.MIN_VALUE; //Peso de la fila de relleno
		panel.setLayout(parrilla);
//		escrol.setHorizontalScrollBar(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		par_etiquetas("Versión: ", "v. " + certificado.getVersion() +"");
		par_etiquetas("Número de serie: ", certificado.getSerialNumber() +"");
		par_etiquetas("Emisor: ", nombre_distinguido(certificado.getIssuerX500Principal() + ""));
		par_etiquetas("Válido desde:", (certificado.getNotBefore()).toString());
		par_etiquetas("Válido hasta:", (certificado.getNotAfter()).toString());
		par_etiquetas("Sujeto:", nombre_distinguido(certificado.getSubjectX500Principal() + ""));
		par_etiquetas("Algoritmo de clave pública: ", certificado.getPublicKey().getAlgorithm());
		//hexadecimal(certificado.getPublicKey() + "");
		//https://docs.microsoft.com/en-us/windows/desktop/seccertenroll/about-object-identifier
		//https://tls.mbed.org/kb/cryptography/asn1-key-structures-in-der-and-pem
		//https://msdn.microsoft.com/en-us/library/ff635835.aspx
		//http://luca.ntop.org/Teaching/Appunti/asn1.html
		//https://www.obj-sys.com/asn1tutorial/asn1only.html
		System.out.println("Clave pública codificada" + certificado.getPublicKey().getEncoded());
		System.out.println("Formato de clave pública" + certificado.getPublicKey().getFormat());
		System.out.println("Algoritmo de clave pública" + certificado.getPublicKey().getAlgorithm());
		System.out.println(hexadecimal(certificado.getPublicKey().getEncoded()));
		par_etiquetas("Clave pública: ", hexadecimal(certificado.getPublicKey().getEncoded()));
		par_etiquetas("Algoritmo de firma: ", certificado.getSigAlgName());
		par_etiquetas("Firma: ", certificado.getSignature().toString());

		escrol = new JScrollPane();
		escrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		escrol.setViewportView(panel);
		
		JLabel titulo = new JLabel("Elementos del certificado");
		titulo.setFont(new Font("Serif", Font.BOLD, 20));
		JPanel superior = new JPanel();
		superior.add(titulo);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new boton_aceptar());
		JPanel inferior = new JPanel();
		inferior.add(aceptar);		
		
		Container contenedor = getContentPane();
		contenedor.add(escrol, BorderLayout.CENTER);
		contenedor.add(inferior,BorderLayout.SOUTH);
		contenedor.add(superior,BorderLayout.NORTH);
		
		setVisible(true);
	}
	
	public class boton_aceptar implements ActionListener {
		
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	public String hexadecimal (byte[] cadena) {
		int longitud = cadena.length;
		System.out.println(longitud + "");
		String hex = "";
		for (int i=0;i<longitud;i++) {
			int numero = cadena[i]& 0xFF;
			String numhex = String.format("%02X", (0xFF & cadena[i])); 
//			hex = hex + Integer.toHexString(numero) + " ";
			hex = hex + numhex + " ";
		}
		return hex;
	}
	
	public String nombre_distinguido(String cadena) {
		cadena.replace(", ", "\n");
		String cadena1 = cadena.replace(", ", "\n");
		return cadena1;
	}
	
	public void par_etiquetas(String etiqueta1, String etiqueta2) {
		JLabel hola1 = new JLabel(etiqueta1);
		hola1.setFont(negrita);
		GridBagConstraints restricciones1 = new GridBagConstraints();
		restricciones1.fill = GridBagConstraints.NONE; //Cambia "NONE" por "BOTH" para centrar verticalmente las etiquetas
		restricciones1.anchor = GridBagConstraints.FIRST_LINE_START;
		restricciones1.gridx = 0;
		restricciones1.gridy = fila;
	
		JTextArea hola2 = new JTextArea(etiqueta2);
		hola2.setLineWrap(true);
		hola2.setWrapStyleWord(true);
		hola2.setOpaque(false);
		hola2.setEditable(false);
		hola2.setCursor(null);
		hola2.setFont(plana);
		GridBagConstraints restricciones2 = new GridBagConstraints();
		restricciones2.fill = GridBagConstraints.BOTH;
		restricciones2.gridx = 1;
		restricciones2.gridy = fila;

		/*		JLabel hola2 = new JLabel(etiqueta2 + fila);
		hola2.setFont(plana);
		GridBagConstraints restricciones2 = new GridBagConstraints();
		restricciones2.fill = GridBagConstraints.BOTH;
		restricciones2.gridx = 1;
		restricciones2.gridy = fila;
		
		jTextArea1.setLineWrap(true);
jTextArea1.setWrapStyleWord(true); 
		*/
			
		panel.add(hola1, restricciones1);
		panel.add(hola2, restricciones2);
		fila++;
	}
}