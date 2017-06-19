package pr04.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que implementa la conexión con la unidad de persistencia
 */
public class BeanDaoConexionImpl implements BeanDaoConexion{
	
	/**
	 * Unidad de peristencia
	 */
	protected String unidadPersistencia;
	
	/**
	 *  Información de la administración de la factoría de la unidad de persistencia.
	 */
	protected EntityManagerFactory emf;
	
	/**
	 * Información del contexto de la unidad de persistencia
	 */
	protected EntityManager em;
	
	/**
	 * Constructor, que recibe la unidad de persistencia
	 * @param unidadPersistencia
	 */
	public BeanDaoConexionImpl(String unidadPersistencia){
		this.unidadPersistencia = unidadPersistencia;
	}

	/**
	 * Método para obtener la conexión con la unidad de persistencia.
	 * @throws Exception
	 * @throws IllegalStateException
	 */
	@Override
	public void getConexion() throws Exception, IllegalStateException{
		if (this.em == null){
			this.emf = Persistence.createEntityManagerFactory(this.unidadPersistencia);
			this.em = emf.createEntityManager();
		}	
	}

	/**
	 * Método que cierra la conexión con la unidad de persistencia.
	 * @throws Exception
	 * @throws IllegalStateException
	 */
	@Override
	public void close() throws Exception, IllegalStateException {
		if (this.em != null){
			this.em.close();
			this.emf.close();
		}
		this.em = null;
		this.emf = null;
	}

}
