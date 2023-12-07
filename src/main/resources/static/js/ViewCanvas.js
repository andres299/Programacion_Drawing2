// app.js
import { canvas, ctx, draw } from './draw.js';

const drawParsedFigures = (parsedFigures) => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    parsedFigures.forEach(figure => {
        draw(figure);
    });
};

const updateCanvas = () => {
    var selectedOption = document.querySelector('#versionSelect option:checked');
    var selectedVersionFigures = JSON.parse(selectedOption.getAttribute('data-figures'));

    document.getElementById("selectedFigures").value = JSON.stringify(selectedVersionFigures);

    drawParsedFigures(selectedVersionFigures);
};

document.getElementById("versionSelect").addEventListener("change", updateCanvas);

document.getElementById("copyButton").addEventListener("click", function() {
    const figuresDataElement = document.getElementById("selectedFigures").value;
    const drawId = document.getElementById("drawId").value;

    const formData = new FormData();
    formData.append("jsonData", figuresDataElement);
    formData.append("draw_Id", drawId);

    fetch('/ViewDraw', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert("¡Se ha copiado correctamente!");
        } else {
            alert("Error al copiar. Tiene que tener permisos o ser el propietario");
        }
    })
    .catch(error => {
        alert("Error de red. Por favor, inténtalo de nuevo más tarde.");
    });
});

updateCanvas();