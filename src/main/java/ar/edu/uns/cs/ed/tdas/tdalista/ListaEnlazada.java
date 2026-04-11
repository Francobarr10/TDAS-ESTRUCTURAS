package ar.edu.uns.cs.ed.tdas.tdalista;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;

@SuppressWarnings("rawtypes")
public class ListaEnlazada<E> implements PositionList<E> {
    private int cant;
    private DNodo<E> head;
    private DNodo<E> tail;
public ListaEnlazada(){
    cant=0;
    head=new DNodo<>(null);
    tail=new DNodo<>(null);
}
    @Override
    public int size() {
    return cant;
    }

    @Override
    public boolean isEmpty() {
        return cant==0;
    }

    @Override
    public Position first() {
        if(isEmpty())throw new InvalidPositionException("lista vacia");
        return head.getSiguiente();
       }

    @Override
    public Position last() {
        // TODO Auto-generated method stub
        if(isEmpty()) throw new InvalidPositionException("lista vacia");
        return tail.getAnterior();
    }

    @Override
    public Position next(Position p) {
        if(isEmpty()) throw new InvalidPositionException("Lista vacia");
         DNodo<E> n= checkPosition(p);
        if(n.getSiguiente()==null)throw new BoundaryViolationException("siguiente del ultimo");
        return n.getSiguiente();
    }
    private DNodo<E> checkPosition(Position <E> p){
    try{
        if(p==null)throw new InvalidPositionException("posicion nula");
        if(p.element()==null) throw new InvalidPositionException("posicion eliminada previamente");
        return (DNodo<E>)p;// este casteo sirve para convertir la posición genérica a un nodo específico de la lista enlazada, 
        // lo que permite acceder a los atributos y métodos específicos de la clase DNodo, como el acceso a los nodos anterior y siguiente, 
        // así como al elemento almacenado en el nodo.        
    }
    catch (ClassCastException e){// vengo aca porque fallo el casting a NODO
        throw new InvalidPositionException("p no es un nodo de lista");
    }
    }

    @Override
    public Position prev(Position p) {
        if(isEmpty()) throw new InvalidPositionException("Lista vacia");
         DNodo<E> n= checkPosition(p);
        if(n.getAnterior()==null)throw new BoundaryViolationException("anterior del primero");
        return n.getAnterior();
    }

    @Override
    public void addFirst (E element) {
        
    DNodo<E> pviejo= head.getSiguiente();
    DNodo<E> nuevo= new DNodo<>((E)element,pviejo,head);
        head.setSiguiente(nuevo);
        pviejo.setAnterior(nuevo);
        cant++;
        }

    @Override
    public void addLast (E element) {
    DNodo<E> pviejo= tail.getAnterior();
    DNodo<E> nuevo= new DNodo<>(element,pviejo,tail);
        tail.setAnterior(nuevo);
        pviejo.setSiguiente(nuevo);
        cant++;
    }

    @Override
    public void addAfter(Position p, E element) {
    DNodo<E> ag= checkPosition(p);
    DNodo<E> nuevo= new DNodo(element,ag,ag.getSiguiente());
        ag.getSiguiente().setAnterior(nuevo);
        ag.setSiguiente(nuevo);
        cant++;
    }

    @Override
    public void addBefore(Position p, E element) {
    DNodo<E> ag=checkPosition(p);
    DNodo<E> nuevo= new DNodo(element, ag, ag.getAnterior());
        ag.getAnterior().setSiguiente(nuevo);
        ag.setAnterior(nuevo);
        cant++;
}

    @Override
    public E remove(Position p) {
    DNodo<E> nodo= checkPosition(p);
    nodo.getAnterior().setSiguiente(nodo.getSiguiente());
    nodo.getSiguiente().setAnterior(nodo.getAnterior());
    E elemento = nodo.element();
    nodo.setElemento(null);
    cant--;
    return elemento;    
}

    @Override
    public E set(Position p, E element) {
    DNodo<E> nodo= checkPosition(p);
    E eviejo =nodo.element();
    nodo.setElemento(element);
    return eviejo;
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Iterable positions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'positions'");
    } 
}
