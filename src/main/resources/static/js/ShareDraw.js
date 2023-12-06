document.addEventListener('DOMContentLoaded', function() {
    // Se intenta obtener el elemento con el id "permisos"
   var select = document.getElementById('permisos');
   //Se obtiene el valor oculto.
   var hiddenInput = document.getElementById('permission');
   //Comprueba si no es null.
   if (select) {
   //Cuando cambia se actualiza el valor del input oculto.
   select.addEventListener('change', function() {
        hiddenInput.value = select.value;
    });
    } else {
    //Si no ha encontrado.
        console.error('El elemento con id "permisos" no fue encontrado.');
  }
});