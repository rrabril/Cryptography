package criptografia;
import java.util.*;

public class Binario {
	
	public static void main(String[] args) {
		positivizar();
		/*
		Scanner entrada = new Scanner (System.in);
		System.out.println("Introduce un número decimal:");
		int numero = entrada.nextInt();
		System.out.println("¿Cuál de sus dígitos binarios quieres adivinar?");
		int digito = entrada.nextInt();
		int b = Archivo.potencia(2,digito);
		boolean a = (numero&b)>0;
		System.out.println(a);
		entrada.close();*/
	}
	
	public static void positivizar() {
		Scanner entrada = new Scanner (System.in);
		System.out.println("Introduce un número en hexadecimal:");
		int numero = entrada.nextInt(16);
		byte hola = (byte)numero;
		System.out.println("El número que has introducido es: " + hola);
		int prueba = (int)hola;
		System.out.println("El número, una vez procesado, es: " + positivo(hola));
	}
	
	public static int positivo(int numero) {
		numero = numero&(0xff);
		return numero;
	}

}
