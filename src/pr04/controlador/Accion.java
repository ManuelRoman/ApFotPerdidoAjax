package pr04.controlador;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interfaz que implementarán todas las acciones.
 * @author Eduardo A. Ponce
 * @version Ajax-MVC2
 *
 */
public interface Accion {
	
	  /**
	   * Ejecuta el proceso asociado a una acción.
	   * @param request La petición
	   * @param response La respuesta
	   * @return True o false en función de que se ejecute correctamente o no.
	   * @throws ServletException Error en ejecución del Servlet
	   * @throws IOException Error en entrada o salida.
	   */
	  public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException;
	  
	  /**
	   * Devuelve la vista establecida por la acción.
	   * @return Cadena con el nombre de la vista que debe proporcionarse.
	   */
	  public String getVista();
	  
	  /**
	   * Devuelve el objeto que representa el modelo que procesará la vista.
	   * @return Un Object que representa el modelo a procesar por la vista.
	   */
	  public Object getModelo();
	  
	  /**
	   * Establece el contexto de aplicación sobre el que trabajará la acción.
	   * @param sc Contexto de aplicación.
	   */
	  public void setSc(ServletContext sc);
	  
	  /**
	   * Devuelve el objeto que encapsula una situación de error grave, y que será
	   * procesada de manera especial.
	   * @return Objeto de tipo Exception que representa la situación de error.
	   */
	  public Exception getError();
	  
	  /**
	   * Establece el datasource a partir del cual la acción podrá comunicarse con 
	   * la base de datos correspondiente.
	   * @param ds Datasource
	   */
	  public void setUP(String upds);

}
