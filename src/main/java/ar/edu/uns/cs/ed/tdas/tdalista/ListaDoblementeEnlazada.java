package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
@SuppressWarnings("rawtypes")
public class ListaDoblementeEnlazada<E> implements PositionList<E>     {
    private int cant;
    private DNodo<E> head;
    private DNodo<E> tail;
public ListaDoblementeEnlazada(){
    cant=0;
    head=new DNodo<>(null);

    tail=new DNodo<>(null,null,head);
    head.setSiguiente(tail);
    tail.setAnterior(head);
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
        if(isEmpty())throw new EmptyListException("lista vacia");
        return head.getSiguiente();
       }

    @Override
    public Position last() {
        if(isEmpty()) throw new EmptyListException("lista vacia");
        return tail.getAnterior();
    }

    @Override
    public Position next(Position p) {
        DNodo<E> n= checkPosition(p);
        if(n.getSiguiente()==tail)throw new BoundaryViolationException("siguiente del ultimo");
        return n.getSiguiente(); 
    }
   

    @Override
    public Position prev(Position p) {
        DNodo<E> n= checkPosition(p);
        if(n.getAnterior()==head)throw new BoundaryViolationException("anterior del primero");
        return n.getAnterior();
    }

    @Override
    public void addFirst (E element) {
    DNodo<E> pviejo= head.getSiguiente();
    DNodo<E> nuevo= new DNodo<>(element,pviejo,head);
    head.setSiguiente(nuevo); 
    pviejo.setAnterior(nuevo); 
    cant++;
        }

    @Override
    public void addLast (E element) {
    DNodo<E> pviejo= tail.getAnterior();
    DNodo<E> nuevo= new DNodo<>(element,tail,pviejo);
        tail.setAnterior(nuevo);
        pviejo.setSiguiente(nuevo);
        cant++;
    }

    @Override
    public void addAfter(Position p, E element) {
    DNodo<E> ag= checkPosition(p);
    DNodo<E> nuevo= new DNodo(element,ag.getSiguiente(),ag);
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
    public Iterator<E> iterator() {
    return new ElementIterator(this);
    }
    @Override
    public Iterable<Position<E>> positions() {
        ListaDoblementeEnlazada<Position<E>> lista = new ListaDoblementeEnlazada<>();
        DNodo<E> actual = this.head.getSiguiente();
        if(actual!= tail)
        while(actual!= tail){
            lista.addLast(actual);
            actual=actual.getSiguiente();
        }
        return lista;
    }
    public class ElementIterator implements Iterator<E>{
        private Position<E> cursor;
        private PositionList<E> lista;
        public ElementIterator(PositionList<E> l){
            this.lista = l;
            try{
                this.cursor= l.first();
            }
            catch(EmptyListException e){
                this.cursor=null;
            }
        }
        @Override
        public boolean hasNext() {
            return cursor!=null;
        }

        @Override
        public E next() {
            if(cursor==null) throw new NoSuchElementException("no hay mas elementos");
            E elemento = cursor.element();
            if(cursor!=last())
                cursor=lista.next(cursor);
            else
                cursor=null;
            return elemento;
        } 
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


}