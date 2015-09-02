package uned.dlr.pfc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="test")
public class PfcTest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String nombre;
	@Column(length=5000)
	String code;
	boolean pasado;
	
	
	public PfcTest() {
		super();
	
	}
	public PfcTest(String nombre) {
		super();
		this.nombre = nombre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public boolean isPasado() {
		return pasado;
	}
	public void setPasado(boolean pasado) {
		this.pasado = pasado;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PfcTest [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (code != null)
			builder.append("code=").append(code).append(", ");
		builder.append("pasado=").append(pasado).append("]");
		return builder.toString();
	}
	
	
	
}
