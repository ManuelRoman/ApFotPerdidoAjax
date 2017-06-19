package pr04.controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Permite crear un objeto de ayuda que acepta el objeto de solicitud
 * en su constructor. Proporciona funcionalidad adicional a la solicitud.
 */
public class AyudaSolicitud {
	
	/**
	 * La petición del usuario, que incorporará el parámetro acción
	 * que determina el proceso a llevar a cabo.
	 */
	HttpServletRequest request;
	
	/**
	 * Constructor que prepara el objeto para procesar la acción de la
	 * petición.
	 * @param request La petición.
	 * @throws ServletException Excepción grave a nivel de Servlet
	 */
	public AyudaSolicitud(HttpServletRequest request)
	    throws ServletException
	  {
	    this.request = request;
	  }
	/**
	 * Devuelve el objeto de tipo Accion que deberá procesar la petición.
	 * @return Obtejo de tipo Accion
	 */
	public Accion getAccion(String archivoAcciones)
	  {
	    String accion = (String) request.getParameter("accion");
	    // System.out.println("Accion: "+accion);
	    // Antes de crear la acción puede haber procesamiento previo de la
	    // solicitud, para dejarla preparada para el método creaAccion.
	    return FactoriaAcciones.creaAccion(accion, archivoAcciones);
	  }
	
}
