package pr04.dao;

import java.util.List;

import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.Fotograma;
import pr04.modelo.beans.Ranking;
import pr04.modelo.beans.Usuario;

/**
 * Interfaz de consultas a la base de datos
 */
public interface BeanDaoConsultas {
	
	/**
	 * Método que obtiene los datos del usuario de la base de datos
	 * @param login, el login del usuario
	 * @param clave, la clave del usuario
	 * @return Usuario, el usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws BeanError
	 */
	public Usuario getUsuario(String login, String clave) throws Exception, IllegalStateException, IllegalArgumentException, BeanError;
	
	/**
	 * Método que comprueba si un login/nombre de usuario, ya está registrado.
	 * @param login, el login que se quiere registrar
	 * @return, true o false
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public boolean existeUsuario(String login) throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Método que obtiene el ranking de los 10 usuarios con la puntuación más alta.
	 * @return List<Ranking>
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public List<Ranking> getRanking10() throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Método que devuelve los id de todos los fotogramas de la base de datos.
	 * @return List<Integer> con todos los id
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public List<Integer> getIdFotogramas() throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Método que devuelve la lista de los id de los fotogramas no acertados del usuario
	 * @param login, del usuario
	 * @param numFotSesion, cantidad de fotogramas que tendrá la sesión de juego
	 * @return List<Integer>, la lista con los ids de  los fotogramas
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public List<Integer> listaFotNoAcertados(String login, int numFotSesion) throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Obtiene el título de la película de un fotograma
	 * @param idFotograma, del fotograma
	 * @return String, con el nombre de la película
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public String obtenerTituloFotograma(int idFotograma) throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Devuelve un objeto fotograma.
	 * @param idFotograma, id del fotograma
	 * @return Fotograma
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public Fotograma obtenerFotograma(int idFotograma) throws Exception, IllegalStateException, IllegalArgumentException;
	
	/**
	 * Obtiene la posición de un usuario en el ranking de puntuación
	 * @param usuario, usuario a buscar
	 * @return Integer con la posición.
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public Integer getPosicion(Usuario usuario) throws Exception, IllegalStateException, IllegalArgumentException;
	
}
