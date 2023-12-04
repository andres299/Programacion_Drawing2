document.addEventListener('DOMContentLoaded', function () {
    var deleteConfirmationModal = document.getElementById('deleteConfirmationModal');
    var confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    var cancelDeleteBtn = document.getElementById('cancelDeleteBtn');

    // Manejar el clic en el botón "Eliminar" de cada fila
    var deleteButtons = document.querySelectorAll('.delete');
    deleteButtons.forEach(function (deleteButton) {
        deleteButton.addEventListener('click', function (event) {
            // Evitar el envío del formulario por defecto
            event.preventDefault();

            // Mostrar el modal de confirmación
            deleteConfirmationModal.style.display = 'block';

            // Manejar el clic en el botón de confirmación del modal
            confirmDeleteBtn.onclick = function () {
                // Aquí podrías enviar el formulario o realizar la acción de eliminación
                // Por ejemplo, puedes usar AJAX para enviar la solicitud al servidor

                // Ocultar el modal de confirmación
                deleteConfirmationModal.style.display = 'none';

                // Ahora, puedes enviar el formulario
                deleteButton.closest('form').submit();
            };

            // Manejar el clic en el botón de cancelar del modal
            cancelDeleteBtn.onclick = function () {
                // Ocultar el modal de confirmación
                deleteConfirmationModal.style.display = 'none';
            };
        });
});