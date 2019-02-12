package criptografia;
import java.util.Base64;

public class Base_64 {
	static String cadenabase64 = "MIIJKQIBAAKCAgEA3W29+ID6194bH6ejLrIC4hb2Ugo8v6ZC+Mrck2dNYMNPjcOK";
	
	public static void main(String[] args) {
		byte[] hola = Base64.getDecoder().decode(cadenabase64);
		int longitud = hola.length;
		System.out.println(longitud);
		for (int i=0;i<longitud;i++) {
			String hex = Archivo.hexadecimal((int)hola[i]);
			System.out.print(hex + "::");
		}
	}

}
