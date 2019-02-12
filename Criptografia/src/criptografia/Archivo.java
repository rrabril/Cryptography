package criptografia;
import java.io.*;
import javax.swing.*;

import java.util.*;

public class Archivo {
	public static int tabulaciones = 0;
	public static int posicion = 0;
	
	public static void main(String[] args) throws Exception{
		mostrar_archivo();
	} 
	
	public static void numero() throws Exception {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introduce un número en base decimal:\n");
		int decimal = entrada.nextInt();
		System.out.println("Tu número en hexadecimal es:\n" + hexadecimal(decimal));
		entrada.close();
	}
	
	public static void mostrar_archivo() throws Exception {
		File archivoseleccionado;
		String cadenaoriginaria = "";
		String cadenadefinitiva = "";
		JFileChooser selector = new JFileChooser("C:\\Users\\USUARIO\\Desktop\\Ethereum\\Certificados");
		int resultado = selector.showOpenDialog(null);
		if (resultado==0) {
			archivoseleccionado=selector.getSelectedFile();
			FileInputStream archivo = new FileInputStream(archivoseleccionado);
			int octeto = archivo.read();
			while (octeto != (-1)){
				System.out.print((char)octeto);
				cadenaoriginaria = cadenaoriginaria + (char)octeto;
				octeto = archivo.read();
			}
			archivo.close();
			cadenadefinitiva = subcadena(cadenaoriginaria);
//			System.out.print(cadenadefinitiva);
			byte[] bytescadena = cadenadefinitiva.getBytes();
//			System.out.println(bytescadena[0]);
//			System.out.println(bytescadena[(bytescadena.length-1)]);
			byte[] cadenaascii = Base64.getDecoder().decode(bytescadena); //Certificado PEM en cadena ASCII
			for (int i=0;i<cadenaascii.length;i++) {
				System.out.print(Archivo.hexadecimal(cadenaascii[i])+"::");
				if (i%20==0) {
					System.out.println("");
				}
//			String DERdescodificado = descodificador_DER(cadenaascii);
//			System.out.println(DERdescodificado);
			}
			System.out.println("");
			Archivo.descodificador_DER(cadenaascii);
		}else{
			throw new ArchivoNoSeleccionado();
		}
	}
	
	public static void descodificador_DER (byte[] cadena) {
		int posicionlocal=0;
		while(posicionlocal<cadena.length) {
			tabular();
			int tag = positivo(cadena[posicionlocal]);
			posicionlocal++;
			int[] arreglo_longitud = longitud(cadena,posicionlocal);
			int length = arreglo_longitud[0];
			posicionlocal = posicionlocal + 1 + arreglo_longitud[1];
			byte[] nueva_cadena = new byte[length];
			for (int i=0;i<length;i++) {
				nueva_cadena[i] = cadena[posicionlocal+i];
			}
			System.out.print(hexadecimal(tag) + " ");
			if (arreglo_longitud[0]>127) {
				System.out.print(hexadecimal(cadena[posicionlocal - 1 - arreglo_longitud[1]]) + " " + hexadecimal(length) + " ");
			}else {
				System.out.print(hexadecimal(arreglo_longitud[0]) + " ");
				}
			tabulaciones++;
			switch(positivo(tag)) {
			case 1:
				System.out.println("\t\t; BOOLEANO (1 byte)");
				booleano(nueva_cadena,length);
				break;
			case 2:
				System.out.println("\t\t; ENTERO (" + length +" bytes)");
				entero(nueva_cadena,length);
				break;
			case 3:
				System.out.println("\t\t; CADENA DE BITS (" + length +" bytes)");
				cadena_bits(nueva_cadena,length);
				break;
			case 4:
				System.out.println("\t\t; CADENA DE OCTETOS (" + length +" bytes)");
				cadena_octetos(nueva_cadena,length);
				break;
			case 5:
//				tabular();
				System.out.println("\t\t; NULO (0 bytes)");
				break;
			case 6:
				System.out.println("\t\t; IDENTIFICADOR DE OBJETO (" + length + " bytes)");
				identificador_objeto(nueva_cadena,length);
				break;
			case 160:
				System.out.println("\t\t; ENUMERADO (" + length +" bytes)");
				entero(nueva_cadena,length);
				break;
			case 12:
				System.out.println("\t\t; CADENA UTF-8 (" + length +" bytes)");
				cadena_UTF8(nueva_cadena,length);
				break;
			case 19:
				System.out.println("\t\t; IDENTIFICADOR RELATIVO DE OBJETO (" + length + " bytes)");
				entero(nueva_cadena,length);
				break;
			case 23:
				System.out.println("\t\t; TIEMPO UTC (" + length +" bytes)");
				cadena_octetos(nueva_cadena,length);
				break;
	//		case 19: cadena_imprimible;
	//		case 20: teletexto;
	//		case 22: cadena_IA5;
	//		case 30: cadena_BMP;
			case 48:
			case 163:
//				tabulaciones++;
				System.out.println("\t\t; ESTRUCTURA (" + length +" bytes)");
				descodificador_DER(nueva_cadena);
				break;
			case 49:
				System.out.println("\t\t; CONJUNTO (" + length +" bytes)");
				descodificador_DER(nueva_cadena);
				break;
			default:
			System.out.println("Error: " + tag);
			}
			posicionlocal = posicionlocal + length;
			tabulaciones--;
//			System.out.println("Tabulaciones =" + tabulaciones);
		}
	}
	
