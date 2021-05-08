package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class LineaHorizonte {
	
	private ArrayList <Punto> LineaHorizonte ;
	
    /*
     * Constructor sin par�metros
     */
    public LineaHorizonte()
    {
        LineaHorizonte = new ArrayList <Punto>();
    }
            
    /*
     * m�todo que devuelve un objeto de la clase Punto
     */
    public Punto getPunto(int i) {
        return (Punto)this.LineaHorizonte.get(i);
    }
    
    // A�ado un punto a la l�nea del horizonte
    public void addPunto(Punto p)
    {
        LineaHorizonte.add(p);
    }    
    
    // m�todo que borra un punto de la l�nea del horizonte
    public void borrarPunto(int i)
    {
        LineaHorizonte.remove(i);
    }
    
    public int size()
    {
        return LineaHorizonte.size();
    }
    // m�todo que me dice si la l�nea del horizonte est� o no vac�a
    public boolean isEmpty()
    {
        return LineaHorizonte.isEmpty();
    }
   
    /*
      M�todo al que le pasamos una serie de par�metros para poder guardar 
      la linea del horizonte resultante despu�s de haber resuelto el ejercicio
      mediante la t�cnica de divide y vencer�s.
     */
    
    public void guardaLineaHorizonte (String fichero) {
        try {
            Punto p1 = new Punto();
            FileWriter fileWriter = new FileWriter(fichero);
            PrintWriter out = new PrintWriter (fileWriter);
         
            for(int i=0; i<this.size(); i++) {
                p1=(getPunto(i));
                out.print(p1.getX() + " " + p1.getY());
            }
            out.close();
        }
        catch(Exception e) {
        	System.out.println("No se ha podido guardar la linea del horizonte");
        }
    }
    
    public void imprimir (){
    	
    	for(int i=0; i< LineaHorizonte.size(); i++ ){
    		System.out.println(cadena(i));
    	}
    }
    
    public String cadena (int i){
    	Punto p = LineaHorizonte.get(i);
    	int x = p.getX();
    	int y = p.getY();
    	String linea = p.toString();
		return linea;
    }
    
    
    
    
    public LineaHorizonte getLineaHorizonte(Ciudad ciudad)
    {
    	// pi y pd, representan los edificios de la izquierda y de la derecha.
        int pi = 0;                       
        int pd = ciudad.size()-1;      
        return crearLineaHorizonte(pi, pd, ciudad);  
    }
    
    public LineaHorizonte crearLineaHorizonte(int pi, int pd, Ciudad ciudad)
    {
    	LineaHorizonte linea = new LineaHorizonte(); // LineaHorizonte de salida
    	Punto p1 = new Punto();   // punto donde se guardara en su X la Xi del efificio y en su Y la altura del edificio
    	Punto p2 = new Punto();   // punto donde se guardara en su X la Xd del efificio y en su Y le pondremos el valor 0
    	Edificio edificio = new Edificio();    
            
    	// Caso base, la ciudad solo tiene un edificio, el perfil es el de ese edificio. 
    	if(pi==pd) 
    	{
    		edificio = ciudad.getEdificio(pi); // Obtenemos el único edificio y lo guardo en b
    		// En cada punto guardamos la coordenada X y la altura.
    		p1.setX(edificio.getXi());       
    		p1.setY(edificio.getY());        // guardo la altura
    		p2.setX(edificio.getXd());       
    		p2.setY(0);                      // como el edificio se compone de 3 variables, en la Y de p2 le añadiremos un 0
    		// Añado los puntos a la línea del horizonte
    		linea.addPunto(p1);      
    		linea.addPunto(p2);
    	}
    	else
    	{
    		// Edificio mitad
    		int medio=(pi+pd)/2;

    		LineaHorizonte s1 = crearLineaHorizonte(pi,medio,ciudad);  
    		LineaHorizonte s2 = crearLineaHorizonte(medio+1,pd,ciudad);
    		linea = LineaHorizonteFussion(s1,s2); 
    	}
    	return linea;
        }
        
        /**
         * Función encargada de fusionar los dos LineaHorizonte obtenidos por la técnica divide y
         * vencerás. Es una función muy compleja ya que es la encargada de decidir si un
         * edificio solapa a otro, si hay edificios contiguos, etc. y solucionar dichos
         * problemas para que el LineaHorizonte calculado sea el correcto.
         */
    public LineaHorizonte LineaHorizonteFussion(LineaHorizonte s1,LineaHorizonte s2)
    {
    	// en estas variables guardaremos las alturas de los puntos anteriores, en s1y la del s1, en s2y la del s2 
    	// y en prev guardaremos la previa del segmento anterior introducido
    	historialAlturas histAlt=new historialAlturas();
        LineaHorizonte salida = new LineaHorizonte(); // LineaHorizonte de salida
        
        Punto p1 = new Punto();         // punto donde guardaremos el primer punto del LineaHorizonte s1
        Punto p2 = new Punto();         // punto donde guardaremos el primer punto del LineaHorizonte s2
        Punto paux = new Punto();
        
        imprimirInfoLineas(s1,s2);
        //Mientras tengamos elementos en s1 y en s2
        while ((!s1.isEmpty()) && (!s2.isEmpty())) 
        {
            paux = new Punto();  // Inicializamos la variable paux
            p1 = s1.getPunto(0); // guardamos el primer elemento de s1
            p2 = s2.getPunto(0); // guardamos el primer elemento de s2

            if (p1.getX() < p2.getX()) // si X del s1 es menor que la X del s2
            {
            	LineaHorizonteFussionCaso1(s1,p1,paux,histAlt,salida);
            }
            else if (p1.getX() > p2.getX()) // si X del s1 es mayor que la X del s2
            {
            	LineaHorizonteFussionCaso2(s2,p2,paux,histAlt,salida);
            }
            else // si la X del s1 es igual a la X del s2
            {
            	if ((p1.getY() > p2.getY()) && (p1.getY()!=histAlt.getPrev())) // guardaremos aquel punto que tenga la altura mas alta
                {
                    salida.addPunto(p1);
                    histAlt.setPrev(p1.getY());
                }
                if ((p1.getY() <= p2.getY()) && (p2.getY()!=histAlt.getPrev()))
                {
                    salida.addPunto(p2);
                    histAlt.setPrev(p1.getY());
                }
                histAlt.setS1y(p1.getY());   // actualizamos la s1y e s2y
                histAlt.setS2y(p2.getY());
                s1.borrarPunto(0); // eliminamos el punto del s1 y del s2
                s2.borrarPunto(0);
            }
        }
        while ((!s1.isEmpty())) //si aun nos quedan elementos en el s1
        {
            paux=s1.getPunto(0); // guardamos en paux el primer punto
            
            if (paux.getY()!=histAlt.getPrev()) // si paux no tiene la misma altura del segmento previo
            {
                salida.addPunto(paux); // lo añadimos al LineaHorizonte de salida
                histAlt.setPrev(paux.getY());    // y actualizamos prev
            }
            s1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }
        while((!s2.isEmpty())) //si aun nos quedan elementos en el s2
        {
            paux=s2.getPunto(0); // guardamos en paux el primer punto
           
            if (paux.getY()!=histAlt.getPrev()) // si paux no tiene la misma altura del segmento previo
            {
                salida.addPunto(paux); // lo añadimos al LineaHorizonte de salida
                histAlt.setPrev(paux.getY());    // y actualizamos prev
            }
            s2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es valido)
        }
        return salida;
    }
        public void LineaHorizonteFussionCaso1(LineaHorizonte s, Punto p, Punto paux, historialAlturas histAlt, LineaHorizonte salida)
        {
            paux.setX(p.getX());                // guardamos en paux esa X
            paux.setY(Math.max(p.getY(), histAlt.getS2y())); // y hacemos que el maximo entre la Y del s1 y la altura previa del s2 sea la altura Y de paux

            if (paux.getY()!=histAlt.getPrev()) // si este maximo no es igual al del segmento anterior
            {
                salida.addPunto(paux); // añadimos el punto al LineaHorizonte de salida
                histAlt.setPrev(paux.getY());     // actualizamos prev
            }
            histAlt.setS1y(p.getY());   // actualizamos la altura s1y
            s.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }

        public void LineaHorizonteFussionCaso2(LineaHorizonte s, Punto p, Punto paux, historialAlturas histAlt, LineaHorizonte salida)
        {
            paux.setX(p.getX());                // guardamos en paux esa X
            paux.setY(Math.max(p.getY(), histAlt.getS1y())); // y hacemos que el maximo entre la Y del s1 y la altura previa del s2 sea la altura Y de paux

            if (paux.getY()!=histAlt.getPrev()) // si este maximo no es igual al del segmento anterior
            {
                salida.addPunto(paux); // añadimos el punto al LineaHorizonte de salida
                histAlt.setPrev(paux.getY());     // actualizamos prev
            }
            histAlt.setS2y(p.getY());   // actualizamos la altura s1y
            s.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es valido)
        }


        
        public void imprimirInfoLineas(LineaHorizonte s1, LineaHorizonte s2)
        {
            System.out.println("==== S1 ====");
            s1.imprimir();
            System.out.println("==== S2 ====");
            s2.imprimir();
            System.out.println("\n");
        }
}
