package com.epn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Aplicación {

	public static void main(String[] args) throws IOException {

		File fs = new File("codigoGen.txt");
		BufferedWriter salida;
		salida = new BufferedWriter(new FileWriter(fs));
		GeneracionCod gen = new GeneracionCod();

		String entrada = "3+8-6";

		salida.write(gen.generador(entrada));
		salida.close();

		System.out.println(gen.generador(entrada));

	}

}
