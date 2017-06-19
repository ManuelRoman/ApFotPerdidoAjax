package pr04.modelo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entidad de persistencia para la tabla ranking de la base de datos
 */
@Entity
@Table(name="ranking")
@NamedQuery(name="Ranking.findAll", query="SELECT r FROM Ranking r order by r.puntos DESC")
public class Ranking implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * LLama a la consulta que obtiene todos los usuarios del ranking ordenados de mayor a menor puntuación
	 */
	public static final String TODOS = "Ranking.findAll";

	/**
	 * Login del usuario
	 */
	@Id
	private String login;

	/**
	 * Puntos del suario
	 */
	private int puntos;

	/**
	 * bi-directional one-to-one association to Usuario
	 * Información del usuario
	 */
	@OneToOne
	@JoinColumn(name="login")
	private Usuario usuario;

	public Ranking() {
	}
	
	//Getters y Setters
	public Ranking(String login){
		this.login=login;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Implementación del método equals
	 */
	@Override
	public boolean equals(Object objeto) {
		boolean iguales = false;
		if ((objeto instanceof Ranking) && (((Ranking) objeto).getLogin().equals(this.login))) {
			iguales = true;
		}
		return iguales;

	}

}