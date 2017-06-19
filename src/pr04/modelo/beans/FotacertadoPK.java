package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Clave primaria de la clase Fotacertado.
 */
@Embeddable
public class FotacertadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/**
	 * Login del usuario
	 */
	private String login;

	/**
	 * Id del fotogramas
	 */
	private int idFotograma;

	public FotacertadoPK() {
	}
	
	public FotacertadoPK(String login) {
		this.login = login;
	}
	
	public FotacertadoPK(String login, int idFotograma) {
		this.login = login;
		this.idFotograma = idFotograma;
	}
	
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getIdFotograma() {
		return this.idFotograma;
	}
	public void setIdFotograma(int idFotograma) {
		this.idFotograma = idFotograma;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FotacertadoPK)) {
			return false;
		}
		FotacertadoPK castOther = (FotacertadoPK)other;
		return 
			this.login.equals(castOther.login)
			&& (this.idFotograma == castOther.idFotograma);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.login.hashCode();
		hash = hash * prime + this.idFotograma;
		
		return hash;
	}
}