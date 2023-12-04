// Función para mostrar el modal
function confirmDelete() {
    var modal = document.getElementById('deleteModal');
    modal.style.display = 'block';
    return false; // Evita el envío del formulario por defecto
}

// Función para cerrar el modal
function closeModal() {
    var modal = document.getElementById('deleteModal');
    modal.style.display = 'none';
}

// Función para realizar la eliminación
function deleteDraw() {
    // Puedes agregar aquí la lógica para enviar el formulario de eliminación
    document.getElementById('deleteForm').submit();
    closeModal();
}
