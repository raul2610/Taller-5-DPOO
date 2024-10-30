package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.PrecioNegativoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
	private Combo comboTest;
	private ProductoMenu hamburguesaProductoMenu;
	private ProductoMenu papasMedianasProductoMenu;
	private ProductoMenu gaseosaProductoMenu;
	private ArrayList<ProductoMenu> itemsBase;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	hamburguesaProductoMenu = new ProductoMenu("Hamburguesa Callejera", 28000);
        papasMedianasProductoMenu = new ProductoMenu("Papas medianas", 5200);
        gaseosaProductoMenu = new ProductoMenu("Gaseosa 250ml", 3000);
        
        itemsBase = new ArrayList<ProductoMenu>();
        
        itemsBase.add(hamburguesaProductoMenu);
        itemsBase.add(papasMedianasProductoMenu);
        itemsBase.add(gaseosaProductoMenu);

        comboTest = new Combo("Combo Callejera", 0.10, itemsBase);
        
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test 
    @DisplayName("Probar getNombre()")
    void testGetNombre() {
    	assertEquals ("Combo Callejera", comboTest.getNombre(), "El nombre no es correcto.");
    }
    
    @Test
    @DisplayName("Probar getPrecio()")
    void testGetPrecio() {
    	// El total del Combo Callejera sin descuentos es de 36,200. El 10% de descuento es de 3,620.
    	// Valor del combo: 32,580.
    	assertEquals (32580, comboTest.getPrecio(), "El precio no es correcto.");
    }
    
    @Test
    @DisplayName("Probar que no se ingrese un descuento negativo o superior al 100%")
    void testDescuento() {
    	assertThrows(PrecioNegativoException.class, () -> {
            new Combo("Corral", -1, itemsBase);
        }, "Se esperaba una excepción PrecioNegativoException al crear un producto con precio negativo");
    }
    
    @Test
    @DisplayName("Probar generarTextoFactura()")
    
    void testGenerarTextoFactura() {
    	String string = "Combo Combo Callejera\n Descuento: 10.0%\n            32580\n";
        assertEquals(string, comboTest.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }   
}
