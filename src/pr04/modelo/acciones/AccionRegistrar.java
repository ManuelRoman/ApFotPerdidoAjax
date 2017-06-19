package pr04.modelo.acciones;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import pr04.controlador.Accion;
import pr04.dao.BeanDaoInsercionImpl;
import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.ModeloAjax;
import pr04.modelo.beans.Usuario;

/**
 * Clase que encapsula el proceso de registro de un usuario, responde a una petición ajax.
 */
public class AccionRegistrar implements Accion{

	/**
	 * Unidad de persistencia que se empleará para acceder a la base de datos.
	 * @uml.property  name="UP"
	 */
	private String UP = null;
	/**
	 * Bean de error para situaciones en los que el método ejecutar() devuelve false.
	 * @uml.property  name="error"
	 * @uml.associationEnd  
	 */
	private BeanError error = null;
	/**
	 * Objeto que encapsula el modelo que procesará la vista.
	 * @uml.property  name="modelo"
	 */
	private Object modelo = null;
	/**
	 * Página JSP que se devuelve como "vista" del procesamiento de la acción.
	 * @uml.property  name="vista"
	 */
	private String vista = null;
	
	/**
	 * Contexto de aplicación.
	 */
	private ServletContext Sc;

	/** 
	 * Ejecuta el proceso asociado a la acción.
	 * @param request Objeto que encapsula la petición.
	 * @param response Objeto que encapsula la respuesta.
	 * @return true o false en función de que no se hayan producido errores o lo contrario.
	 * @see fotperdido.controlador.Accion#ejecutar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean ejecutar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean estado = true;
		ModeloAjax modelo = new ModeloAjax();
		HttpSession sesion = request.getSession();
		String json = null;
		error = null;
		String login = request.getParameter("login");
		String clave = request.getParameter("clave");
		System.out.println("ha soliciado registrar el login: " + login);
		Usuario usuario = new Usuario(login, clave);
		BeanDaoInsercionImpl daoInsercion = (BeanDaoInsercionImpl) sesion.getAttribute("daoInsercion");
		if (daoInsercion == null){
			daoInsercion = new BeanDaoInsercionImpl(this.UP);
			sesion.setAttribute("daoInsercion", daoInsercion);
		}
		try {
			daoInsercion.getConexion();
			daoInsercion.insertaUsuario(usuario);
		} catch (RollbackException e) {
			error = new BeanError(3, "El Usuario ya existe, pruebe con otro");
			e.printStackTrace();
		} catch (EntityExistsException e) {
			error = new BeanError("La base de datos no está operativa, intentelo más tarde EntityExistsException",e);
			e.printStackTrace();
		} catch (IllegalStateException e) {
			error = new BeanError("La base de datos no está operativa, intentelo más tarde IllegalStateException",e);
			e.printStackTrace();
		} catch (Exception e) {
			error = new BeanError("La base de datos no está operativa, intentelo más tarde Exception",e);
			e.printStackTrace();
		} finally {
			try {
				daoInsercion.close();
			} catch (Exception e) {
				System.out.println("Error al cerrar la conexión.");
				e.printStackTrace();
			}
		}
		if (error == null) {
			sesion.setAttribute("usuario", usuario);
			json = new Gson().toJson("");
		} 
		else {
			json = new Gson().toJson(error.getMensError());
		}
		modelo.setContentType("application/json;charset=UTF-8");
		modelo.setRespuesta(json);
		this.setModelo(modelo);
		System.out.println("json a enviar: "+json.toString());
		return estado;
	}

	/**
	 * Devuelve el error asociado a la acción, si lo hubiera.
	 * @return
	 * @uml.property  name="error"
	 */
	@Override
	public Exception getError() {
		return error;
	}

	/**
	 * Devuelve el objeto modelo
	 * @return
	 * @uml.property  name="modelo"
	 */
	@Override
	public Object getModelo() {
		return modelo;
	}
	
	/**
	 * Método setter para la propiedad modelo.
	 * @param modelo  El modelo a establecer.
	 * @uml.property  name="modelo"
	 */
	private void setModelo(Object modelo) {
		this.modelo = modelo;
	}	

	/**
	 * Devuelve la vista que debe procesar el modelo. En caso de ser
	 * una petición AJAX, la vista deberá ser null.
	 * @return
	 * @uml.property  name="vista"
	 */
	@Override
	public String getVista() {
		return vista;
	}
	
	/**
	 * Método setter para la propiedad vista.
	 * @param vista  La vista a establecer.
	 * @uml.property  name="vista"
	 */
	private void setVista(String vista) {
		this.vista = vista;
	}
	
	/**
	 * Método getter para la propiedad UP (unidad de persistencia).
	 * @return  La unidad de persistencia UP.
	 * @uml.property  name="UP"
	 */
	private String getUP() {
		return UP;
	}
	
	/**
	 * Establece el valor de la unidad de persistencia
	 * @param up
	 * @uml.property  name="UP"
	 */
	@Override
	public void setUP(String up) {
		this.UP = up;
	}

	/**
	 * Establece el contexto de aplicación
	 * @param sc
	 * @uml.property  name="sc"
	 */
	@Override
	public void setSc(ServletContext sc) {
		this.Sc = sc;
	}

}