	/*
	0 	reserved for BER
1 	BOOLEAN
2 	INTEGER
3 	BIT STRING
4 	OCTET STRING
5 	NULL
6 	OBJECT IDENTIFIER
7 	ObjectDescriptor
8 	INSTANCE OF, EXTERNAL
9 	REAL
10 	ENUMERATED
11 	EMBEDDED PDV
12 	UTF8String
13 	RELATIVE-OID
16 	SEQUENCE, SEQUENCE OF
17 	SET, SET OF
18 	NumericString
19 	PrintableString
20 	TeletexString, T61String
21 	VideotexString
22 	IA5String
23 	UTCTime
24 	GeneralizedTime
25 	GraphicString
26 	VisibleString, ISO646String
27 	GeneralString
28 	UniversalString
29 	CHARACTER STRING
30 	BMPString

	*/
	
	public static void booleano(byte[]cadena, int longitud) {
		tabular();
		System.out.println(hexadecimal(cadena[0]));
	}

	public static void entero(byte[]cadena, int longitud) {
		tabular();
		int paso = 1;
		if (cadena[0]==0) {
			System.out.println(hexadecimal(cadena[0]));
			tabular();
			paso = 0;
			longitud--;
		}
		for (int i=1;i<=longitud;i++) {
			System.out.print(hexadecimal(cadena[i-paso]) + " ");
			if (i%8==0) System.out.print(" ");
			if (i%16==0) {
				System.out.print("\n");
				tabular();
			}
		}
		System.out.print("\n");
	}

	public static void cadena_bits(byte[]cadena, int longitud) {
		tabular();
		System.out.println(hexadecimal(cadena[0]));
		posicion++;
		tabular();
		for (int i=1;i<longitud;i++) {
			System.out.print(hexadecimal(cadena[i]) + " ");
			if (i%8==0) System.out.print(" ");
			if (i%16==0) {
				System.out.print("\n");
				tabular();
			}
		}
		System.out.print("\n");
	}

	public static void cadena_octetos(byte[]cadena, int longitud) {
		tabular();
		for (int i=1;i<=longitud;i++) {
			System.out.print(hexadecimal(cadena[i-1]) + " ");
			if (i%8==0) System.out.print(" ");
			if (i%16==0) {
				System.out.print("\n");
				tabular();
			}
		}
		System.out.print("\n");
	}
	
	public static void identificador_objeto(byte[]cadena, int longitud) {
		tabular();
		for (int i=1;i<=longitud;i++) {
			System.out.print(hexadecimal(cadena[i-1]) + " ");
			if (i%8==0) System.out.print(" ");
			if (i%16==0) {
				System.out.print("\n");
				tabular();
			}
		}
		int [] OID = new int[cadena.length];
		System.out.print("\t\t\t\t ");
		OID[0] = (cadena[0])/40;
		OID[1] = (cadena[0])%40;
		System.out.print(OID[0] + "." + OID[1]);
		for (int i=1; i<cadena.length; i++) {
			int num = cadena[i]&(0xFF);
			if((cadena[i]&(0xFF))>127){
				int n = 0;
				List<Integer> numeros = new ArrayList<Integer>();
				numeros.add(cadena[i]&(0xFF));
				i++;
				numeros.add(cadena[i]&(0xFF));
				while((cadena[i]&(0xFF))>127) {
					i++;
					numeros.add(cadena[i]&(0xFF));
				}
				int tamano=numeros.size();
				int [] cadena1 = new int[tamano];
				for (int j=0;j<tamano;j++) {
					cadena1[j]=positivo((int)numeros.get(j));
				}
				num = valor_OID(cadena1);
			}
			System.out.print("." + num);
		}
		System.out.print("\n");
	}
	
