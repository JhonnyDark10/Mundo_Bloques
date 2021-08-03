package mundobloques;
import java.util.*;


public class Nodos {
	//VARIABLES A UTILIZAR 
	private Nodos padre;
    private Set<Nodos> sucesores = null;
    //UNA PILA 
    private List<Stack<String>> pila;
    //VARIABLES A UTILIZAR
    private int profundidad;
    private int num_bloques;
    private int num_pila;
    private int numexitos;
    
    //NODOS PARA PODER RECORRER LAS PILAS
    public Nodos(int num_bloques, int num_pila) {
    	
    	this.num_pila = num_pila;
        this.num_bloques = num_bloques;
        padre = null;
             
        pila = new ArrayList<>();
       
        Stack<String> pilaInicial1 = new Stack<>();
		// pilaInicial1.push("E");	
        pilaInicial1.push("D");
        pilaInicial1.push("C");
			
		Stack<String> pilaInicial2 = new Stack<>();
		// pilaInicial2.push("E");	
	    // pilaInicial2.push("D");
		// pilaInicial2.push("C");
		pilaInicial2.push("B");
		// pilaMeta.push("A");
		Stack<String> pilaInicial3 = new Stack<>();
		// pilaInicial3.push("E");	
		// pilaInicial3.push("D");
		// pilaInicial3.push("C");
		// pilaInicial3.push("B");
		pilaInicial3.push("A");
		pilaInicial3.push("E");
		
         pila.add(pilaInicial1);
         pila.add(pilaInicial2);
         pila.add(pilaInicial3);
        
         //*************************
         //obtiene cantidad de estados
          setNumexitos();
         //ordenar aleatoriamente las letras en las pilas
         //OrdenarAleatoriamente();
        
    }

     //SIRVE PARA OBTENER EL TOTAL DE ESPACIO CON CANTIDAD DE PILA Y LOS BLOQUES
    private void setNumexitos() {
    	numexitos = num_bloques * num_pila;
    }
  
    //para realizar los movimientos de las letras
    
    private boolean mover(int inicio, int destino) {
        if (inicio == destino) {
            return false;
        }
      //COMPRUEVA SI LA CADENA ESTA VACIA DEVUELVE TRUE SI LA LONGITUD DE LA CADENA ES 0 DE LO CONTRARIO ES FALSA 
        
        if (pila.get(inicio).isEmpty()) {
            return false;
        }
      //POP SACA UN ELEMENTO DE LA PILA
        String top = pila.get(inicio).pop();
      //PUSH INTRODUCE UN ELEMENTO EN LA PILA
        pila.get(destino).push(top);
        return true;
    }
    
      
  //PRESENTAR DATOS DEL NODO EVALUADO
    public void imprimir_estado() {
         for (int i = 0; i < num_pila; i++) {
            
        	System.out.print(i + 1);
            System.out.print(" | ");
            Stack<String> p_pila = pila.get(i);
            
            for (int j = 0; j < p_pila.size(); j++) {
                System.out.print(p_pila.get(j) + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
      
    //para dar los hijos sucesores clonar
    public Set<Nodos> getSucesores() {
        if (sucesores != null) {
            return sucesores;
        }
        //HASHSET - la l�gica de verificaci�n que el elemento no sea repetido
        sucesores = new HashSet<>();
       
        for (int i = 0; i < num_pila; i++) {
            for (int j = 0; j < num_pila; j++) {
            	Nodos p_sucesor = copiaSucesor();
                if (p_sucesor.mover(i, j)) {
                	sucesores.add(p_sucesor);
                  }
                }
            }
        
        return sucesores;
    }

    private Nodos copiaSucesor() {
    	Nodos nuevo_nodo = new Nodos(num_bloques, num_pila);
    	nuevo_nodo.setPila(copiar_pila(pila));
    	nuevo_nodo.setPadre(this);
    	nuevo_nodo.setProfundidad(profundidad + 1);
        return nuevo_nodo;
    }
    
    private List<Stack<String>> copiar_pila(List<Stack<String>> pila) {
        List<Stack<String>> nueva_pila = new ArrayList<>();
       
        for (Stack<String> a_pila : pila) {
            Stack<String> nueva_pil = new Stack<>();
            nueva_pil.addAll(a_pila);
            nueva_pila.add(nueva_pil);
        }
        return nueva_pila;
    }
    
  //GETTER Y SETTER  
 
    private void setPila(List<Stack<String>> pila) {
        this.pila = pila;
    }

    
    public List<Stack<String>> getPila() {
		return pila;
	}

	public int getProfundidad() {
        return profundidad;
    }

    private void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

	public int getNum_bloques() {
		return num_bloques;
	}

	public void setNum_bloques(int num_bloques) {
		this.num_bloques = num_bloques;
	}

	public int getNum_pila() {
		return num_pila;
	}

	public void setNum_pila(int num_pila) {
		this.num_pila = num_pila;
	} 
	
	public Nodos getPadre() {
	    return padre;
	}

	private void setPadre(Nodos padre) {
	    this.padre = padre;
	}
    
	 //para presentar 
    @Override
    public String toString() {
    	//StringBuilder ALMACENAR CARACTERES
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num_pila; i++) {
            Stack<String> stack = pila.get(i);
          //size PARA DEVOLVER EL TAMA�O
            for (int j = 0; j < stack.size(); j++) {
            	//APPEND PARA PRESENTAR CARACTERES
            	//ELEMENTART RETORNA EL COMPONENTE ESPECIFICADO
            
            	sb.append(stack.elementAt(j));
            }
            //se a�ade al final del caracter
            sb.append("#");
        }
        return sb.toString();
    }
    
   

}
