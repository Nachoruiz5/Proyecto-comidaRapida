package com.rotiseria.modeladoDeSoftware.model;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido")
	protected Long idPedido;
	
	@Column(name="fecha")
	protected Date fecha;
	
	@Column(name="total")
	protected double total;
	
	@OneToMany(mappedBy = "pedido")
	protected List<DetallePedido> detallesPedido;
	
	@ManyToOne
	private Usuario usuario;
	
	//Constructores:
	public Pedido() {}
	public Pedido(Date fecha, double total, List<DetallePedido> detallesPedido, Usuario usuario) {
		super();
		this.fecha = fecha;
		this.total = total;
		this.detallesPedido = detallesPedido;
		this.usuario = usuario;
	}


	//Getters y setters:
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public List<DetallePedido> getDetallesPedido() {
		return detallesPedido;
	}
	public void setDetallesPedido(List<DetallePedido> detallesPedido) {
		this.detallesPedido = detallesPedido;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", fecha=" + fecha + ", total=" + total + ", detallesPedido="
				+ detallesPedido + ", usuario=" + usuario + "]";
	}
}
