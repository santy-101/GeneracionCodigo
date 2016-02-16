import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Enumeration;

class TokenAsignaciones {
	// Variable para validar asignaciones a caracteres(ichr)
	public static int segunda = 0;
	public static int linea = 0;
	// Tabla que almacenara los tokens declarados
	// private static Hashtable tabla = new Hashtable();

	// Listas que guardaran los tipos compatibles de las variables
	private static ArrayList<Integer> intComp = new ArrayList();
	private static ArrayList<Integer> decComp = new ArrayList();
	private static ArrayList<Integer> strComp = new ArrayList();
	private static ArrayList<Integer> chrComp = new ArrayList();
	private static ArrayList<Integer> boolComp = new ArrayList();
	private static ArrayList<Funcion> tablaFuncion = new ArrayList();
	private static ArrayList<Token> tablaTokens = new ArrayList();

	public static void insertarFuncion(Token identificador) {
		Funcion e = new Funcion();
		e.setIdentificador(identificador.image);
		e.setTipoDato(identificador.kind);
		tablaFuncion.add(e);
	}

	public static void insertarParametros(Token funcion, Token variable) {
		int j = -1;
		Funcion nueva = new Funcion();
		for (Funcion x : tablaFuncion) {
			j++;
			if (x.getIdentificador().equals(funcion.image)) {
				break;
			}
		}
		if (j >= 0) {
			nueva = tablaFuncion.get(j);
			ArrayList<Token> parametros = new ArrayList<>();
			parametros = nueva.getParametros();
			if (parametros.isEmpty()) {
				nueva.setParametros(variable);
				tablaTokens.add(variable);
			} else {
				for (int i = 0; i < parametros.size(); i++) {

					if (parametros.get(i).image.equals(variable.image)) {
						if(parametros.get(i).beginLine!=funcion.beginLine)
						{
						System.out.println("Error Semántico: Linea: " + funcion.beginLine + " El parametro \" "
								+ variable.image + "\" ya ha sido declarado.");
						}
						i = parametros.size();
					} else {
						nueva.setParametros(variable);
						tablaTokens.add(variable);
					}
				}
			}

		}

	}

	public static void verificarFuncion1(Token identificador) {
		Funcion e = new Funcion();
		boolean aux = false;
		e.setIdentificador(identificador.image);
		e.setTipoDato(identificador.kind);
		for (Funcion x : tablaFuncion) {
			if (x.getIdentificador().equals(e.getIdentificador())) {
				aux = true;
				break;
			}
		}
		if (aux == false) {
			System.out.println("Error Semántico: Linea: " + identificador.beginLine + " La funcion \" "
					+ identificador.image + "\" no ha sido declarada");
		}
	}

	public static void verificarFuncion2(Token funcion, Token variable) {
		int j = -1;
		boolean aux = false;
		Funcion nueva = new Funcion();
		ArrayList<Token> parametros = new ArrayList();
		for (Funcion x : tablaFuncion) {
			j++;

			if (x.getIdentificador().equals(funcion.image)) {
				break;
			}
		}
		if (j >= 0) {
			nueva = tablaFuncion.get(j);
			parametros = nueva.getParametros();
			System.out.println(variable.image);
			for (Token y : parametros) {
				System.out.println(y.image);
				if (y.image.equals(variable.image) && (y.kind == variable.kind)) {
					aux = true;
				} else {
					aux = false;
					System.out.println("Error Semántico: Linea: " + funcion.beginLine + " La funcion \" "
							+ funcion.image + "\" no tiene los mismos argumentos que la declaracion"+variable.image+y.image);
					break;
				}

			}

		} else {
			System.out.println("Error Semántico: Linea: " + funcion.beginLine + " La funcion \" " + funcion.image
					+ "\" no ha sido declarada");

		}
	}

