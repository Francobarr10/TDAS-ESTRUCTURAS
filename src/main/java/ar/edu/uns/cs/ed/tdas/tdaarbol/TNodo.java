package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;

public class TNodo<E> implements Position<E>{
    private E elemento;
    private TNodo<E> padre;
    private ListaDoblementeEnlazada<TNodo<E>> hijos;

    public TNodo(E e, TNodo<E> padre){
        elemento = e;
        this.padre = padre;
        hijos = new ListaDoblementeEnlazada<TNodo<E>>();
    }

    public TNodo(E ele){ 
        this(ele,null); 
    }

    public E element(){ 
        return elemento; 
    }

    public ListaDoblementeEnlazada<TNodo<E>> getHijos(){ 
        return hijos; 
    }

    public void setElemento( E elemento ) { 
        this.elemento = elemento; 
    }

    public TNodo<E> getPadre() { 
        return padre; 
    }

    public void setPadre( TNodo<E> padre ) { 
        this.padre = padre; 
    }
}