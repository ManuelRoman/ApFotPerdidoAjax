package pr04.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;

import pr04.modelo.beans.BeanError;
import pr04.modelo.beans.DatosJuego;
import pr04.modelo.beans.Usuario;

/**
 * Interfaz que encapsula los procesos de inserción y actualización con la base de datos.
 */
public interface BeanDaoInsercion {
	
	/**
	 * Proceso que inserta un usuario en la base de datos, en la tabla usuarios, fotacertado y concurso
	 * @param usuario, el usuario a insertar
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws EntityExistsException
	 * @throws RollbackException
	 * @throws BeanError
	 */
	public void insertaUsuario(Usuario usuario) throws Exception, IllegalStateException, EntityExistsException, RollbackException, BeanError;
	
	/**
	 * Proceso que inserta un usuario en la tabla que almacena el estado (acertados o no) de un fotograma dado para un usuario
	 * @param login, del usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws EntityExistsException
	 * @throws RollbackException
	 */
	public void insertaFotAcertados(String login) throws Exception, IllegalStateException, EntityExistsException, RollbackException;
	
	/**
	 * Actualiza el estado de un fotograma, pasaondo de false a true, cuando es acertado
	 * @param idFotAcertado, del fotograma
	 * @param login, del usuario
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	public void actualizaFotAcertado(int idFotAcertado, String login) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException;
	
	/**
	 * Proceso que almacena los nuevos datos del juego del usuario 
	 * @param usuario, usuario que está jugando
	 * @param datosJuego, del juego actual
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	public void actualizaDatosJuego(Usuario usuario, DatosJuego datosJuego) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException;
	
	/**
	 * Actualiza el ranking del usuario
	 * @param usuario
	 * @param datosJuego, está la nueva puntuación.
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @throws RollbackException
	 */
	public void actualizaRanking(Usuario usuario, DatosJuego datosJuego) throws Exception, IllegalStateException, IllegalArgumentException, RollbackException;
	
		
	
}