	// Inserto de variables globales variable //tipoDato
	public static void insertarSimbolo(Token identificador) {
		boolean aux = false;
		for (Token x : tablaTokens) {
			if (x.image.equals(identificador.image)) {
				aux = true;
				break;
			}
		}
		if (aux) { // verifica ya ya existe o no el simbolo a ingresar

			System.out.println("Error Semántico: Linea: " + identificador.beginLine + " El identificador \" "
					+ identificador.image + "\" ya ha sido declarado");
		} else {
			// En este metodo se agrega a la tabla de tokens el identificador
			// que esta siendo declarado junto con su tipo de dato
			// tabla.put(identificador.image, tipo);
			tablaTokens.add(identificador);
		}
	}

	public static void SetTables() {
		/*
		 * En este metodo se inicializan las tablas, las cuales almacenaran los
		 * tipo de datos compatibles con: entero = intComp decimal = decComp
		 * cadena = strComp caracter = chrComp boolean = boolComp
		 */

		// Compatibles con int = int, float, bool
		intComp.add(40); // int
		intComp.add(41); // float
		intComp.add(44); // bool
		intComp.add(45); // int
		intComp.add(48); // float
		intComp.add(46); // bool

		// Compatibles con float = int, float, bool

		decComp.add(40); // int
		decComp.add(41); // float
		decComp.add(44); // bool
		decComp.add(45); // int
		decComp.add(48); // float
		decComp.add(46); // bool

		// Compatibles con char = char
		chrComp.add(42); // char
		chrComp.add(50); // char

		// Compatibles con string = string
		strComp.add(43); // string
		strComp.add(49); // string

		// Compatibles con bool = bool, int, float
		boolComp.add(44); // bool
		boolComp.add(40); // int
		boolComp.add(41); // float
		boolComp.add(46); // bool
		boolComp.add(45); // int
		boolComp.add(48); // float
	}

