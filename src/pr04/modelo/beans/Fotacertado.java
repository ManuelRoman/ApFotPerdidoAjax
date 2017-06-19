package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entidad de persistencia para la tabla fotacertados de la base de datos
 */
@Entity
@Table(name="fotacertados")
@NamedQuery(name="Fotacertado.findAll", query="SELECT f FROM Fotacertado f")
public class Fotacertado implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Información del fotograma
	 */
	@EmbeddedId
	private FotacertadoPK id;

	/**
	 * Infotmación de se ha sido acertado
	 */
	private byte acertado;

	public Fotacertado() {
	}
	
	//Getters y Setters
	public Fotacertado(FotacertadoPK id) {
		this.id = id;
	}

	public FotacertadoPK getId() {
		return this.id;
	}

	public void setId(FotacertadoPK id) {
		this.id = id;
	}

	public byte getAcertado() {
		return this.acertado;
	}

	public void setAcertado(byte acertado) {
		this.acertado = acertado;
	}

}