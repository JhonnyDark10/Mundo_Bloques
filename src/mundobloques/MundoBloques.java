package mundobloques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class MundoBloques {

	//FUNCION PRINCIPAL
	public static void main(String[] args) {
		MundoBloques bloq = new MundoBloques();
		System.out.println("---------------------------------");
		System.out.println("---------------------------------");
		System.out.println("-------Sistemas Expertos---------");
		System.out.println("---------------------------------");
		List<lista_resultado> resultado = new ArrayList<>();
		System.out.println("------------ Inicio--------------");
		int numero_itiraciones_limite = 15000;
		int numero_de_combinaciones_evaluadas = 1;
        //FUNCION GENERAL PARA EVALUAR
		resultado.add(bloq.retorna_resultados(5, 3, numero_itiraciones_limite, numero_de_combinaciones_evaluadas));
	}

   //FUNCION PARA RETORNAR LOS RESULTADOS A EVALUAR
	private lista_resultado retorna_resultados(int num_bloques, int num_pila, int num_iteraciones_lim, int num_combinaciones) {
		List<resultado> resultado = new ArrayList<>();
		MundoBloques bloq = new MundoBloques();
		//RECORRE EL NUMERO DE COMBINACIONES A PRESENTAR
		for (int i = 0; i < num_combinaciones; i++) {
			try {//EVALUAR ESA COMBINACION ELEGIDA 
				resultado.add(bloq.bloque_busca_solucion(num_bloques, num_pila, num_iteraciones_lim));
			} catch (Exception e){	System.out.println(e.getMessage());}
		}
		//VARIABLES INICIALIZADAS EN 0
		double num_iteraciones = 0;
		double tam_cola = 0;
		double profundidad = 0;
		int    num_exitos = 0;
		//RECORRE UN CICLO PARA RECORRER TODAS LAS VARIABLES DE LA LISTA  Y LA ASIGNA A LA OTRA
		for (resultado t_resultado : resultado) {
			if (t_resultado.profundidad >= 0) {
				num_iteraciones += t_resultado.num_iteraciones;
				tam_cola += t_resultado.tam_cola;
				profundidad += t_resultado.profundidad;
				num_exitos += 1;}
		}
		return new lista_resultado(num_bloques, num_pila, num_exitos,num_iteraciones , tam_cola , profundidad );
	}

	//va a evaluar todo el algoritmo 
	public resultado bloque_busca_solucion(int num_bloques, int num_pila, int num_iteraciones_lim) {
		
		List<Stack<String>> pilaMetas = new ArrayList<>();
		
		Stack<String> pilaMeta1 = new Stack<>();
		// pilaMeta.push("E");	
		//pilaMeta1.push("D");
		pilaMeta1.push("A");
		pilaMeta1.push("B");	
		Stack<String> pilaMeta2 = new Stack<>();
		// pilaMeta.push("E");	
	    // pilaMeta.push("D");
		pilaMeta2.push("D");
		pilaMeta2.push("C");
		pilaMeta2.push("E");
		// pilaMeta.push("A");
		Stack<String> pilaMeta3 = new Stack<>();
		//pilaMeta.push("E");	
		//pilaMeta3.push("D");
		//pilaMeta3.push("C");
		//pilaMeta3.push("A");

		pilaMetas.add(pilaMeta1);
		pilaMetas.add(pilaMeta2);
		pilaMetas.add(pilaMeta3);
		
		
		
		Nodos nodo = new Nodos(num_bloques, num_pila);
		System.out.println("Estado Inicial:");
		//IMPRIMO EN PANTALLA
		nodo.imprimir_estado();
		//clase para el manejo de colas
		Queue<Nodos> prioridad = new LinkedList<Nodos>();	
			
		
		//MAP REPRESENTAR UNA ESTRUCTURA DE DATOS PARA ALMACENAR PARES "CLAVE/VALOR"
        //DE TAL MANERA QUE PARA UNA CLAVE SOLAMENTE TENEMOS UN VALOR.
		Map<String, Integer> nodo_profundidad = new HashMap<>();
		Map<String, Nodos> nodo_ref_mapa = new HashMap<>();
		
		prioridad.add(nodo);
		
		//put asocia el valor con la clave en el mapa.
		nodo_profundidad.put(nodo.toString(), nodo.getProfundidad());
		nodo_ref_mapa.put(nodo.toString(), nodo);
		
		int tam_cola = 1;
		
		//recorre segun el numero de iteraciones a evaluar 
		for (int i = 0; i < num_iteraciones_lim; i++) {
			//puede o no suceder ya que la cola no puede estar vacia pero por siacaso
			//empty COMPRUEVA SI LA CADENA ESTA VACIA DEVUELVE TRUE SI LA LONGITUD DE LA CADENA ES 0 DE LO CONTRARIO ES FALSA    
			if (prioridad.isEmpty()) {
				System.out.println("la cola de prioridad esta vacia");
				return new resultado(null, i, tam_cola, num_bloques, num_pila);
			}
			//�remove()� y �poll()�. Cuando se intenta extraer un dato vac�a el m�todo remove()
			//lanzar� una excepci�n como resultado, 
			//en cambio el m�todo poll() simplemente retornar� o tomar� el valor null.
			Nodos siguiente = prioridad.poll();
			System.out.println("Iteracion = " + i +", tama�o_cola = " + prioridad.size() +", profundidad = " + siguiente.getProfundidad());
			//prueba si es el objetivo //presento el estado
			siguiente.imprimir_estado();
			//comprobar si es el estado meta 				
			
			
				if(siguiente.getPila().equals(pilaMetas))
				{
					for(i = 1; i<siguiente.getPila().get(i).size(); i++) {
						if(!siguiente.getPila().get(i).isEmpty())
							return new resultado(siguiente, i, tam_cola, num_bloques, num_pila);
					}
					
					System.out.println("-------------------------------");
					System.out.println("*_* bloque completado correctamente *_*");
					System.out.println("-------------------------------");
					
					return new resultado(siguiente, i, tam_cola, num_bloques, num_pila);	
				}
			//recorre los hijos
			for (Nodos hijo : siguiente.getSucesores()) {
				String asigna_hijo = hijo.toString();		
				//M�todo que devuelve true si el mapa contiene la clave pasada como par�metro.
				//Disyunci�n realidades que est�n intr�nsecamente relacionadas entre s� o or
				//negamos
				if (!nodo_profundidad.containsKey(asigna_hijo) || hijo.getProfundidad() < nodo_profundidad.get(asigna_hijo)) {	
					nodo_profundidad.put(asigna_hijo, hijo.getProfundidad());
					//elimina en la referencia prioridad
					prioridad.remove(nodo_ref_mapa.get(asigna_hijo));
					//agrega el nodo hijo
					prioridad.add(hijo);
					//reasigna al ref mapa 
					nodo_ref_mapa.put(asigna_hijo, hijo);
				}
				//presenta
				hijo.imprimir_estado();	
			}
			//asigna el total de nodos agregados en la ocola
			tam_cola = Math.max(tam_cola, prioridad.size());
		}	
		System.out.println(" xxxxxxx Resultado no encontrado xxxxxxxx");
		return new resultado(null, num_iteraciones_lim, tam_cola, num_bloques, num_pila);
	}
	//VARIABLE INICIALIZADA EN UNA CLASE CON SU CONSTRUCTOR 
	private class lista_resultado {
		
		public double num_iteraciones;
		public double tam_cola;
		public int num_bloques;
		public int num_pilas;
		public double profundidad;
		public int num_exito;
		
		public lista_resultado(int num_bloques, int num_pila, int num_exito, double num_iteraciones, double tam_cola, double profundidad) {
			this.num_bloques = num_bloques;
			this.num_pilas = num_pila;
			this.num_iteraciones = num_iteraciones;
			this.tam_cola = tam_cola;
			this.profundidad = profundidad;
			this.num_exito = num_exito;
		}
	}
	//VARIABLE INICIALIZADA EN UNA CLASE CON SU CONSTRUCTOR 
		private class resultado {
			public int num_iteraciones;
			public int tam_cola;
			public int num_bloques;
			public int num_pila;
			public int profundidad;
			public Nodos nodo;

			public resultado(Nodos nodo, int num_iteraciones, int tam_cola, int num_bloques, int num_pilas) {
				this.nodo = nodo;
				this.num_bloques = num_bloques;
				this.num_pila = num_pilas;
				this.profundidad = (nodo == null) ? -1 : nodo.getProfundidad();
				this.num_iteraciones = num_iteraciones;
				this.tam_cola = tam_cola;
			}
		}

}
