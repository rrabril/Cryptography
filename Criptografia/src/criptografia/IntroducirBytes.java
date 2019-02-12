package criptografia;
import java.util.*;

public class IntroducirBytes {
	
	public static int tabulaciones = 0;
	public static int posicion = 0;
	
	public static void main(String[] args) {
		Scanner entrada = new Scanner (System.in);
		System.out.println("Introduce varios bytes en formato hexadecimal:");
		String cadena = entrada.nextLine();
		Scanner cadena1 = new Scanner (cadena);
		cadena1.useDelimiter(" ");
		List<Integer> bytescertificado = new ArrayList<>();
		int i = 0;
		while (cadena1.hasNextInt(16)) {
			int numero = cadena1.nextInt(16);
/*			if (numero<0) {
				numero = -numero;
				numero = ~(numero-1);
			}*/
			bytescertificado.add(numero);
		    i++;
		    }
		int tamano = bytescertificado.size();
		byte bytecertificado[] = new byte[tamano];
		for (i=0;i<tamano;i++) {
			int num = (int)bytescertificado.get(i);
			bytecertificado[i] = (byte)num;
//			System.out.println(bytecertificado[i] + "");
		}
		System.out.print("\n");
		Archivo.descodificador_DER(bytecertificado);
		cadena1.close();
		entrada.close();
	}
}
