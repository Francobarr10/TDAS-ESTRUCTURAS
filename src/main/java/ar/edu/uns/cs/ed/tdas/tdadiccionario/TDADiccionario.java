package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.TDAEntry;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;


public class TDADiccionario<K,V> implements Dictionary<K,V>{
    protected PositionList<Entry <K,V>> D;
    public TDADiccionario(){
        D = new ListaDoblementeEnlazada<Entry<K,V>>();
    }
    public int size() {
        return D.size();
    }
    public boolean isEmpty() {
        return D.isEmpty();
    }
    public Entry<K,V> insert (K key, V value){
        if (key == null) throw new InvalidKeyException("Clave nula no permitida");
        Entry<K,V> e= new TDAEntry<K,V>(key, value);
        D.addLast(e);
        return e;
    }
    public Entry<K,V> find (K key){
        if (key == null) throw new InvalidKeyException("Clave nula no permitida");
        for (Entry<K,V> e : D){
            if (e.getKey().equals(key)){
                return e;
            }
        }
        return null;
    }
    public Iterable<Entry<K,V>> findAll(K key){
        if (key == null) throw new InvalidKeyException("Clave nula no permitida");
        PositionList<Entry<K,V>> lista= new ListaDoblementeEnlazada<>();
        for (Entry<K,V> e : D){
            if(e.getKey().equals(key)){
                lista.addLast(e);
            }
        }
        return lista;
    }
    public Entry<K,V> remove (Entry <K,V> e){
        if (e.getKey() == null) throw new InvalidKeyException("Clave nula no permitida");
        for(Position<Entry<K,V>> p: D.positions()){
            if (p.element()==e){
                D.remove(p);
                return e;
            }
        } 
    return null;
    }
    public Iterable<Entry<K,V>> entries(){
        return D;
    }
}
