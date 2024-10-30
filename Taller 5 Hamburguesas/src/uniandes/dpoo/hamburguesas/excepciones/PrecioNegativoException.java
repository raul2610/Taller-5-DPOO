package uniandes.dpoo.hamburguesas.excepciones;

@SuppressWarnings("serial")
public class PrecioNegativoException extends HamburguesaException{
	
	@Override
    public String getMessage( )
    {
        return "No puede haber un precio negativo.";
    }
	
}
