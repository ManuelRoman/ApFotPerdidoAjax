package pr04.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr04.modelo.beans.ModeloAjax;


/**
 * Implementación del servlet Controlador
 * @author  Eduardo A. Ponce
 * @version  Ajax-MVC2
 */
public class Controlador extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	/**
	 * Informacion de la base de datos.
	 */
	private String unidadPersistencia;
	/**
	 * Objeto que encapsula toda la información a nivel de aplicación.
	 */
	private ServletContext sc;
	
	/**
	 * Archivo de acciones
	 */
	private String archivoAcciones;
	
	/**
	 * Informa si la aplicación ha podido iniciar correctamente
	 */
	private String appOperativa;
	/**
	 * Inicializa el servlet, y le proporciona un objeto, ServletConfig con
	 * información de nivel de aplicación sobre el contexto de datos que rodea
	 * al servlet en el contenedor web.
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// Imprescindible llamar a super.init(config) para tener acceso a la
		// configuración del servlet a nivel de contenedor web.
		super.init(config);
		// En este punto se procedería a obtener las referencias a las fuentes
		// de datos de la aplicación.
		sc = config.getServletContext();
		setUPApp((String) sc.getInitParameter("unidadPersistencia"));
		archivoAcciones = sc.getInitParameter("archivoAcciones");
		sc.setAttribute("upbd", getUPApp());
		this.appOperativa = "true";
		if (!getUPApp().equals("pr04")){
			System.out.println("la unidad de persistencia no es válida.");
			this.appOperativa = "false";
		}
	}
	
	/**
	 * Lo último que se debe hacer antes de que se elimine la instancia del servlet.
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// Elimina el datasource del ámbito de aplicación, liberando todos los
		// recursos que tuviera asignados.
		sc.removeAttribute("upbd");
		sc.removeAttribute("appOperativa");
		// Elimina el ámbito de aplicación.
		sc = null;
	}
	
	/**
     * Procesa las peticiones que vienen por la vía GET.
     * @param request La petición.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Error al ejecutar doPost()
     * @throws java.io.IOException Error de E/S proviniente de doPost()
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se reenvía hacia el método doPost(), ya que tanto las peticiones GET como
	    // las POST se procesarían igual, y de esta manera, se evita código redundante.
	    doPost(request,response);		
	}

	/**
     * Procesa las peticiones que vienen por la vía POST.
     * @param request La petición.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser lanzada por forward().
     * @throws java.io.IOException Puede ser lanzada por forward().
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se comprueba si se tiene acceso a la unidad de presistencia
		if(this.appOperativa.equals("true")){
			//Se obtiene el objeto de ámbito sesión
			HttpSession sesion = request.getSession();
			// Obtener referencia archivo propiedades acciones-clases asociadas
			String urlArchivoAcciones = sc.getRealPath("/WEB-INF/"+archivoAcciones); 
			// Obtener un objeto de ayuda para la solicitud
			AyudaSolicitud ayudaSol = new AyudaSolicitud(request);
			// Crear un objeto de acción partiendo de los parámetros asociados a la solicitud
			Accion accion = ayudaSol.getAccion(urlArchivoAcciones);
			// Se proporciona el contexto del servlet (ámbito aplicación) a la acción
			accion.setSc(sc);
			// Se proporciona el DataSource asociado al servlet a la acciï¿½n
			accion.setUP(unidadPersistencia);
			// Se procesa la solicitud (lógica de empresa)
			if (accion.ejecutar(request,response)) {
				// Si es correcto, obtener el componente relativo a la vista
				String vista = accion.getVista();
				Object modelo = accion.getModelo();
				if (vista!=null) {
					// Añadir en la petición el modelo a visualizar
					request.setAttribute("modelo",accion.getModelo());
					// Enviar la respuesta a la solicitud
					RequestDispatcher rd = request.getRequestDispatcher(vista);
					rd.forward(request,response);
				} else {
					ejecutarAjax (request, response, (ModeloAjax) modelo);
				}
			} else {
				// Si la ejecución ha generado un error, procesarlo mediante el gestor centralizado de errores
				gesError(accion.getVista(),accion.getError(),request,response);
			}
		} else {
		    // Si hay un error al iniciar.
		      gesError("WEB-INF/gesError.jsp",new Exception("Hay un problema en la aplicación, intentelo más tarderrr."),request,response);
		}
	}
	
	/**
     * Reenvía el proceso hacia una vista de gestión de errores.
     * @param vistaError Página que gestionaría el error.
     * @param excepcion Objeto encapsulador de la excepción.
     * @param request La petición.
     * @param response La respuesta.
     * @throws javax.servlet.ServletException Puede ser generada por forward().
     * @throws java.io.IOException Puede ser generada por forward().
     */
	private void gesError(String vistaError, Exception excepcion, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(vistaError);
		request.setAttribute("error",excepcion);
		rd.forward(request,response);
	}
	
	/**
	 * Ejecuta la petición Ajax
	 * @param request La petición.
	 * @param response La respuesta.
	 * @param modelo
	 */
	private void ejecutarAjax (HttpServletRequest request, HttpServletResponse response, ModeloAjax modelo) {
		  PrintWriter out = null;
		  try {
				out = response.getWriter();
				response.setContentType(modelo.getContentType());
				out.println(modelo.getRespuesta());
			} catch (IOException e) {
				System.out.println("Error al obtener el flujo de salida.");
		  }
		  finally {
			  out.close();
		  }
	  }
	
	/**
	 * Establece la fuente de datos para la aplicación.
	 */
	public void setUPApp(String unidadPersistencia) {
		this.unidadPersistencia = unidadPersistencia;
	}

	/**
	 * Obtiene la referencia a la fuente de datos de la aplicación.
	 */
	public String getUPApp() {
		return this.unidadPersistencia;
	}
}
