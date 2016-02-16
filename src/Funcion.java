import java.util.ArrayList;

public class Funcion {
	private int tipoDato;
	private String identificador;
	private ArrayList<Token> parametros = new ArrayList();
	private ArrayList<Token> variables = new ArrayList();
	private Token retorno;

	public Funcion() {
		super();

		this.retorno = null;
	}

	public Token getRetorno() {
		return retorno;
	}

	public void setRetorno(Token retorno) {
		this.retorno = retorno;
	}

	public int getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(int tipoDato) {
		this.tipoDato = tipoDato;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public ArrayList<Token> getParametros() {
		return parametros;
	}

	public void setParametros(Token parametros) {
		this.parametros.add(parametros);
	}

	public ArrayList<Token> getVariables() {
		return parametros;
	}

	public void setVariables(Token parametros) {
		this.variables.add(parametros);
	}

	public String toString() {
		String salida = "\n";
		try {
			String a = retorno.image;

			salida += tipos(tipoDato) + "\t: " + identificador + " ( " + salidaParametros() + " )" + " { "
					+ salidaDeclaraciones() + "\n\t retun " + retorno.image + ";" + " }";
		} catch (Exception e) {
			salida += tipos(tipoDato) + "\t: " + identificador + " ( " + salidaParametros() + " )" + " { "
					+ salidaDeclaraciones() +  " }";
		}

		return salida;
	}

	public String salidaParametros() {
		String salida = "";

		for (Token x : parametros) {
			salida += tipos(x.kind) + ":" + x.image + ",";
		}
		return salida;
	}

	public String salidaDeclaraciones() {
		String salida = "";

		for (Token x : variables) {
			salida += tipos(x.kind)+ ":" + x.image +"; ";
		}
		return salida;
	}

	static String tipos(int o) {
		String nombre = "";
		if (o == 40) {
			nombre = "int";
		}
		if (o == 41) {
			nombre = "float";
		}
		if (o == 42) {
			nombre = "char";
		}
		if (o == 43) {
			nombre = "string";
		}
		if (o == 44) {
			nombre = "bool";
		}
		if (o == 1) {
			nombre = "void";
		}
		return nombre;

	}

}
