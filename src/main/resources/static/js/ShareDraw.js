function actualizarCampoOculto(select) {
   // Obtener el permiso seleccionado en el menú desplegable
   var valorSeleccionado = select.value;

   // Actualizar el valor del campo oculto con el valor seleccionado
   document.getElementById('permission').value = valorSeleccionado;
}