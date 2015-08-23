package uned.dlr.pfc.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="codigo")
public class Codigo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@NotEmpty
	String nombre;
	String code;
	@OneToOne
	PfcTest test;
	@ElementCollection(fetch=FetchType.EAGER)
	Set<Long> propietarios;
	
	
	
	public Codigo() {
		super();
	
	}
	public Codigo(String nombre) {
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
	
	public PfcTest getTest() {
		return test;
	}
	public void setTest(PfcTest test) {
		this.test = test;
	}
	
	
	public void removePropietario(Long proyectoId) {
		if(propietarios==null||proyectoId==null) return;
		Iterator<Long> it=propietarios.iterator();
	    propietarios.remove(proyectoId);
	}
	public void addPropietario(Long proyectoId) {
		if(propietarios==null){
			propietarios=new HashSet<Long>();
		}
		propietarios.add(proyectoId);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Codigo [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", code=");
		builder.append(code);
		builder.append(", test=");
		builder.append(test);
		builder.append(", propietarios=");
		builder.append(propietarios);
		builder.append("]");
		return builder.toString();
	}
	public Set<Long> getPropietarios() {
		return propietarios==null?new HashSet<Long>():propietarios;
	}
	public void setPropietarios(Set<Long> propietarios) {
		this.propietarios = propietarios;
	}
	
	
	
}