	public static int valor_OID(int[] cadena){
		int num = cadena[0]%128;
		for (int i=1;i<cadena.length;i++) {
			num = (num*128) + cadena[i]%128;
		}
		return num;
	}
	
	public static void cadena_UTF8(byte[]cadena, int longitud) {
		tabular();
		for (int i=1;i<=longitud;i++) {
			System.out.print(hexadecimal(cadena[i-1]) + " ");
			if (i%8==0) System.out.print(" ");
			if (i%16==0) {
				System.out.print("\n");
				tabular();
			}
		}
		byte [] nuevacadena = new byte[cadena.length+2];
		for (int i=(cadena.length-1);i>=0;i--) {
			nuevacadena[i+2]=cadena[i];
		}
		nuevacadena[0]=(byte)(longitud/256);
		nuevacadena[1]=(byte)(longitud%256);
		InputStream is = new ByteArrayInputStream(nuevacadena);
		DataInputStream dis = new DataInputStream(is);
		try{
			String UTF = dis.readUTF();
			System.out.print("\t\t\t\t " + UTF);
			
			}
		catch(IOException e) {
			e.printStackTrace();
			System.out.print("\t\t\t\t");
			}
		System.out.print("\n");
	}
	
	public static void tabular() {
		for (int i=0;i<tabulaciones;i++) {
			System.out.print("|  ");
		}
	}
	
	public static int[] longitud (byte[] cadena, int posicionlocal) {
		int l = 0;
		int longitud_longitud=0;
		if ((cadena[posicionlocal]&128)==0) {
			l = cadena[posicionlocal];
		}else {
			longitud_longitud = positivo(cadena[posicionlocal]&127);
			for (int i=1;i<=longitud_longitud;i++) {
				int sumando = (potencia(256,(longitud_longitud-i)))*positivo(cadena[posicionlocal+i]);
				//System.out.print(cadena[posicion+i]);
				l = l + sumando;
			}
		}
		int [] arreglo = new int[2];
		arreglo[0]=l;
		arreglo[1]=longitud_longitud;
		return arreglo;
	}
	
	public static int positivo(int numero) {
		numero = numero&(0xFF);
		return numero;
	}
	
	public static int potencia (int a, int b) {
		int resultado = 1;
		for (int i = 0; i<b; i++) {
			resultado = resultado*a;
		}
		return resultado;
	}
	
	public static String hexadecimal (int decimal) {
		String numhex = String.format("%02X", (0xFF & decimal)); 
		return numhex;
	}
	
	public static String subcadena(String cadena) throws NoEsCertificado {
		if(cadena.contains("-----BEGIN CERTIFICATE-----")) {
			cadena = cadena.substring((cadena.indexOf("-----BEGIN CERTIFICATE-----")+28), cadena.length());
		}else {
			throw new NoEsCertificado();
		}
		if(cadena.contains("-----END CERTIFICATE-----")) {
			cadena = cadena.substring(0, cadena.indexOf("-----END CERTIFICATE-----")-1);
		}else {
			throw new NoEsCertificado();
		}
		String cadena1 = cadena.replaceAll("(\\r|\\n)", "");
		return cadena1;
	}
	
	public static String subcadena_PKCS8(String cadena) throws NoEsCertificado{
		if(cadena.contains("-----BEGIN RSA PRIVATE KEY-----")) {
			cadena = cadena.substring((cadena.indexOf("-----BEGIN RSA PRIVATE KEY-----")+31), cadena.length());
		}else {
			throw new NoEsCertificado();
		}
		if(cadena.contains("-----END RSA PRIVATE KEY-----")) {
			cadena = cadena.substring(0, cadena.indexOf("-----END RSA PRIVATE KEY-----")-1);
		}else {
			throw new NoEsCertificado();
		}
		String cadena1 = cadena.replaceAll("(\\r|\\n)", "");
		return cadena1;
	}
	
}

class ArchivoNoSeleccionado extends Exception{
	ArchivoNoSeleccionado(){
		System.out.println("No has seleccionado ningún archivo");
	}
}

class NoEsCertificado extends Exception{
	NoEsCertificado(){
		System.out.println("El archivo seleccionado no es un certificado válido.");
	}
}