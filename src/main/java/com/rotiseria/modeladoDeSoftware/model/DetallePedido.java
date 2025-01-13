package com.rotiseria.modeladoDeSoftware.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalle_pedido")
public class DetallePedido {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_pedido")
	protected Long idDetallePedido;
	
	@Column(name="nombre")
	protected String nombre;
	
	@Column(name="cantidad")
	protected int cantidad;
	
	@Column(name="precio")
	protected Float precio;
	
	@Column(name="total")
	protected Float total;
	
	@ManyToOne
	protected Pedido pedido;
	
	@ManyToOne
	protected Producto producto;
	
	//Constructores:
	public DetallePedido() {}
	public DetallePedido(String nombre, int cantidad, Float precio, Float total, Pedido pedido, Producto producto) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
		this.pedido = pedido;
		this.producto = producto;
	}
	
	//Getters y setters:
	public Long getIdDetallePedido() {
		return idDetallePedido;
	}
	public void setIdDetallePedido(Long idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	@Override
	public String toString() {
		return "DetallePedido [idDetallePedido=" + idDetallePedido + ", nombre=" + nombre + ", cantidad=" + cantidad
				+ ", precio=" + precio + ", total=" + total + ", pedido=" + pedido + ", producto=" + producto + "]";
	}
}
