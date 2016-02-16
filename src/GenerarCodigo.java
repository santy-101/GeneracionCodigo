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
			code+="\nmain_entry:"+"\n";
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
		int a=-1111111,b=-111111;
		boolean c=false,d=false;
		int x=0;
		try{
			a=Integer.parseInt(var1);
			c=true;
			}
			catch (Exception e)
			{
				
			}
		try{
		b=Integer.parseInt(var2);
		d=true;
		}
		catch (Exception e)
		{
			
		}
		
		if(c==true && d==true)//var1 es numero var2 es numero
		{	x=1;
		}else
			if(c==false && d==true)// var1 es registro var2 es numero
			{
				x=2;
			}else
				if(c==true && d==false)// var1 es numero var2 es registro
				{
					x=3;
				}else
					if(c==false && d==false)// var1 es registro var2 es registro
					{
						x=4;
					}
		if(var.equals("=="))
		{
			if(x==1)
			{
			code+="eq "+var1+" "+var2+"\n";}
			if(x==2)
			{
			code+="eq "+buscarRegistro(var1)+" "+var2+"\n";}
			if(x==3)
			{
			code+="eq "+var1+" "+buscarRegistro(var2)+"\n";}
			if(x==4)
			{
			code+="eq "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}
		if(var.equals(">="))
		{
			if(x==1)
			{
			code+="egt "+var1+" "+var2+"\n";}
			if(x==2)
			{
			code+="egt "+buscarRegistro(var1)+" "+var2+"\n";}
			if(x==3)
			{
			code+="egt "+var1+" "+buscarRegistro(var2)+"\n";}
			if(x==4)
			{code+="egt "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}
		if(var.equals("<="))
		{
			if(x==1)
			{
			code+="elt "+var1+" "+var2+"\n";}
			if(x==2)
			{
			code+="elt "+buscarRegistro(var1)+" "+var2+"\n";}
			if(x==3)
			{
			code+="elt "+var1+" "+buscarRegistro(var2)+"\n";}
			if(x==4)
			{
			code+="elt "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}
		if(var.equals(">"))
		{
			if(x==1)
			{
			code+="gt "+var1+" "+var2+"\n";}
			if(x==2)
			{
			code+="gt "+buscarRegistro(var1)+" "+var2+"\n";}
			if(x==3)
			{
			code+="gt "+var1+" "+buscarRegistro(var2)+"\n";}
			if(x==4)
			{
			code+="gt "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}if(var.equals("<"))
		{if(x==1)
		{
		code+="lt "+var1+" "+var2+"\n";}
		if(x==2)
		{
		code+="lt "+buscarRegistro(var1)+" "+var2+"\n";}
		if(x==3)
		{
		code+="lt "+var1+" "+buscarRegistro(var2)+"\n";}
		if(x==4)
		{
			code+="lt "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}if(var.equals("!="))
		{if(x==1)
		{
		code+="distinct "+var1+" "+var2+"\n";}
		if(x==2)
		{
		code+="distinct "+buscarRegistro(var1)+" "+var2+"\n";}
		if(x==3)
		{
		code+="distinct "+var1+" "+buscarRegistro(var2)+"\n";}
		if(x==4)
		{
			code+="distinct "+buscarRegistro(var1)+" "+buscarRegistro(var2)+"\n";}
		}
	}
	
	public void operadorBinario(String var1, String var2,  int operador)
	{ 
		int a=-1111111;
		boolean c=false;
		int x=0;
		try{
		a=Integer.parseInt(var2);
		c=true;
		}
		catch (Exception e)
		{
			
		}
		
		if(c==true)//var2 es numero
		{	x=1;
		}else
			if(c==false)// var2 esregistro
			{
				x=2;
			}
		if(operador==1)
		{
			if(x==1)
			{	code+="add "+buscarRegistro(var1)+", "+var2+" => "+buscarRegistro(var1)+"\n";}
			if(x==2)
			{	code+="add "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";}
			
		}
		if(operador==2)
		{
			if(x==1)
			{	code+="sub "+buscarRegistro(var1)+", "+var2+" => "+buscarRegistro(var1)+"\n";}
			if(x==2)
			{	code+="sub "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";;}
		}
		if(operador==3)
		{
			if(x==1)
			{	code+="mult "+buscarRegistro(var1)+", "+var2+" => "+buscarRegistro(var1)+"\n";}
			if(x==2)
			{	code+="mult "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";}
		}
		if(operador==4)
		{if(x==1)
		{	code+="div "+buscarRegistro(var1)+", "+var2+" => "+buscarRegistro(var1)+"\n";}
		if(x==2)
		{	
			code+="div "+buscarRegistro(var1)+", "+buscarRegistro(var2)+" => "+buscarRegistro(var1)+"\n";}
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
	
	public void ifs(int i)
	{
		if(i==1)
		{
		code+="\nif_entry:\n";
		}
		if(i==2)
		{
			code+="\nif_yes:\n";	
		}
		if(i==3)
		{
			code+="\nif_no:\n";	
		}
	}
	
	public void dos(int i)
	{
		if(i==1)
		{
		code+="\ndo_entry:\n";
		}
		if(i==2)
		{
			code+="\nwhile_condition:\n";	
		}
		if(i==3)
		{
			code+="\nwhile_entry:\n";	
		}
		if(i==4)
		{
			code+="\ndo_action:\n";	
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
