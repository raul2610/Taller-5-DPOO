package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {

	private ProductoMenu ingredienteBase;
	private ProductoAjustado ingredienteTest;
	private ProductoAjustado ingredienteTest2;
	private Ingrediente ingredienteAdicional1;
	private Ingrediente ingredienteAdicional2;
	private Ingrediente ingredienteAdicional3;
	private Ingrediente ingredienteAEliminar1;
	private Ingrediente ingredienteAEliminar2;
	private Ingrediente ingredienteAEliminar3;
	

    @BeforeEach
    void setUp( ) throws Exception
    {
        ingredienteBase = new ProductoMenu("Papas medianas", 5200);
        ingredienteTest = new ProductoAjustado(ingredienteBase);
        ingredienteAdicional1 = new Ingrediente("Extra carne", 7000);
        ingredienteAdicional2 = new Ingrediente("Salsas", 900);
        ingredienteAdicional3 = new Ingrediente("Gaseosa XL", 2000);
        ingredienteTest2 = new ProductoAjustado(ingredienteBase);
        ingredienteAEliminar1 = new Ingrediente("Cebolla", 1600);
        ingredienteAEliminar2 = new Ingrediente("Queso", 3100);
        ingredienteAEliminar3 = new Ingrediente("Pepinillos", 2400);
        
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
    @DisplayName("Probar agregarIngrediente() y getAgregados()")
    void testAgregarIngrediente() {
    	// Agregamos al ingredienteTest los adicionales
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional3);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	
    	// Lista adicionales
    	List<Ingrediente> listaAgregados = ingredienteTest.getAgregados();
    	int sizeAdicionales = listaAgregados.size();
    	
    	assertEquals(5, sizeAdicionales, "La cantidad de ingredientes adicionales no coincide con la ingresada.");
    	
    	//Prueba sin ingredientes adicionales, usamos ingredienteTest2
    	List<Ingrediente> listaAgregados2 = ingredienteTest2.getAgregados();
    	int sizeVacio = listaAgregados2.size();
    	assertEquals(0, sizeVacio, "La cantidad de ingredientes adicionales no coincide con la ingresada, debía ser 0.");
    }
    
    @Test
    @DisplayName("Probar eliminarIngrediente() y getEliminados()")
    void testEliminarIngrediente() {
    	// Eliminamos al ingredienteTest 
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar1);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar2);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar3);

    	// Lista eliminados
    	List<Ingrediente> listaEliminados = ingredienteTest.getEliminados();
    	int sizeEliminados = listaEliminados.size();
    	
    	assertEquals(3, sizeEliminados, "La cantidad de ingredientes eliminados no coincide con la ingresada.");
    	
    	//Prueba sin ingredientes eliminados, usamos ingredienteTest2
    	List<Ingrediente> listaEliminados2 = ingredienteTest2.getEliminados();
    	int sizeVacio = listaEliminados2.size();
    	assertEquals(0, sizeVacio, "La cantidad de ingredientes eliminados no coincide con la ingresada, debía ser 0.");
    }
    
    @Test
    @DisplayName("Probar getPrecio()")
    void testGetPrecio() {
    	// Agregamos al ingredienteTest los adicionales
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional3);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	// Suma de ingredientes adicionales es 17,800. Las papas base cuestan 5200. Total 23,000
    	assertEquals (23000, ingredienteTest.getPrecio(), "El precio no es correcto.");
    }
    
    // Los siguientes tests corresponden a generarTextoFactura()
    
    @Test
    @DisplayName("Probar generarTextoFactura() pero sin agregados ni eliminados")
    void testGenerarTextoFacturaSinIngredientes() {
        String string = "Papas medianas            5200\n";
        assertEquals(string, ingredienteTest.generarTextoFactura(), "El texto de la factura no es el esperado para un producto sin ingredientes");
    }

    @Test
    @DisplayName("Probar generarTextoFactura() pero con agregados y sin eliminados")
    void testGenerarTextoFacturaConIngredientesAdicionales() {
    	// Agregamos al ingredienteTest los adicionales
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional3);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	// Total: 7000+900+2000+900+5200 = 16000

        String string = "Papas medianas    +Extra carne                7000    +Salsas                900    +Gaseosa XL                2000    +Salsas                900            16000\n";
        assertEquals(string, ingredienteTest.generarTextoFactura(), "El texto de la factura no es el esperado para un producto con ingredientes adicionales");
    }

    @Test
    @DisplayName("Probar generarTextoFactura() pero con eliminados y sin agregados")
    void testGenerarTextoFacturaConIngredientesEliminados() {
    	// Eliminamos al ingredienteTest 
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar1);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar2);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar3);

    	String string = "Papas medianas    -Cebolla    -Queso    -Pepinillos            5200\n";
        assertEquals(string, ingredienteTest.generarTextoFactura(), "El texto de la factura no es el esperado para un producto con ingredientes adicionales");
    }

    @Test
    @DisplayName("Probar generarTextoFactura() pero con eliminados y agregados")
    
    void testGenerarTextoFacturaConIngredientesAdicionalesYEliminados() {
    	// Agregamos al ingredienteTest los adicionales
    	ingredienteTest.agregarIngrediente(ingredienteAdicional1);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional3);
    	ingredienteTest.agregarIngrediente(ingredienteAdicional2);
    	// Total: 7000+900+2000+900+5200 = 16000
    	
    	// Eliminamos al ingredienteTest 
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar1);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar2);
    	ingredienteTest.eliminarIngrediente(ingredienteAEliminar3);

    	String string = "Papas medianas    +Extra carne                7000    +Salsas                900    +Gaseosa XL                2000    +Salsas                900    -Cebolla    -Queso    -Pepinillos            16000\n";
        assertEquals(string, ingredienteTest.generarTextoFactura(), "El texto de la factura no es el esperado para un producto con ingredientes adicionales y eliminados");
    }
	
}
