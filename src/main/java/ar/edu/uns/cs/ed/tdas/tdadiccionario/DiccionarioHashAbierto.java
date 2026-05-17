package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.TDAEntry;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;


public class DiccionarioHashAbierto<K,V> implements Dictionary<K,V>{

    protected int n; // cantidad de entradas en el diccionario
    protected int cant; // tamaño del arreglo de posiciones
    protected PositionList<Entry<K,V>>[] tabla; // arreglo de posiciones

    public DiccionarioHashAbierto() {
        n=13;
        tabla =new PositionList[n];
        for (int i = 0; i <n; i ++){
            tabla[i]=new ListaDoblementeEnlazada<>();
        }
        cant=0;
    }
    @Override
    public int size() {
        return cant;
    }

    @Override
    public boolean isEmpty() {
        return cant == 0;
    }

    @Override
    public Entry<K, V> find(K key) {
        if (key == null) throw new InvalidKeyException("Clave nula no permitida");
        int i = h(key);
        for (Entry<K,V> e: tabla[i]){
          if (e.getKey().equals(key)){ 
            return e;
          }    
        }
        return null;
     }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) {
        if (key == null) throw new InvalidKeyException("Clave nula no permitida");
        int i = h(key);
        PositionList<Entry<K,V>> lista = new ListaDoblementeEnlazada<>();
        for (Entry<K,V> e: tabla[i]){
            if(e.getKey().equals(key)){
                lista.addLast(e);
            }
        }
            return lista;
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
    if(key == null )throw new InvalidKeyException("Clave nula no permitida");
        int i = h(key);
        Entry <K,V> e = new TDAEntry<K,V>(key, value);
        tabla[i].addLast(e);
        cant++;
        return e;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) {
        if ( e == null) throw new InvalidEntryException("Entrada no valida");
        int i = h(e.getKey());
        for ( Position<Entry<K,V>> p: tabla[i].positions()){
            if(p.element()== e){
                Entry <K,V> r= p.element();
                tabla[i].remove(p);
                cant--;
                return r;   
            }
    }
   throw new InvalidEntryException("Entrada no valida");
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
    PositionList<Entry<K,V>> entradas = new ListaDoblementeEnlazada<>();
    for ( int i =0; i <n ; i ++){
        for (Entry<K,V> e: tabla[i]){
            entradas.addLast(e);
        }
    }
    return entradas;
    }
    private int h(K key){
        if (key == null )throw new InvalidKeyException("Clave nula no permitida");
        return Math.abs(key.hashCode()) % n;
    }
    Iterable<Entry<K,V>> eliminarTodas(K c,V v){
        if (c==null) throw new InvalidKeyException("Clave nula no permitida");//c0
        int i = h(c);// una operacion O(1)c1
        PositionList<Entry<K,V>> res= new ListaDoblementeEnlazada<>();//O(1)c2
        for (Position<Entry<K,V>> p: tabla[i].positions()){//aca n
            if(p.element().getKey().equals(c) && p.element().getValue().equals(v)){//c4
                res.addLast(p.element());//c5
                tabla[i].remove(p);//c6
                cant--;//c7
            }
        }//k =tamaño del bucket
        return res;//c8
    }
// T(n)= c0+c1+c2+k(c3+c4+c5+c6+c7)+c8,donde k es el tamaño del bucket recorrido.
// asumiendo q tenemos una buena funcion hash, las claves se distribuyen uniformemente
// entre los buckets, por lo que k puede considerarse constante.
// Entonces:
// T(n)O(1)
//
// En el peor caso, si todas las entradas colisionan en el mismo bucket,
// k = n, y queda:
// T(n)=O(n)
// entonces T(n)=O(n)
}