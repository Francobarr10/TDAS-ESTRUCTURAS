package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.TDAEntry;
public class TDAMapeo<K,V> implements Map<K,V> {
  protected PositionList<Entry<K, V>> M;  
  public TDAMapeo() {
    M = new ListaDoblementeEnlazada<>();
  }
public int size() {
    return M.size();
}
public boolean isEmpty() {
    return M.isEmpty();
}
public V get(K key){
    for (Entry<K,V> e: M){
      if (e.getKey().equals(key)){ 
        return e.getValue();
      }    
    }
    return null;
}
public V put(K key, V value){
  for (Position<Entry<K,V>> p : M.positions()){
    if (p.element().getKey().equals(key)){
      V valorviejo = p.element().getValue();
      p.element().setValue(value);
      return valorviejo;
    }
  }
  M.addLast(new TDAEntry<>(key, value));
  return null;
}
public V remove(K key){
  for (Position<Entry<K,V>>p: M.positions()){
    if (p.element().getKey().equals(key)){
      V valorviejo = p.element().getValue();
      M.remove(p);
      return valorviejo;
    }
  }
  return null;
}
public Iterable<K> keys(){
  PositionList<K> claves = new ListaDoblementeEnlazada<>();
  for (Entry<K,V> e: M){
    claves.addLast(e.getKey());
  }
  return claves;
}
public Iterable<V> values(){
  PositionList<V> valores = new ListaDoblementeEnlazada<>();
  for (Entry<K,V> e: M){
    valores.addLast(e.getValue());
  }
  return valores;
}
public Iterable<Entry<K,V>> entries(){
  return M;
}
public 
}