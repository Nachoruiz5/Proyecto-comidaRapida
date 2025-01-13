function agregarEnvio(subt) {							//subt es el parámetro que le pasamos a la función cuando la llamamos desde resumenPedido.html
	
	let servicio = document.getElementById("servicio");
	var subtotal = subt;
	var total = 0;
	
	if (servicio.value == "envio") {
		total = subtotal + 300;
		var costo = document.querySelector('#costoEnvio');
		costo.textContent = "Costo de envío: $ 300";
		document.getElementById("costoEnvio").style.display = "block";
	} else {
		document.getElementById("costoEnvio").style.display = "none";
		total = subtotal;
	}
	var totalMostrado = document.querySelector('#total');
	totalMostrado.value = `${total}`;
}