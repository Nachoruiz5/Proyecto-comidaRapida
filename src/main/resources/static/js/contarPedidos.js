let tablaPedidos = document.getElementById('tablaPedidos');
let filas = tablaPedidos.getElementsByTagName('tbody')[0];
document.getElementById('cantidadPedidos').innerText = "Número de pedidos realizados: " + filas.children.length;

window.addEventListener("load", function(){
	calcularGanancias();
})

function calcularGanancias(){
	let total = 0;
	const tabla = document.getElementById('tablaPedidos');
	for(let i=1; i<=tabla.tBodies[0].rows.length; i++){
		let valorPedido = tabla.rows[i].cells[2].innerHTML;
		total = total + Number(valorPedido);
		total = Math.trunc(total);
	}
	document.getElementById('ganancia').innerText = "Ganancia Total: $" + total;
}


