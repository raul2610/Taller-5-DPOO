package uniandes.dpoo.hamburguesas.mundo;

import uniandes.dpoo.hamburguesas.excepciones.PrecioNegativoException;

/**
 * Esta clase se utiliza para mantener la información de un ingrediente que se puede agregar o eliminar de un producto
 */
public class Ingrediente
{
    /**
     * El nombre del ingrediente
     */
    private String nombre;

    /**
     * El costo de agregar el ingrediente a un producto
     */
    private int costoAdicional;

    /**
     * Construye un nuevo ingrediente con un nombre y un costo adicional
     * @param nombre
     * @param costoAdicional
     * @throws PrecioNegativoException 
     */
    public Ingrediente( String nombre, int costoAdicional ) throws PrecioNegativoException
    {
        this.nombre = nombre;
        if (costoAdicional >= 0) {
        	this.costoAdicional = costoAdicional;
        } else {
        	throw new PrecioNegativoException();
        }
    }

    /**
     * Retorna el nombre del ingrediente
     * @return
     */
    public String getNombre( )
    {
        return nombre;
    }

    /**
     * Retorna el costo adicional del ingrediente
     * @return
     */
    public int getCostoAdicional( )
    {
        return costoAdicional;
    }

}
