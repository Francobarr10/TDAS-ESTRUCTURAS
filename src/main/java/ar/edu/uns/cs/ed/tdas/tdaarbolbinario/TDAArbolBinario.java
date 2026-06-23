package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;
import java.util.Iterator;
import java.util.Map;

import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.TDADiccionario;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapHashAbierto;
import ar.edu.uns.cs.ed.tdas.tdamapeo.TDAMapeo;

public class TDAArbolBinario<E> implements BinaryTree<E> {
   protected int size;
   protected BTNodo<E> raiz;
    public TDAArbolBinario(){
        raiz=null;
        size=0;
    }
    private BTNodo<E> checkPosition(Position <E> p){
        try{
            if(p==null)throw new InvalidPositionException("posicion nula");//c1
            if(p.element()==null) throw new InvalidPositionException("posicion eliminada previamente");//c2
            return (BTNodo<E>)p;// este casteo sirve para convertir la posición genérica a un nodo específico de la lista enlazada//c3 
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
    private void preorden(BTNodo<E> r,PositionList<Position<E>> l){
        l.addLast(r);//c1
        if (r.getLeft()!=null)
            preorden(r.getLeft(), l);
        if(r.getRight()!=null)
            preorden(r.getRight(), l);
    }//visita cada nodo una vez por recursividad y lo agrega a la lista por lo tanto es de orden O(n) ya que el arbol tiene n nodos.

    @Override
    public E replace(Position<E> v, E e) {
        if (v== null) throw new InvalidPositionException("Posicion nula ");//c1
        BTNodo<E> n= checkPosition(v);//c2
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
        BTNodo<E> n = checkPosition(v);//c3
        return n.getPadre();//c4
    }//c1+c2+c3+c4 pert al O(1) por ser c

    @Override
    public Iterable<Position<E>> children(Position<E> v) {
        if (v==null) throw new InvalidPositionException("posicion nula");//c1
        BTNodo<E> n = checkPosition(v);//c2
        PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();//c3
        if(n.getLeft()!=null)//c4
            l.addLast(n.getLeft());
        if(n.getRight()!=null)
            l.addLast(n.getRight());
        //c4 *n, k es la cantidad de hijos
        return l; //c5
    }//c1+c2+c3+k*c4+c5 pertencen al orden O(k) debido a q k = a la cantidad de hijos del nodo en la pos p, y k<=n por lo tanto es de O(n)

    @Override
    public boolean isInternal(Position<E> v) {
        if (v==null) throw new InvalidPositionException("posicion nula");//c1
        BTNodo<E> n= checkPosition(v);
        return n.getLeft()!=null||n.getRight()!=null;
    }//O(1)

    @Override
    public boolean isExternal(Position<E> v) {
        if (v==null) throw new InvalidPositionException("Posicion nula ");
        BTNodo<E> n= checkPosition(v);
        return n.getRight()==null&&n.getLeft()==null;
    }//O(1)

    @Override
    public boolean isRoot(Position<E> v) {
        if(v==null) throw new InvalidPositionException("posicion nula");
        BTNodo<E> n= checkPosition(v);
        if (n==raiz)
            return true;
        return false;
    }//c1+c2+max(c3,c4) pert al O(1)

    @Override
    public void createRoot(E e) {
        if (!isEmpty()) throw new  InvalidOperationException("ya tiene raiz");
        raiz = new BTNodo<E>(e);
        size++;
    }//O(1)

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) {
    return addLeft(p, e);
    }//O(1)

    @Override
    public Position<E> addLastChild(Position<E> p, E e) {
        return addRight(p, e);
    }//O(1)

    @Override
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        if(p==null|| isEmpty())throw new InvalidPositionException("posicion no valida");//c1
        BTNodo <E> padre= checkPosition(p);//c2
        if(padre.getLeft()!=null && padre.getRight()!=null)throw new InvalidPositionException("padre con 2 hijos");
        BTNodo<E> hijoA= checkPosition(rb);//c3
        if(hijoA.getPadre()!=padre||hijoA==padre.getLeft()) throw new InvalidPositionException("posicion no valida");//c4
        return addLeft(p, e);
    }//c1+c2+c3+c4+c5+k*(c6+c7)+c8 pert al orden O(k) y en el peor de los casos k<=n por lo tanto T(n)pertence al orden O(n)

