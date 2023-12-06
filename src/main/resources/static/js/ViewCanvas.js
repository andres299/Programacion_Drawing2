//Guardar variables
const newCanvas = document.getElementById("ViewCanvas");
const newCtx = newCanvas.getContext("2d");

// Función para dibujar figuras
const draw = (parsedFigures, ctx) => {
    // Limpia el lienzo antes de dibujar las nuevas figuras
    ctx.clearRect(0, 0, newCanvas.width, newCanvas.height);

    parsedFigures.forEach(figure => {
        const { type, color, size, filled, coordinates } = figure;

        ctx.beginPath();
        ctx.fillStyle = color;
        ctx.strokeStyle = color;
        ctx.lineWidth = 2;

        const halfSize = size / 2;
        const currentSize = filled ? size : 0;

        switch (type) {
            case "circle":
                ctx.arc(coordinates[0].x, coordinates[0].y, halfSize, 0, 2 * Math.PI);
                break;
            case "square":
                ctx.rect(coordinates[0].x - halfSize, coordinates[0].y - halfSize, size, size);
                break;
            case "triangle":
                const x = coordinates[0].x;
                const y = coordinates[0].y;
                ctx.moveTo(x, y - halfSize);
                ctx.lineTo(x - halfSize, y + halfSize);
                ctx.lineTo(x + halfSize, y + halfSize);
                ctx.lineTo(x, y - halfSize);
                break;
            case "star":
                for (let i = 0; i < 14; i++) {
                    const angle = (Math.PI * 2 * i) / 14;
                    const radius = i % 2 === 0 ? size : size / 2;
                    ctx.lineTo(coordinates[0].x + radius * Math.cos(angle), coordinates[0].y + radius * Math.sin(angle));
                }
                ctx.closePath();
                break;
            case "line":
                if (coordinates && coordinates.length > 0) {
                    ctx.moveTo(coordinates[0].x, coordinates[0].y);
                    for (let i = 1; i < coordinates.length; i++) {
                        ctx.lineTo(coordinates[i].x, coordinates[i].y);
                    }
                }
                break;
            default:
                break;
        }

        if (filled) {
            ctx.fill();
        } else {
            ctx.stroke();
        }
        ctx.closePath();
    });
};

// Función para actualizar el canvas y el input hidden
function updateCanvas() {
    var selectedOption = document.querySelector('#versionSelect option:checked');
    var selectedVersionFigures = JSON.parse(selectedOption.getAttribute('data-figures'));

    // Actualiza el valor del input hidden con las figuras de la versión seleccionada
    document.getElementById("selectedFigures").value = JSON.stringify(selectedVersionFigures);
    console.log("Selected Figures: ", document.getElementById("selectedFigures").value);

    // Llama a la función draw para dibujar las nuevas figuras
    draw(selectedVersionFigures, newCtx);
}

// Llamamos al updateCanvas al cargar la página para dibujar las figuras de la ultima version.
updateCanvas();


document.getElementById("copyButton").addEventListener("click", function() {
   // Obténemos las figuras actualizadas del input hidden
   const figuresDataElement = document.getElementById("selectedFigures").value;
   const drawId = document.getElementById("drawId").value;
   // Crea un objeto FormData y agrega las figuras
   const formData = new FormData();
   formData.append("jsonData", figuresDataElement);
   formData.append("draw_Id", drawId);
   // Realiza la solicitud Fetch para enviar los datos al servidor
   fetch('/ViewDraw', {
        method: 'POST',
        body: formData
   })
   .then(response => {
           // Maneja la respuesta del servidor aquí
           if (response.ok) {
               console.log("Datos enviados con éxito");
               alert("¡Se ha copiado correctamente!");
           } else {
               console.error("Error al enviar los datos al servidor");
               alert("Error al copiar. Por favor, inténtalo de nuevo.");
           }
       })
       .catch(error => {
           console.error("Error en la solicitud Fetch:", error);
           alert("Error de red. Por favor, inténtalo de nuevo más tarde.");
       });
});