package uned.dlr.pfc.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="proyecto")
public class Proyecto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String nombre;
	String descripcion;
	@NotNull
	@ManyToOne
	User user;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="proyecto_codigo")
	List<Codigo> codigos;
	
	public Proyecto() {
		super();
	
	}
	public Proyecto(String nombre, String descripcion, User user) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.user = user;
	}
	public Proyecto(String nombre, User user) {
		super();
		this.nombre = nombre;
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public List<Codigo> getCodigos() {
		return codigos;
	}
	public void setCodigos(List<Codigo> codigos) {
		this.codigos = codigos;
	}
	public void addCode(Codigo codigo) {
		if(codigos==null){
			codigos=new ArrayList<Codigo>();
		}
		codigos.add(codigo);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Proyecto [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", user=");
		builder.append(user);
		builder.append(", codigos=");
		builder.append(codigos);
		builder.append("]");
		return builder.toString();
	}
	public void removeCodigo(Codigo codigo) {
		if(codigos==null) return;
		Iterator<Codigo> it=codigos.iterator();
		while(it.hasNext()){
			if(it.next().getId().equals(codigo.getId())) it.remove();
		}
		
	}
	public void removeCodigo(Long codigoId) {
		if(codigoId==null) return;
		Iterator<Codigo> it=codigos.iterator();
		while(it.hasNext()){
			if(it.next().getId().equals(codigoId)) it.remove();
		}
		
	}
	
	
}