    @Override
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
        if(p==null|| isEmpty())throw new InvalidPositionException("posicion no valida");//c1
        BTNodo <E> padre= checkPosition(p);//c2
        if(padre.getLeft()!=null && padre.getRight()!=null)throw new InvalidPositionException("padre con 2 hijos");
        BTNodo<E> hijoD= checkPosition(lb);//c3
        if(hijoD.getPadre()!=padre||hijoD==padre.getLeft()) throw new InvalidPositionException("posicion no valida");//c4
        return addRight(p, e);
    }// lo mismo q el anterior
    
    @Override
    public void removeExternalNode(Position<E> p) {
        if(p==null||!isExternal(p)||isEmpty()) throw new InvalidPositionException("posicicon no valida");//c1
        if(isRoot(p)){
            raiz=null;
            size--;
        }else {
            BTNodo<E> n= checkPosition(p);
            if(n.getPadre().getLeft()==n)
                n.getPadre().setLeft(null);
            else
                n.getPadre().setRight(null);
            size--;
        }
    }//c1+max(c2+c3,c4+k*(c5+c6)) pert al orden O(k) y en el peor caso k<=n por lo tanto es de O(n)
public Map<Character,Integer> eliminarHojas (BinaryTree<Character> a, Position<Character> p){
    if (p==null) throw new InvalidPositionException(null);
    Map<Character,Integer> m= new MapHashAbierto();
    PositionList<Position<Character>> lh= new ListaDoblementeEnlazada<Position<Character>>();
    BuscarHojas(lh, p,a);
    for(Position<Character> t:lh){
        if(m.get(t.element())==null)
            m.put(t.element(),1);
        else
            m.put(t.element(),m.get(t.element())+1);
    }
    while(!lh.isEmpty()){
        a.removeExternalNode(lh.first().element());
        lh.remove(lh.first());
    }
    return m;
}
private void BuscarHojas (PositionList<Position<Character>> l, Position<Character> p, BinaryTree<Character> a){
    if (a.isExternal(p))
        l.addLast(p);
    else 
        if (a.hasLeft(p))
            BuscarHojas(l, a.left(p), a);
        if (a.hasRight(p))
            BuscarHojas(l, a.right(p), a);
}
    @Override
    public void removeInternalNode(Position<E> p) {
        if (p==null||isEmpty()||!isInternal(p)) throw new InvalidPositionException("posicion no valida");//c1
        BTNodo<E> n= checkPosition(p);//c2
        BTNodo<E> hijo;
        if(n.getLeft()!= null && n.getRight()!= null){
            throw new InvalidPositionException("la raiz teine 2 hijos");
        }else if(n.getLeft()!=null){
            hijo=n.getLeft();
        }else{
            hijo=n.getRight();
        }
        if(isRoot(n)){
            raiz=hijo;
            size--;
            hijo.setPadre(null);
        }else{
            hijo.setPadre(n.getPadre());
            size--;
            if(n.getPadre().getLeft()==n)
                n.getPadre().setLeft(hijo);
            else 
                n.getPadre().setRight(hijo);
        }        
    }//T(n)=c1+c2+max(max(c3+c4+c5+c6,c7),c8+c9+k*(c10+c11)+m*(c12+13+14+15)+16+17)=c1+c2+max(max(O(1),O(1),c8+c9+O(k)+O(m)+c16+c17)=c1+c2+max(O(1),O(n)=c1+c2+O(n)=O(n) pertence al orden O(n)
    @Override
    public void removeNode(Position<E> p) {
        if (isExternal(p))//c1
            removeExternalNode(p);//O(n)
        else
            removeInternalNode(p);//O(n)
    }//c1+max (O(n), O(n)) pertence al orden O(n)
    @Override
    public Position<E> left(Position<E> v) {
        if(v==null) throw new InvalidPositionException("posicion no valida");
        BTNodo<E> n= checkPosition(v);
        if (n.getLeft()==null) throw new BoundaryViolationException("no tiene hijo izquierdo");
        return n.getLeft();
    }
    @Override
    public Position<E> right(Position<E> v) {
        if(v==null) throw new InvalidPositionException("posicion no valida");
        BTNodo<E> n = checkPosition(v);
        if(n.getRight()==null) throw new BoundaryViolationException("No tiene hijo derecho");
        return n.getRight();
    }
    @Override
    public boolean hasLeft(Position<E> v) {
        BTNodo<E> n=checkPosition(v);
        return n.getLeft()!=null;
    }
    @Override
    public boolean hasRight(Position<E> v) {
        BTNodo<E> n =checkPosition(v);
        return n.getRight()!=null;
    }
    @Override
    public Position<E> addLeft(Position<E> v, E r) {
        if (v==null) throw new InvalidPositionException("no valida");
        BTNodo<E> n= checkPosition(v);
        if(n.getLeft()!=null)throw new InvalidOperationException("ya tiene hijo izq");
        BTNodo<E> pos= new BTNodo<E>(r);
        n.setLeft(pos);
        pos.setPadre(n);
        size++;
        return pos;
    }
    @Override
    public Position<E> addRight(Position<E> v, E r) {
        if (v==null)throw new InvalidPositionException("pos no valida");
        BTNodo<E> n= checkPosition(v);
        if (n.getRight()!=null) throw new InvalidOperationException("ya tiene hijo der");
        BTNodo<E> pos= new BTNodo<E>(r);
        n.setRight(pos);
        pos.setPadre(n);
        size++;
        return pos;
    }
    @Override
    public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) {
        if(v==null||isEmpty()||!isExternal(v))throw new InvalidPositionException("pos no valida");
        BTNodo<E> n = checkPosition(v);
        TDAArbolBinario<E> a1= (TDAArbolBinario<E>) T1;
        TDAArbolBinario<E> a2= (TDAArbolBinario<E>) T2;
        if (!a1.isEmpty()){
            n.setLeft(a1.raiz);
            a1.raiz.setPadre(n);
            size+=a1.size();
            a1.raiz=null;
            a1.size=0;
        }
        if(!a2.isEmpty()){
            n.setRight(a2.raiz);
            a2.raiz.setPadre(n);
            size+=a2.size();
            a2.raiz=null;
            a2.size=0;
        }
    }
    public Dictionary<E,E> dicDeBin (){
        Dictionary<E, E> d= new TDADiccionario<E,E>();//c1
        for(Position<E> pos: positions()){
                BTNodo<E> n= checkPosition(pos);
                if(n.getLeft()!=null)
                d.insert(n.element(),n.getLeft().element());
                if(n.getRight()!=null)
                d.insert(n.element(),n.getRight().element());
            }
        return d;
    }

    public Iterable<Character> notacionInfija(BinaryTree<Character> a){
        PositionList<Character> l = new ListaDoblementeEnlazada<Character>();
        if (!a.isEmpty()){
        BTNodo<Character> n= checkPosition2(a.root());
        infija(n,l);
        }
        return l;
    }
   
    private void infija( BTNodo<Character> c,PositionList<Character> l){
        if(c.getLeft()== null && c.getRight()== null){
            l.addLast(c.element());
        }else {
            l.addLast('(');
            infija(c.getLeft(),l);
            l.addLast(c.element());
            infija(c.getRight(),l);
            l.addLast(')');
        }
    }
    private BTNodo<Character> checkPosition2(Position<Character> p) {
             try{
            if(p==null)throw new InvalidPositionException("posicion nula");//c1
            if(p.element()==null) throw new InvalidPositionException("posicion eliminada previamente");//c2
            return (BTNodo<Character>)p;// este casteo sirve para convertir la posición genérica a un nodo específico de la lista enlazada//c3 
            // lo que permite acceder a los atributos y métodos específicos de la clase DNodo, como el acceso a los nodos anterior y siguiente, 
            // así como al elemento almacenado en el nodo.        
        }
        catch (ClassCastException e){// vengo aca porque fallo el casting a NODO//c4
            throw new InvalidPositionException("p no es un nodo de lista");//c5
        }
    }
    public void completarDerechos(E r, BinaryTree<E> t){
        if (t.isEmpty())throw new EmptyTreeException("arbol vacio");
        for(Position<E> pos: t.positions()){
            if(hasLeft(pos) && !hasRight(pos))
                t.addRight(pos, r);
        }
    }
    public void eliminarSubarbol(Position<E> p){
        if (p==null) throw new InvalidPositionException("posicicon no valida");
        BTNodo<E> n=checkPosition(p);
        PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();
        postorden(n,l);
        while (!l.isEmpty()){
            Position<E> t= l.first().element();
            removeNode(t);
            l.remove(l.first());
        }
    }

    private void postorden(BTNodo<E> n, PositionList<Position<E>> l){
        if (n.getLeft()!=null)
            postorden(n.getLeft(), l);
        if(n.getRight()!=null)
            postorden(n.getRight(), l);
        l.addLast(n);
    }

    public void eliminarSubarbol2(Position<E> p){
        if(p==null)throw new InvalidPositionException("pos no valida");
        BTNodo<E> n= checkPosition(p);
        int cant= contarSubArbol(n);
        if(isRoot(p)){
            raiz=null;
            size=0;
        }else if(n.getPadre().getLeft()==n){
            n.getPadre().setLeft(null);
            size-=cant;    
        }else{
            n.getPadre().setRight(null);
            size-=cant;
        }
    }
    private int contarSubArbol(BTNodo<E> n){
        int cant=1;
        if(n.getLeft()!= null)
            cant+=contarSubArbol(n.getLeft());
        if(n.getRight()!=null)
            cant+=contarSubArbol(n.getRight());
        return cant;
    }

    public void removeNode1(Position<E> p) {
        //si el arbol eta vacio, no hay ninguna posicion para eliminar
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio, no se puede eliminar");
        }
        //validamos la posicion recibida y la convertimos al nodo real 
        BTNodo<E> nodo = checkPosition(p);

        //guardamos las referencias a sus posibles hijos
        BTNodo<E> hijoIzquierdo = nodo.getLeft();
        BTNodo<E> hijoDerecho = nodo.getRight();

        //si tiene dos hijos, no podemos eliminarlo con esta operacion, el padre del nodo eliminado solo puede reemplazarlo por una unica referencia
        if(hijoIzquierdo != null && hijoDerecho != null){
            throw new InvalidPositionException("No se puede eliminar un nodo con dos hijos, el padre no puede apuntar a dos nodos, solo tiene una unica referencia");
        }

        //determinamos cual es el unico hijo del nodo si existe. Si el nodo es hoja, hijo queda en null
        BTNodo<E> hijo;
        if(hijoIzquierdo != null){
            hijo = hijoIzquierdo;
        }else{
            hijo = hijoDerecho;
        }

        //Caso 1: el nodo a eliminar es la raiz
        if(nodo == raiz){
            //Si hijo es null, el arbol queda vacio. Si hijo no es null, ese hijo pasa a ser la raiz
            raiz = hijo;

            if(hijo != null){
                hijo.setPadre(null);
            }
        }

        //Caso 2: el nodo a eliminar no es la raiz
        else{
            //obtenemos el padre del nodo que voy a eliminar
            BTNodo<E> padre = nodo.getPadre();

            //si el nodo era hijo izquierdo de su padre, el padre ahora debe apuntar al hijo del nodo eliminado, si el nodo era hoja, hijo vale null
            if(padre.getLeft() == nodo){
                padre.setLeft(hijo);
            }

            //si el nodo era hijo derecho de su padre, el padre ahora debe apuntar al hijo del nodo eliminado, si el nodo era hoja, hijo vale null
            else if(padre.getRight() == nodo){
                padre.setRight(hijo);
            }

            //si no aparece ni como hijo izquierdo ni como derecho , entonces las referencias internas del arbol estan mal armadas
            else{
                throw new InvalidPositionException("La estructura del arbol es invalida");
            }

            //si el nodo eliminado tenia un hijo, ese hijo ahora pasa a depender del padre del nodo eliminado
            if(hijo != null){
                hijo.setPadre(padre);
            }
        }
        //actualizamos la cantidad
        size--;
    }
    public Map<Character,Integer> eliminarHoja(BinaryTree<Character> a, Position<Character> p){
        BTNodo<Character>  n = checkPosition(p);
        Map<Character,Integer> M = new MapHashAbierto<Character, Integer>();
        recpost(M, n,a);
        return M;
    }
    private void recpost (Map<Character,Integer> m, BTNodo<Character> n, BinaryTree<Character> a){
        boolean erahoja =(n.getLeft()== null && n.getRight()== null); 
        if (n.getLeft()!=null)
            recpost(m,n.getLeft(),a);
        if(n.getRight()!= null)
            recpost(m, n.getRight(),a);
        if (erahoja){
            Character c= n.element();
            Integer valor= m.get(c);
            if( valor==null)
                m.put(c,1);
            else 
                m.put(c,valor + 1);
            TDAArbolBinario<Character> A= (TDAArbolBinario<Character>)a;
            if(n==A.raiz){
                A.raiz=null;
                A.size=0;
            }else{
                if(n.getPadre().getLeft()==n){
                    n.getPadre().setLeft(null);
                    A.size--;
                }else{ 
                    n.getPadre().setRight(null);
                    A.size--;
                }
            }
        }
    }
    public Map<Character, Integer> cantOperadores(BinaryTree<Character> a){
        Map<Character, Integer> m= new TDAMapeo<>();
        for(Position<Character> p: a.positions()){
            if(a.isInternal(p)){
                Character c= p.element();
                Integer valor= m.get(c);
                if(valor==null) 
                    m.put(c,0);
                else 
                    m.put(c, valor+1);
            }
        }
        return m;
    }
    public Map<String,Integer> cantidadE (BinaryTree<String> a){
        Map <String, Integer> m = new MapHashAbierto<String, Integer>();
        m.put("operadores",0);
        m.put("pares",0);
        m.put("impares",0);
        inorden3(a,a.root(),m);
        return m;
    }
    private void inorden3(BinaryTree<String> a, Position<String> p, Map<String, Integer> m){
        if (a.hasLeft(p)){
            inorden3(a,a.left(p),m);
        }
        if(a.isInternal(p))
            m.put("operadores", m.get("operadores")+1);
        else{
            Integer n= Integer.parseInt(p.element());
            if (n % 2==0 )
                m.put("pares", m.get("pares")+1);
            else
                m.put("impares",m.get("impares")+1);
        }
        if (a.hasRight(p))
            inorden3(a,a.left(p),m);   
    }
}