package ar.edu.uns.cs.ed.tdas.tdaarbol;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class TDAArbol<E>implements Tree<E> {
    protected TNodo<E> raiz;
    int size;

    public TDAArbol(){
        raiz = null;//c1
        size=0;//c2
    }//c1+c2=T(n) pertenece al O(1)

    private TNodo<E> checkPosition(Position <E> p){
        try{
            if(p==null)throw new InvalidPositionException("posicion nula");//c1
            if(p.element()==null) throw new InvalidPositionException("posicion eliminada previamente");//c2
            return (TNodo<E>)p;// este casteo sirve para convertir la posición genérica a un nodo específico de la lista enlazada//c3 
            // lo que permite acceder a los atributos y métodos específicos de la clase DNodo, como el acceso a los nodos anterior y siguiente, 
            // así como al elemento almacenado en el nodo.        
        }
        catch (ClassCastException e){// vengo aca porque fallo el casting a NODO//c4
            throw new InvalidPositionException("p no es un nodo de lista");//c5
        }
    }//c1+c2+c3+c4+c5 pertencen al O(1)
    @Override
    public int size() {
        return size;//c1
    }//c1 pertence al O(1)

    @Override
    public boolean isEmpty() {
        return size==0;//c1
    }//c1 pertenece al O(1)

    @Override
    public Iterator<E> iterator() {
        PositionList<E> l= new ListaDoblementeEnlazada<E>();//c1
        for(Position<E> pos: positions()){//como position ya es de orden O(n) y esto recorre todo positions tenemos 
            l.addLast(pos.element());//c2
        }//n veces, siendo n igual al tamanio de la lista de posiciones del arbol
        return l.iterator();//c3
    }//c1+n*c2+O(n)+c3= O(n)+O(n)= O(n) T(n) pertenece a O(n)

    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();//c1
        if(!isEmpty())
            preorden(raiz,l);//k veces, siendo k la cantidad de posiciones del arbol
        return l;//c3
    }//c1+c2+O(n)+c3 pertence al O(n)  
    private void preorden(TNodo<E> n,PositionList<Position<E>> l){
        l.addLast(n);//c1
        for (TNodo<E> t: n.getHijos()){//n 
            preorden(t, l);
        }
    }//visita cada nodo una vez por recursividad y lo agrega a la lista por lo tanto es de orden O(n) ya que el arbol tiene n nodos.

    @Override
    public E replace(Position<E> v, E e) {
        if (v== null) throw new InvalidPositionException("Posicion nula ");//c1
        TNodo<E> n= checkPosition(v);//c2
        E viejo= n.element();//c3
        n.setElemento(e);//c4
        return viejo;//c5
    }//c1+c2+c3+c4+c5 pertences al O(1)debido a q son constantes los tiempos del metodo

    @Override
    public Position<E> root() {
        if (isEmpty()) throw new EmptyTreeException("raiz vacia");//c1
        return raiz;//c2
    }//c1+c2 pertences al O(1) por ser c

    @Override
    public Position<E> parent(Position<E> v) {
        if (v==null) throw new InvalidPositionException("posicion nula");// c1
        if (this.isRoot(v))throw new BoundaryViolationException(" la pos es raiz");//c2
        TNodo<E> n = checkPosition(v);//c3
        return n.getPadre();//c4
    }//c1+c2+c3+c4 pert al O(1) por ser c

    @Override
    public Iterable<Position<E>> children(Position<E> v) {
        if (v==null) throw new InvalidPositionException("posicion nula");//c1
        TNodo<E> n = checkPosition(v);//c2
        PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();//c3
        for (Position<E> p : n.getHijos()){//k
            l.addLast(p);//c4
        }//c4 *n, k es la cantidad de hijos
        return l; //c5
    }//c1+c2+c3+k*c4+c5 pertencen al orden O(k) debido a q k = a la cantidad de hijos del nodo en la pos p, y k<=n por lo tanto es de O(n)

    @Override
    public boolean isInternal(Position<E> v) {
        if (v==null) throw new InvalidPositionException("posicion nula");//c1
        TNodo<E> n= checkPosition(v);
        return !n.getHijos().isEmpty();
    }//O(1)

    @Override
    public boolean isExternal(Position<E> v) {
        if (v==null) throw new InvalidPositionException("Posicion nula ");
        TNodo<E> n= checkPosition(v);
        return n.getHijos().isEmpty();
    }//O(1)

    @Override
    public boolean isRoot(Position<E> v) {
        if(v==null) throw new InvalidPositionException("posicion nula");
        TNodo<E> n= checkPosition(v);
        if (n==raiz)
            return true;
        return false;
    }//c1+c2+max(c3,c4) pert al O(1)

    @Override
    public void createRoot(E e) {
        if (!isEmpty()) throw new  InvalidOperationException("ya tiene raiz");
        raiz = new TNodo<E>(e);
        size++;
    }//O(1)

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) {
        if (p==null|| isEmpty())throw new InvalidPositionException("posicion no valida");
        TNodo<E> padre= checkPosition(p);
        TNodo<E> hijo= new TNodo<E>(e, padre);
        padre.getHijos().addFirst(hijo);
        size++;
        return hijo;
    }//O(1)

    @Override
    public Position<E> addLastChild(Position<E> p, E e) {
        if(p==null|| isEmpty())throw new InvalidPositionException("posicion no valida");
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hijo = new TNodo<E> (e,padre);
        padre.getHijos().addLast(hijo);
        size++;
        return hijo;
    }//O(1)

    @Override
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        if(p==null|| isEmpty())throw new InvalidPositionException("posicion no valida");//c1
        TNodo <E> padre= checkPosition(p);//c2
        TNodo<E> hijoA= checkPosition(rb);//c3
        if(hijoA.getPadre()!=padre) throw new InvalidPositionException("posicion no valida");//c4
        TNodo <E> hijoN= new TNodo<E>(e, padre);//c5
        for (Position<TNodo<E>> pos: padre.getHijos().positions()){//aca k
            if(pos.element()==hijoA){//
                padre.getHijos().addBefore(pos, hijoN);//c6
                size++;//c7
            }
        }//k igual a la cantidad de hijos del nodo en la posicion p 
        return hijoN;//c8
    }//c1+c2+c3+c4+c5+k*(c6+c7)+c8 pert al orden O(k) y en el peor de los casos k<=n por lo tanto T(n)pertence al orden O(n)

    @Override
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
        if (p==null|| isEmpty()) throw new InvalidPositionException("posicion no valida");
        TNodo<E> padre= checkPosition(p);
        TNodo<E> hijoD= checkPosition(lb);
        if(hijoD.getPadre()!=padre)throw new InvalidPositionException("posicion no valida");
        TNodo<E> hijoN = new TNodo<E>(e, padre);
        for (Position<TNodo<E>> pos: padre.getHijos().positions()){
            if(pos.element()==hijoD){
                padre.getHijos().addAfter(pos, hijoN);
                size++;
            }
        }
        return hijoN;
    }// lo mismo q el anterior

    @Override
    public void removeExternalNode(Position<E> p) {
        if(p==null||!isExternal(p)||isEmpty()) throw new InvalidPositionException("posicicon no valida");//c1
        if(isRoot(p)){
            raiz=null;//c2
            size--;//c3
        }else{//preguntar esto sobre la raiz
            TNodo<E> n= checkPosition(p);//c4
            for(Position<TNodo<E>> pos:n.getPadre().getHijos().positions()){//aca k
                if (pos.element()==n){
                    n.getPadre().getHijos().remove(pos);//c5
                    size--;//c6
                }
            }//k es igual a la cantidad de posiciones de la lista de hijos del padre del nodo de la posicion p
        }
    }//c1+max(c2+c3,c4+k*(c5+c6)) pert al orden O(k) y en el peor caso k<=n por lo tanto es de O(n)

    @Override
    public void removeInternalNode(Position<E> p) {
        if (p==null||isEmpty()||!isInternal(p)) throw new InvalidPositionException("posicion no valida");//c1
        TNodo<E> n= checkPosition(p);//c2
        if(n==raiz){
            if(n.getHijos().size()==1){
                raiz=n.getHijos().first().element();//c3
                n.getHijos().first().element().setPadre(null);//c4
                size--;//c5
            }
            else{
                throw new InvalidPositionException("raiz con mas de un hijo");//c7
            }
        }else{
            TNodo<E> padre= n.getPadre();//c8
            Position<TNodo<E>> posN=null;//c9
            for(Position<TNodo<E>> pos: padre.getHijos().positions()){//aca n
                if(pos.element()==n){
                    posN=pos;//c10
                    break;//c11
                }
            }//n igual a la cantidad de posiciciones de la lista de hijos del padre del nodo de la posicicion p
            while(!n.getHijos().isEmpty()){//aca m
                TNodo<E>t=n.getHijos().first().element();//c12
                n.getHijos().remove(n.getHijos().first());//c13
                t.setPadre(padre);//14
                padre.getHijos().addBefore(posN, t);//c15
            }//m igual a tamanio de la lista de hijos del nodo de la posicion p
            padre.getHijos().remove(posN);//c16
            size--;//c17
        }
    }//T(n)=c1+c2+max(max(c3+c4+c5+c6,c7),c8+c9+k*(c10+c11)+m*(c12+13+14+15)+16+17)=c1+c2+max(max(O(1),O(1),c8+c9+O(k)+O(m)+c16+c17)=c1+c2+max(O(1),O(n)=c1+c2+O(n)=O(n) pertence al orden O(n)
    @Override
    public void removeNode(Position<E> p) {
        if (isExternal(p))//c1
            removeExternalNode(p);//O(n)
        else
            removeInternalNode(p);//O(n)
    }   
}//c1+max (O(n), O(n)) pertence al orden O(n)