package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xi, xd, h, siendo. Siendo
 xi la coordenada en X origen del edificio iésimo, xd la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {
	
    private ArrayList <Edificio> ciudad;

    public Ciudad()
    {    
    	ciudad = new ArrayList <Edificio>();
    }
    
    public Ciudad(int n) {
    	ciudad = new ArrayList <Edificio>();
    	int xi,y,xd;
    	for(int i = 0; i < n; i++)
    	{
    		xi=(int)(Math.random()*100);
    		y=(int)(Math.random()*100);
    		xd=(int)(xi+(Math.random()*100));
    		this.addEdificio(new Edificio(xi,y,xd));
    	}

    }
        
    public Edificio getEdificio(int i) {
        return (Edificio)this.ciudad.get(i);
    }
    
       
    public void addEdificio (Edificio e)
    {
        ciudad.add(e);
    }
    public void removeEdificio(int i)
    {
        ciudad.remove(i);
    }
    
    public int size()
    {
        return ciudad.size();
    }
    
    
    /*
     Método que carga los edificios que me pasan en el
     archivo cuyo nombre se encuentra en "fichero".
     El formato del fichero nos lo ha dado el profesor en la clase del 9/3/2020,
     pocos días antes del estado de alarma.
     */

    public void cargarEdificios (String fichero) {	
        try {
            int xi, y, xd;
            Scanner sr = new Scanner(new File(fichero));
            while(sr.hasNext()) {
                xi = sr.nextInt();
                xd = sr.nextInt();
                y = sr.nextInt();
                Edificio Salida = new Edificio(xi, y, xd);
                this.addEdificio(Salida);
            }
        }
        catch(Exception e){
        	System.out.println("No se han podido cargar los edificios correctamente");
        }     
    }
}
