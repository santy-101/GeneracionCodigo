import java.util.ArrayList;
import java.util.StringTokenizer;

public class GenerarCodigo {
	static ArrayList<String> tablaRegistros = new ArrayList<>();
	static int  acc=0;
	static String code="";

	public GenerarCodigo() {
		super();
	}

	public ArrayList<String> getTablaRegistros() {
		return tablaRegistros;
	}

	public void setTablaRegistros(ArrayList<String> tablaRegistros) {
		this.tablaRegistros = tablaRegistros;
	}
	
	public void generadorVariables(String variable)
	{String entrada="";
		
			entrada=variable+"|$r"+acc;
			tablaRegistros.add(entrada);
			code+="store "+variable+" => $r"+acc+"\n";
			acc++;
	}
	public void generadorVector(String tamaño, String variable)
	{String entrada="";
	tamaño.trim();
	int tam=Integer.parseInt(tamaño);
	for(int i=0; i<tam; i++)
	{
			entrada=variable+"["+i+"]"+" | $r"+acc;
			tablaRegistros.add(entrada);
			code+="store "+variable+" => $r"+acc+"\n";
			acc++;
	}
		
	}
	 
	public void generadorMain()
	{String entrada="";
			code+="main_entry:"+"\n";
			System.out.println(entrada);
	}
	public void asignacion(String var,String asig)
	{
		asig.trim();
		int a=-1111111;
		try{
		a=Integer.parseInt(asig);
		}
		catch (Exception e)
		{
			
		}
		if (a!=-1111111 && (a>=0 || a<0))
		{
		code+="loadI "+asig+" => "+buscarRegistro(var)+"\n";
		}
		else
		{
			code+="load "+buscarRegistro(asig)+" => "+buscarRegistro(var)+"\n";
		}
	}
	
	public void operadores(String var1,String var2,String var)
	{
		var.trim();
		if(var.equals("=="))
		{
			code+="igual "+var1+", "+var2+" => "+var+"\n";
		}
		if(var.equals(">="))
		{
			code+="mayorigual "+var1+", "+var2+" => "+var+"\n";
		}
		if(var.equals("<="))
		{
			code+="menorigual "+var1+", "+var2+" => "+var+"\n";
		}
		if(var.equals(">"))
		{
			code+="mayor "+var1+", "+var2+" => "+var+"\n";
		}if(var.equals("<"))
		{
			code+="menor "+var1+", "+var2+" => "+var+"\n";
		}if(var.equals("!="))
		{
			code+="disting "+var1+", "+var2+" => "+var+"\n";
		}
			 
		
	}
	public void operadorBinario(String var1, String var2,  int operador)
	{ 
		if(operador==1)
		{
			code+="add "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";
		}
		if(operador==2)
		{
			code+="sub "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";;
		}
		if(operador==3)
		{
			code+="mult "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";
		}
		if(operador==4)
		{
			code+="div "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";
		}
		
	}
	public void operadorA(int i, String var)
	{
		if(i==0)
		{
			code+="\n";
		}
		else
		{
			code+=var;
		}
	}
	
	public void imprimir()
	{
		System.out.println("Tabla de Registros");
		for(int i=0; i<tablaRegistros.size();i++)
		{
			System.out.println(tablaRegistros.get(i));
		}
		System.out.println("Codigo");
		System.out.println(code);
		
	}
 String buscarRegistro(String variable)
	{
	String registro="";
	String variable1;
	String data;
	StringTokenizer  token ;
	for(int i=0; i<tablaRegistros.size(); i++)
	{
		data=tablaRegistros.get(i);
		data.trim();
		token = new StringTokenizer(data, "|");
		variable1=token.nextToken();
		variable1.trim();
		variable.trim();
		if(variable1.equals(variable))
		{	registro=token.nextToken();
			break;
		}
	}
	return registro;	
	}
}