	public static String checkAsing(Token TokenIzq, Token TokenAsig) {
		// variables en las cuales se almacenara el tipo de dato del
		// identificador y de las asignaciones (ejemplo: n1(tipoIdent1) =
		// 2(tipoIdent2) + 3(tipoIdent2))
		int tipoIdent1;
		int tipoIdent2;
		/*
		 * De la tabla obtenemos el tipo de dato del identificador asi como, si
		 * el token enviado es diferente a algun tipo que no se declara como los
		 * numeros(48), los decimales(50), caracteres(52) y cadenas(51) entonces
		 * tipoIdent1 = tipo_de_dato, ya que TokenAsig es un dato
		 */
		if (TokenIzq.kind != 45 && TokenIzq.kind != 48) {
			int aux = -1;
			boolean sw=false;
		
				// Si el TokenIzq.image existe dentro de la tabla de tokens,
				// entonces tipoIdent1 toma el tipo de dato con el que
				// TokenIzq.image fue declarado
				// tipoIdent1 = (Integer) tabla.get(TokenIzq.image);
				for (Token a : tablaTokens) {
					aux++;
					if (a.image.equals(TokenIzq.image) ) {
						sw=true;
						break;
					}
				}
				// Si TokenIzq.image no se encuentra en la tabla en la cual se
				// agregan los tokens, el token no ha sido declarado, y se manda
				// un error
				if(sw)
				{
					tipoIdent1 = (tablaTokens.get(aux)).kind;
				}
				else
				{
				return "Error Semántico: Linea: " + TokenIzq.beginLine + " El identificador \"" + TokenIzq.image
						+ "\" no ha sido declarado";
				}
			
		} else
			tipoIdent1 = 0;

		// TokenAsig.kind != 48 && TokenAsig.kind != 50 && TokenAsig.kind != 51
		// && TokenAsig.kind != 52
		if (TokenAsig.kind == 47) {
			int aux = -1;
			boolean sw=false;
			
			/*
			 * Si el tipo de dato que se esta asignando, es algun
			 * identificador(kind == 46) se obtiene su tipo de la tabla de
			 * tokens para poder hacer las comparaciones
			 */
			for (Token a : tablaTokens) {
				aux++;
				if (a.image.equals(TokenAsig.image) ) {
					sw=true;
					break;
				}
			}
				
			if(sw)
			{
				tipoIdent2 = (tablaTokens.get(aux)).kind;
			}
			else
			{
				return "Error Semántico: Linea: " + TokenAsig.beginLine + " El identificador " + TokenAsig.image
						+ " no ha sido declarado ";
			}
			

			
		}
		// Si el dato es entero(45) o decimal(47) o caracter(51) o cadena(52) o
		// bool (51)
		// tipoIdent2 = tipo_del_dato
		else if (TokenAsig.kind == 45 || TokenAsig.kind == 48 || TokenAsig.kind == 49 || TokenAsig.kind == 50
				|| TokenAsig.kind == 46)
			tipoIdent2 = TokenAsig.kind;
		else // Si no, se inicializa en algun valor "sin significado(con
				// respecto a los tokens)", para que la variable este
				// inicializada y no marque error al comparar
			tipoIdent2 = 0;

		if (tipoIdent1 == 40) // Int
		{
			// Si la lista de enteros(intComp) contiene el valor de tipoIdent2,
			// entonces es compatible y se puede hacer la asignacion
			if (intComp.contains(tipoIdent2))
				return " ";
			else // Si el tipo de dato no es compatible manda el error
				return "Error Semántico : No se puede convertir " + TokenAsig.image + " a Entero \r\nLinea: "
						+ TokenIzq.beginLine;
		} else if (tipoIdent1 == 41) // double
		{
			if (decComp.contains(tipoIdent2))
				return " ";
			else
				return "Error Semántico : No se puede convertir " + TokenAsig.image + " a Decimal \r\nLinea: "
						+ TokenIzq.beginLine;
		} else if (tipoIdent1 == 42) // char
		{
			/*
			 * variable segunda: cuenta cuantos datos se van a asignar al
			 * caracter: si a el caracter se le asigna mas de un dato (ej: 'a' +
			 * 'b') marca error NOTA: no se utiliza un booleano ya que entraria
			 * en asignaciones pares o impares
			 */
			segunda++;
			if (segunda < 2) {
				if (chrComp.contains(tipoIdent2))
					return " ";
				else
					return "Error Semántico : No se puede convertir " + TokenAsig.image + " a Caracter \r\nLinea: "
							+ TokenIzq.beginLine;
			} else // Si se esta asignando mas de un caracter manda el error
				return "Error: No se puede asignar mas de un valor a un caracter \r\nLinea: " + TokenIzq.beginLine;

		} else if (tipoIdent1 == 43) // string
		{
			if (strComp.contains(tipoIdent2))
				return " ";
			else
				return "Error Semántico: Linea: " + TokenIzq.beginLine + "No se puede convertir " + TokenAsig.image
						+ " a Cadena";
		} else if (tipoIdent1 == 44) // bool
		{
			if (boolComp.contains(tipoIdent2))
				return " ";
			else
				return "Error Semántico : Linea: " + TokenIzq.beginLine + "No se puede convertir " + TokenAsig.image
						+ " a Booleano ";
		} else {
			return "El Identificador " + TokenIzq.image + " no ha sido declarado" + " Linea: " + TokenIzq.beginLine;
		}
	}

	/*
	 * Metodo que verifica si un identificador ha sido declarado, ej cuando se
	 * declaran las asignaciones: i++, i--)
	 */
	public static String checkVariable(Token checkTok) {
		int aux = 0;
		try {
			for (Token a : tablaTokens) {
				aux++;
				if (a.image.equals(checkTok.image) && a.kind == checkTok.kind) {
					break;
				}

			}

			int tipoIdent1 = (tablaTokens.get(aux)).kind;

			// Intenta obtener el token a verificar(checkTok) de la tabla de los
			// tokens
			// int tipoIdent1 = (Integer) tabla.get(checkTok.image);
			return " ";
		} catch (Exception e) {
			// Si no lo puede obtener, manda el error
			return "Error: Linea:" + checkTok.beginLine + " El identificador " + checkTok.image
					+ " no ha sido declarado";
		}
	}

