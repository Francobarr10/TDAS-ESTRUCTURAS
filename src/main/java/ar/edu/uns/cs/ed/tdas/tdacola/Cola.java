package ar.edu.uns.cs.ed.tdas.tdacola;
import java.util.EmptyStackException;
/**
 * Implementación de la interfaz Queue utilizando un arreglo.
 * @author Cátedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computación, UNS.
 */
public class Cola<E> implements Queue<E> {
private E [] datos;
private int frente;
private int fondo;
private int cantidad;
private static final int CAPACIDAD_INICIAL=10;
public Cola(){
    datos= (E[]) new Object[CAPACIDAD_INICIAL];
    this.cantidad=0;//cantidad es 0 porque la cola esta vacia al principio
    this.frente=0;//frente es 0 porque el primer elemento de la cola se encuentra en la posición 0 del arreglo
    this.fondo=-1;//fondo es -1 porque no hay elementos en la cola, por lo que el fondo no apunta a ningún elemento válido
}
public void enqueue (E e){
    datos[fondo]=e;
    //SI ESTA LLENA REDIMENSIONO
    if (cantidad==datos.length-1){
        redimensionar();
    }
    //movemos de forma circular el fondo de la cola
    fondo=(fondo+1)%datos.length;//esto se hace para que el fondo vuelva al inicio del arreglo cuando llegue al final, creando un efecto de cola circular.
    datos[fondo]=e;
    cantidad++;
}
public E dequeue(){
    if (isEmpty())throw new EmptyQUeueException("la cola esta vacia");
    E elemento= datos[frente];
    datos[frente]=null

    //movemos frente en forma circular
    frente=(frente+1)%datos.length;
    cantidad--;

    return elemento;
}
public E front();
if(isEmpty())throw new EmptyQueueException("la pila Esta Vacia");
E elemento =datos[frete];
return elemento;
public boolean isEmpty(){
    return cantidad==0;
}
public int size(){
    return cantidad;
}
private void redimensionar(){
    int nuevacapacidad=datos.length*2;
    E[] nuevosDatos = (E[]) new Object[nuevacapacidad];
    for (int i = 0; i < cantidad; i++) {
        nuevosDatos[i] = datos[(frente + i) % datos.length];//esto se hace para copiar los elementos de la cola al nuevo arreglo en el orden correcto, teniendo en cuenta que el frente puede estar en cualquier posición del arreglo original debido a la naturaleza circular de la cola.
    }
    datos = nuevosDatos;//asignamos el nuevo arreglo al atributo datos  
    frente = 0;//frente se reinicia a 0 porque el primer elemento de la cola ahora se encuentra en la posición 0 del nuevo arreglo
    fondo = cantidad - 1;//fondo se actualiza para apuntar al último elemento de la cola, que ahora se encuentra en la posición cantidad - 1 del nuevo arreglo
}
