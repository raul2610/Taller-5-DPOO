package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.PrecioNegativoException;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {
	
	private ProductoMenu ingredienteTest;

    @BeforeEach
    void setUp( ) throws Exception
    {
        ingredienteTest = new ProductoMenu( "Papas medianas", 5200 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test 
    @DisplayName("Probar getNombre()")
    void testGetNombre() {
    	assertEquals ("Papas medianas", ingredienteTest.getNombre(), "El nombre no es correcto.");
    }
    
    @Test
    @DisplayName("Probar getPrecio()")
    void testGetPrecio() {
    	assertEquals (5200, ingredienteTest.getPrecio(), "El precio no es correcto.");
    }
    
    @Test
    @DisplayName("Probar que precios no sean negativos.")
    void testPrecioNegativoLanzaException() {
        assertThrows(PrecioNegativoException.class, () -> {
            new ProductoMenu("Papas grandes", -1);
        }, "Se esperaba una excepción PrecioNegativoException al crear un producto con precio negativo");
    }
    
    @Test
    @DisplayName("Probar generarTextoFactura()")
    void testGenerarTextoFactura () {
    	String texto = "Papas medianas\n            5200\n";
    	assertEquals(texto, ingredienteTest.generarTextoFactura(), "El texto generado no corresponde al aceptado.");
    }
}