	// Validacion del Retorno
	public static void validarRetornos(Token funcion, Token retorno) {
		boolean aux = false;
		ArrayList<Token> parametros = new ArrayList<>();
		ArrayList<Token> varibles = new ArrayList<>();
		for (Funcion x : tablaFuncion) {
			if (x.getIdentificador().equals(funcion.image)) {
				parametros = x.getParametros();
				varibles = x.getVariables();
				aux = true;
				break;
			}
		}
		if (aux) {
			for (int i = 0; i < parametros.size(); i++) {
				if (parametros.get(i).image.equals(retorno.image)) {
					aux = false;
					if (parametros.get(i).kind != funcion.kind) {
						System.out.println("Error semántico: Linea: " + retorno.beginLine + " La funcion \""
								+ funcion.image + "\" retorna tipo " + tipos(funcion.kind));
					}
					i = parametros.size();
				}

			}

			if (aux) {
				for (int i = 0; i < varibles.size(); i++) {
					if (varibles.get(i).image.equals(retorno.image)) {
						aux = false;
						if (varibles.get(i).kind != funcion.kind) {
							System.out.println("Error semántico: Linea: " + retorno.beginLine + " La funcion \""
									+ funcion.image + "\" retorna tipo " + tipos(funcion.kind));
						}
						i = varibles.size();
					}

				}
			}
			if (aux) {
				for (int i = 0; i < tablaTokens.size(); i++) {
					if (tablaTokens.get(i).image.equals(retorno.image)) {
						aux = false;
						if (tablaTokens.get(i).kind != funcion.kind) {
							System.out.println("Error semántico: Linea: " + retorno.beginLine + " La funcion \""
									+ funcion.image + "\" retorna tipo " + tipos(funcion.kind));
						}
						i = tablaTokens.size();
					}

				}
			}

		} else {
			System.out.println("Error semántico: Linea: " + retorno.beginLine + " La funcion \"" + funcion.image
					+ "\" no ha sido declarada");

		}

	}

	public static void insertarRetorno(Token funcion, Token retorno) {

		Funcion e = new Funcion();
		boolean aux = false;
		e.setIdentificador(funcion.image);
		e.setTipoDato(funcion.kind);
		for (Funcion x : tablaFuncion) {
			if (x.getIdentificador().equals(e.getIdentificador())) {
				e = x;
				break;
			}
		}
		e.setRetorno(retorno);

	}

	public static void insertarVariableLocal(Token funcion, Token var) {
		ArrayList<Token> variables = new ArrayList<>();
		for (Funcion x : tablaFuncion) {
			if (x.getIdentificador().equals(funcion.image)) {
				variables = x.getVariables();
				break;
			}
		}
		if (variables.isEmpty()) {
			variables.add(var);
			tablaTokens.add(var);
		} else {
			for (int i = 0; i < variables.size(); i++) 
			{
					if (variables.get(i).image.equals(var.image) ) {
						if(variables.get(i).beginLine!=var.beginLine)
						{
						System.out.println("Error Semántico: Linea: " + var.beginLine + " El identificador \" "
								+ var.image + "\" ya ha sido declarado");
						}
						i = variables.size();
						
					} else {
						variables.add(var);
						tablaTokens.add(var);
						i = variables.size();
					}
			}
			
		}

	}

	// visualizacion de tabla de simbolos
	public static void visualizarTablas() {
		System.out.printf("\n%10s%6s%5s%6s", "TABLA DE SIMBOLOS\n", "NOMBRE", " |", "TIPO\n");
		/*
		 * Enumeration e = tabla.keys(); Object obj; while (e.hasMoreElements())
		 * { obj = e.nextElement(); System.out.printf("%6s%5s%6s", obj, " :",
		 * tipo(obj) + "\n"); }
		 */

		for (Token a : tablaTokens) {
			System.out.printf("%6s%5s%6s", a.image, " :", tipos(a.kind) + "\n");
		}
	}

	public static void visualizarTablasFunciones() {
		System.out.printf("\n%8s%5s%3s%6s", "TABLA DE FUNCIONES\n", "NOMBRE", " |", "TIPO\n");
		for (Funcion x : tablaFuncion) {
			System.out.printf(x.toString());
		}

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
		return nombre;

	}

}
