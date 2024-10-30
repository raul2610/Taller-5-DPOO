package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {
	
	private Pedido pedidoTest;
	private Pedido pedidoTest2;
	private Producto producto1;
	private Producto producto2;
	private Producto producto3;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	Pedido.numeroPedidos = 0;
    	pedidoTest = new Pedido("Raúl Ruiz", "Carrera 7 con calle 92");
    	pedidoTest2 = new Pedido("Camila Ruiz", "Carrera 9 con calle 94");
    	producto1 = new ProductoMenu("Corral", 13000);
    	producto2 = new ProductoMenu("Callejera", 16000);
    	producto3 = new ProductoMenu("Hawaiana", 14900);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    @DisplayName("Probar getIdPedido()")
    void testGetIdPedido() {
    	assertEquals(1, pedidoTest.getIdPedido(), "No se está generando el id correcto al pedido.");
    	assertEquals(2, pedidoTest2.getIdPedido(), "No se está generando el id correcto al pedido.");
    }
    
    @Test
    @DisplayName("Probar getNombreCliente()")
    void testGetNombreCliente() {
    	assertEquals("Raúl Ruiz", pedidoTest.getNombreCliente(), "El nombre del cliente no es el correcto.");
    }
    
    @Test
    @DisplayName("Probar agregarProducto() y getCantidadProductos()")
    void testAgregarProducto() {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	
    	int cantidadProductos = pedidoTest.getCantidadProductos();
    	assertEquals(3, cantidadProductos, "No se agrego la cantidad correcta de productos y/o no consigue la cantidad correcta de productos.");
    }
    
    @Test
    @DisplayName("Probar agregarProducto() y getCantidadProductos() pero esta vacio")
    void testAgregarProductoVacio() {
    	int cantidadProductos = pedidoTest.getCantidadProductos();
    	assertEquals(0, cantidadProductos, "No se agrego la cantidad correcta de productos y/o no consigue la cantidad correcta de productos.");
    }
    
    @Test
    @DisplayName("Probar getPrecioNetoPedido()")
    void testGetPrecioNetoPedido() {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	// Valor total: 13000+16000+14900 = 43900
    	
    	assertEquals(43900, pedidoTest.getPrecioNetoPedido(), "El precio neto sin iva de los productos es incorrecto.");
    	
    	// Probamos si esta vacio...
    	assertEquals(0, pedidoTest2.getPrecioNetoPedido(), "El precio neto de un pedido vacio debe ser 0.");
    }
    
    @Test
    @DisplayName("Probar getPrecioIVAPedido()")
    void testGetPrecioIVAPedido() {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	// Valor total: 13000+16000+14900 = 43900.
    	// El iva al 19% --> 43900*0.19 = 8341
    	
    	assertEquals(8341, pedidoTest.getPrecioIVAPedido(), "El precio del iva de los productos es incorrecto.");
    	
    	// Probamos si esta vacio...
    	assertEquals(0, pedidoTest2.getPrecioIVAPedido(), "El precio del iva de un pedido vacio debe ser 0.");
    }
    
    @Test
    @DisplayName("Probar getPrecioTotalPedido()")
    void testGetPrecioTotalPedido() {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	// Valor total: 13000+16000+14900 = 43900.
    	// El iva al 19% --> 43900*0.19 = 8341
    	// Total: 52241
    	
    	assertEquals(52241, pedidoTest.getPrecioTotalPedido(), "El precio total del pedido es incorrecto.");
    	
    	// Probamos si esta vacio...
    	assertEquals(0, pedidoTest2.getPrecioTotalPedido(), "El precio total de un pedido vacio debe ser 0.");
    }
    
    @Test
    @DisplayName("Probar generarTextoFactura()")
    void testGenerarTextoFactura() {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	// Valor total: 13000+16000+14900 = 43900.
    	// El iva al 19% --> 43900*0.19 = 8341
    	// Total: 52241
    	
    	String stringDatos = "Cliente: Raúl Ruiz\nDirección: Carrera 7 con calle 92\n----------------\n";
    	// Conseguimos el string de los productos de tipo ProductoMenu
    	String stringProductos = producto1.generarTextoFactura()+producto2.generarTextoFactura()+producto3.generarTextoFactura();
    	// Construimos el string conjunto
    	String stringValores = "----------------\nPrecio Neto:  43900\nIVA:          8341\nPrecio Total: 52241\n";
    	// String total
    	String stringTotal = stringDatos+stringProductos+stringValores;
    	
    	assertEquals(stringTotal, pedidoTest.generarTextoFactura(), "El texto del pedido se genero de manera incorrecta.");
    
    	// Probamos para un pedido vacio
    	String stringDatos2 = "Cliente: Camila Ruiz\nDirección: Carrera 9 con calle 94\n----------------\n";
    	String stringValores2 = "----------------\nPrecio Neto:  0\nIVA:          0\nPrecio Total: 0\n";
    	String stringTotal2 = stringDatos2+stringValores2;
    	assertEquals(stringTotal2, pedidoTest2.generarTextoFactura(), "El texto de un pedido vacio es incorrecto.");
    }
    
    @Test
    @DisplayName("Probar guardarFactura()")
    void testGuardarFactura() throws IOException {
    	// Agregamos los productos
    	pedidoTest.agregarProducto(producto1);
    	pedidoTest.agregarProducto(producto2);
    	pedidoTest.agregarProducto(producto3);
    	// Vamos a guardar en facturas.txt dentro de la carpeta data.
    	File archivo = new File("data/facturasTests.txt");
    	pedidoTest.guardarFactura(archivo);
    	
        // Lee el contenido del archivo generado
        StringBuilder valoresArchivo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
            	valoresArchivo.append(linea).append("\n");
            }
        }
        
        String textoFacturaEsperado = pedidoTest.generarTextoFactura();
        assertEquals(textoFacturaEsperado, valoresArchivo.toString(), "El contenido de la factura guardada es incorrecto.");
    }
    
    @Test
    @DisplayName("Probar FileNotFoundException en guardarFactura()")
    void testGuardarFacturaLanzaExcepcion() {
        // Usar una ruta de archivo loca
        File archivoNoValido = new File("/carpeta/que/no/es/valida/hola.txt");

        assertThrows(FileNotFoundException.class, () -> {
            pedidoTest.guardarFactura(archivoNoValido);
        }, "Se esperaba una excepción FileNotFoundException al intentar guardar en una ruta no válida");
    }
    
}
