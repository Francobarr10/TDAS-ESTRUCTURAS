package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;

import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.TDAEntry;
public class MapHashAbierto<K,V> implements Map<K,V> {

    protected int n; // cantidad de entradas en el mapa
    protected int cant; // tamaño del arreglo de posiciones
    protected PositionList<Entry<K,V>>[] tabla; // arreglo de posiciones}


public MapHashAbierto() {
n=13;
tabla =new PositionList[n];
for (int i = 0; i <n; i ++){
    tabla[i]=new ListaDoblementeEnlazada<>();
}
cant=0;
}
public int size() {
    return cant;
}
public boolean isEmpty() {
    return cant == 0;
}
public V get(K key){
    if (key == null) throw new InvalidKeyException("Clave nula no permitida");
    int i = h(key);
    for (Entry<K,V> e: tabla[i]){
      if (e.getKey().equals(key)){ 
        return e.getValue();
      }    
    }
    return null;
}
private int h(K key){
    return Math.abs(key.hashCode()) % n;
}
public V put(K key, V value){
    if (key == null) throw new InvalidKeyException("Clave nula no permitida");
    int i = h(key);
  for (Entry<K,V> e : tabla[i]){
    if (e.getKey().equals(key)){
      V valorviejo = e.getValue();
      // Note: This assumes Entry has a setValue method
      // If not, you might need to create a new Entry or update the existing one differently
      return valorviejo;
    }
  }
  tabla[i].addLast(new TDAEntry<>(key, value));
  cant++;
  return null;
}
public V remove(K key){
    if (key == null) throw new InvalidKeyException("Clave nula no permitida");
  int i = h(key);
  for (Position<Entry<K,V>> p: tabla[i].positions()){
    if (p.element().getKey().equals(key)){
      V valorviejo = p.element().getValue();
      tabla[i].remove(p);
      cant--;
      return valorviejo;
    }
  }
  return null;
}
public Iterable<K> keys(){
  PositionList<K> claves = new ListaDoblementeEnlazada<>();

  for (int i = 0; i < n; i++){
    for (Entry<K,V> e: tabla[i]){
      claves.addLast(e.getKey());
    }
  }
  return claves;
}
public Iterable<V> values(){
  PositionList<V> valores = new ListaDoblementeEnlazada<>();
  for (int i = 0; i < n; i++){
    for (Entry<K,V> e: tabla[i]){
      valores.addLast(e.getValue());
    }
  }
  return valores;
}
public Iterable<Entry<K,V>> entries(){
  PositionList<Entry<K,V>> entradas = new ListaDoblementeEnlazada<>();
  for (int i = 0; i < n; i++){
    for (Entry<K,V> e: tabla[i]){
      entradas.addLast(e);
    }
  }
  return entradas;
}


}
    

