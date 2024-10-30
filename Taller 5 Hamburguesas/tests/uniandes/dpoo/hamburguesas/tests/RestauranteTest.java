package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {
	
	private Restaurante restauranteTest;
	private Restaurante restauranteTest2;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	restauranteTest = new Restaurante();
    	restauranteTest2 = new Restaurante();

    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    @DisplayName("Probar inciarPedido(), getPedidoEnCurso() y YaHayUnPedidoEnCursoException")
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
    	// Iniciamos el primer pedido
    	restauranteTest.iniciarPedido("Raúl Ruiz", "Carrera 7 con calle 92");
    	
    	// Probamos getPedidoEnCurso()
    	assertNotNull(restauranteTest.getPedidoEnCurso(), "No debería mandar null ya que se inicio un pedido en curso.");
    	
    	// Iniciamos un segundo pedido para probar la excepcion
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restauranteTest.iniciarPedido("Camila Ruiz", "Carrera 9 con calle 94");
        }, "Debio arrojar YaHayUnPedidoEnCursoException al iniciar un nuevo pedido cuando ya hay uno activo.");
    	
    }
    
    @Test
    @DisplayName("Probar NoHayPedidoEnCursoException en cerrarYGuardarPedido()")
    void testNoHayPedidoEnCursoException() throws NoHayPedidoEnCursoException {
    	assertThrows(NoHayPedidoEnCursoException.class, () -> {
    		restauranteTest.cerrarYGuardarPedido();
    	}, "Al no haber ningun pedido en curso (null), debe arrojar NoHayPedidoEnCursoException.");
    }
    
    @Test
    @DisplayName("Probar cerrarYGuardarPedido()")
    void testCerrarYGuardarPedido() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
    	// Iniciamos el primer pedido
    	restauranteTest.iniciarPedido("Raúl Ruiz", "Carrera 7 con calle 92");
    	int idPedido = restauranteTest.getPedidoEnCurso().getIdPedido();
    	restauranteTest.cerrarYGuardarPedido();
    	// Revisamos que no haya pedido en curso (null)
    	assertNull(restauranteTest.getPedidoEnCurso(), "Ya que se cerro y guardo un pedido, no se inicio otro, no debe haber un pedido en curso.");
    	String archivo = "factura_"+idPedido+".txt";
    	File nuevoArchivoPedido = new File("facturas/"+archivo);
    	// Si se cerro y guardo, debemos poder entrar a la direccion y saber si existe.
    	assertTrue(nuevoArchivoPedido.exists(), "Ya debería existir el archivo, no se está creando correctamente.");
    	// Revisa que la factura no sea un documento vacio.
    	assertTrue(nuevoArchivoPedido.length()>0, "La factura del pedido no debería estar vacia.");
    }
    
    @Test
    @DisplayName("Probar getPedidos()")
    void testGetPedidos() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
    	// Creamos y cerramos unos pedidos
    	restauranteTest.iniciarPedido("Sebastián Ruiz", "Carrera 10 con calle 92");
    	restauranteTest.cerrarYGuardarPedido();
    	restauranteTest.iniciarPedido("Manuela Rojas", "Carrera 11 con calle 95");
    	restauranteTest.cerrarYGuardarPedido();
    	restauranteTest.iniciarPedido("George Bush", "Carrera 9 con calle 11");
    	restauranteTest.cerrarYGuardarPedido();
    	// Dejamos uno abierto para verificar que no reciba el pedido cerrado.
    	ArrayList<Pedido> pedidosCerrados = restauranteTest.getPedidos();
    	assertEquals(3, pedidosCerrados.size(), "La cantidad de pedidos cerrados es incorrecta.");
    	
    	// Si tenemos otro restaurante sin pedidos debe ser 0.
    	ArrayList<Pedido> pedidosCerradosVacio = restauranteTest2.getPedidos();
    	assertEquals(0, pedidosCerradosVacio.size(), "La cantidad de pedidos cerrados cuando no hay pedidos debe ser 0.");
    }
    
    @Test
    @DisplayName("Probar cargarInformacionRestaurante(). Incluye cargarIngredientes(), cargarCombos() y cargarMenu(). Tambien verifica los getCantidad...()")
    void testCargarInformacionRestaurante() throws Exception {
    	File archivoIngredientes = new File("data/ingredientes.txt");
    	File archivoMenu = new File("data/menu.txt");
    	File archivoCombos = new File("data/combos.txt");
    	
    	restauranteTest.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
    	assertEquals(15, restauranteTest.getIngredientes().size(), "No se cargo la totalidad de ingredientes.");
    	assertEquals(22, restauranteTest.getMenuBase().size(), "No se cargo la totalidad de productos del menu.");
    	assertEquals(4, restauranteTest.getMenuCombos().size(), "No se cargo la totalidad de combos.");
    }
    
    @Test
    @DisplayName("Probar IngredienteRepetidoException en cargarInformacionRestaurante()")
    void testIngredienteRepetidoException() {
    	File archivoIngredientes = new File("data/ingredientesRepetidosTest.txt");
    	File archivoMenu = new File("data/menu.txt");
    	File archivoCombos = new File("data/combos.txt");
    	
    	assertThrows(IngredienteRepetidoException.class, () -> {
        restauranteTest.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
    	}, "Deberia mandar IngredienteRepetidoException cuando un ingrediente está repetido.");
    }
	
    @Test
    @DisplayName("Probar ProductoRepetidoException en cargarInformacionRestaurante()")
    void testProductoRepetidoException() {
    	File archivoIngredientes = new File("data/ingredientes.txt");
    	File archivoMenu = new File("data/menuRepetidosTest.txt");
    	File archivoCombos = new File("data/combos.txt");
    	
    	assertThrows(ProductoRepetidoException.class, () -> {
    		restauranteTest.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
    	}, "Deberia mandar ProductoRepetidoException cuando un combo está repetido.");
    }
    
    @Test
    @DisplayName("Probar ProductoFaltanteException en cargarInformacionRestaurante()")
    void testProductoFaltanteException() {
    	File archivoIngredientes = new File("data/ingredientes.txt");
    	File archivoMenu = new File("data/menu.txt");
    	File archivoCombos = new File("data/combosProductoFaltante.txt");
    	
    	assertThrows(ProductoFaltanteException.class, () -> {
    		restauranteTest.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
    	}, "Deberia mandar ProductoFaltanteException cuando un combo está repetido.");
    }
}
