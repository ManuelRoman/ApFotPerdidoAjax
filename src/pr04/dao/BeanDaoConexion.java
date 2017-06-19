package pr04.dao;

/**
 * Interfaz que obliga a implementar la conexión con la unidad de persistencia.
 */
public interface BeanDaoConexion {
	
	/**
	 * Método para obtener la conexión con la unidad de persistencia.
	 * @throws Exception
	 * @throws IllegalStateException
	 */
	public void getConexion() throws Exception, IllegalStateException;
	
	/**
	 * Método que cierra la conexión con la unidad de persistencia.
	 * @throws Exception
	 * @throws IllegalStateException
	 */
	public void close() throws Exception, IllegalStateException;

}
