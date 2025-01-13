package com.rotiseria.modeladoDeSoftware.model;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="producto")
@SQLDelete(sql = "UPDATE producto SET borrado=true WHERE id_producto = ?")
@Where(clause = "borrado = false")
public class Producto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto")
	protected Long idProducto;

	@Column(name="image")
	public String image; 
	
	@NotBlank(message = "Debe ingresar un nombre")
	@Column(name="nombre")
	protected String nombre;
	
	@NotNull(message = "Debe ingresar el precio")
	@Column(name="precio")
	protected Float precio;
	
	@Column(name="borrado")
	protected Boolean borrado;

	
	@ManyToOne
	private Usuario usuario;
	
	//constructores:
	public Producto() {}
	public Producto(@NotBlank(message = "Debe ingresar un nombre") String nombre,
			@NotNull(message = "Debe ingresar el precio") Float precio, Boolean borrado, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.borrado = borrado;
		this.usuario = usuario;
	}


	//getters y setters:
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isBorrado() {
		return borrado;
	}
	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	public Long getimage() {
		return idProducto;
	}
	public void setimage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", precio=" + precio + ", borrado="
				+ borrado + ", usuario=" + usuario + "]";
	}
}
