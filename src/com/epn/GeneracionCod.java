package com.epn;

public class GeneracionCod {

	char[] expresion;
	char[] numeros = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	char[] simbolos;

	public String generador(String entrada) {
		int contador = 0;
		String salida = "";
		entrada.trim();
		expresion = entrada.toCharArray();
		simbolos = new char[expresion.length - 1];

		for (int i = 0; i < entrada.length(); i++) {
			if (esNumero(expresion[i])) {
				salida += "\nacc <- " + expresion[i];

				if (i != expresion.length - 1) {
					salida += "\npush acc";
				}
			} else {
				simbolos[contador] = expresion[i];
				contador++;
			}

		}

		for (int j = contador - 1; j >= 0; j--) {
			salida += "\nacc <- acc " + simbolos[j] + " top_of_stack";
			salida += "\npop";
		}
		return salida;
	}

	public boolean esNumero(char a) {
		boolean resultado = false;
		for (int i = 0; i < numeros.length; i++) {
			if (a == numeros[i]) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	public void mostrarResultados()
	{
		
	}

}
