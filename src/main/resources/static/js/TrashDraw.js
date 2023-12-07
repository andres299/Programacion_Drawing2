document.addEventListener('DOMContentLoaded', function () {
    var deleteButton = document.querySelector('.delete');
    var modalDeleteButton = document.querySelector('.modal-delete');
    var modalCancelButton = document.querySelector('.modal-cancel');
    var deleteForm = document.getElementById('deleteForm');
    var deleteModal = document.getElementById('deleteModal');

    // Agregar un listener al botón de eliminar en la tabla
    deleteButton.addEventListener('click', function () {
        confirmDelete();
    });

    // Agregar un listener al botón de eliminar en el modal
    modalDeleteButton.addEventListener('click', function () {
        deleteDraw();
    });

    // Agregar un listener al botón de cancelar en el modal
    modalCancelButton.addEventListener('click', function () {
        closeModal();
    });

    // Función para mostrar el modal de confirmación
    function confirmDelete() {
        deleteModal.style.display = 'block';
    }

    // Función para cerrar el modal
    function closeModal() {
        deleteModal.style.display = 'none';
    }

    // Función para realizar la eliminación
    function deleteDraw() {
        // Puedes agregar aquí la lógica para enviar el formulario de eliminación
        deleteForm.submit();
        closeModal();
    }
});
