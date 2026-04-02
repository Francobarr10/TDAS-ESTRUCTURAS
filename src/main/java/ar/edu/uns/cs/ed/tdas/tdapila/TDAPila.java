package ar.edu.uns.cs.ed.tdas.tdapila;
import java.util.EmptyStackException;
/**
 * Implementación de la interfaz Stack utilizando un arreglo.
 * @author Cátedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computación, UNS.
 */
public class TDAPila<E> implements Stack<E> {
private E [] datos;
private int tope;
private static final int CAPACIDAD_INICIAL=10;

public TDAPila(){
    datos=(E[])new Object[CAPACIDAD_INICIAL];
    this.tope=-1;
}
public void push(E e){
if (datos==null)throw new IllegalArgumentsException("No se permite null");
if (tope==datos.length-1){
    redimensionar();
}
tope++;
datos[tope]=e;
}
public E pop(){
    if (isEmpty())throw new EmptyStackException("pila vacia");
    E elemento = datos[tope];
    datos[tope]=null;
    return elemento;
}
public E tope(){
    if (isEmpty()) throw new EmptyStackException("pila Vacia");
    return datos[tope];
}
public boolean isEmpty(){
    return tope==-1;
}
public int size(){
    return tope+1;
}
private void redimensionar(){
    int nuevacapacidad = datos.length *2;
    E[]nuevoarreglo =(E[]) new Object[nuevacapacidad];
    for (int i=0;i<=tope;i++){
        nuevoarreglo[i]=datos[i];
    }
    datos=nuevoarreglo;
}
/*Lo que hace redimensionar es crear un nuevo arreglo con el doble de 
capacidad, copiar los elementos del arreglo original al nuevo arreglo
y luego asignar el nuevo arreglo a la variable datos. 
Esto permite que la pila pueda crecer dinámicamente cuando se 
alcance su capacidad máxima. 
*/
}