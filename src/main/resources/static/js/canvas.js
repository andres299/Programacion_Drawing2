// Guardar variables del jsp
const canvas = document.querySelector(".canvas");
const ctx = canvas.getContext("2d");
const figureSelect = document.getElementById("figureSelect");
const colorInput = document.getElementById("color");
const sizeInput = document.getElementById("size");
const fillFigureCheckbox = document.getElementById("fillFigure");
const drawButton = document.getElementById("drawButton");
const clearButton = document.getElementById("clearButton");
const saveButton = document.getElementById("sendButton");
const logs = document.getElementById("logs");

let figures = [];
let isDrawingLine = false;
let currentPath = [];
let currentFigure = "circle";

// Declaración de la variable visibility
let visibility = "private";

// Función para establecer la visibilidad
function setVisibility(value) {
    visibility = value;
    localStorage.setItem('visibility', value);
}

// Función para eliminar una figura
const removeFigure = (i) => {
    figures.splice(i, 1);
    render(figures);
};

// Recupera el valor de visibilidad del localStorage al cargar la página
window.addEventListener('load', () => {
    const storedVisibility = localStorage.getItem('visibility');
    if (storedVisibility) {
        visibility = storedVisibility;
        // Selecciona el radio button correspondiente después de que el HTML esté completamente cargado
        if (storedVisibility === 'public') {
            document.querySelector("input[value='public']").checked = true;
        } else if (storedVisibility === 'private') {
            document.querySelector("input[value='private']").checked = true;
        }
    }
});

// Función para renderizar las figuras y actualizar los registros
const render = (figures) => {
    logs.innerHTML = "";
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    figures.forEach((figure, i) => {
        logs.innerHTML += `<li>Tipo: ${figure.type} - Color: ${figure.color}
       <button id="${i}" onclick="removeFigure(${i})" class="Delete-Button">Eliminar</button>
       </li>`;
        draw(figure);
    });

    // Dibujar la línea en tiempo real mientras se está dibujando
    if (isDrawingLine && currentPath.length > 1) {
        const lineFigure = {
            type: "line",
            color: colorInput.value,
            size: sizeInput.value,
            filled: fillFigureCheckbox.checked,
            coordinates: [...currentPath],
        };
        draw(lineFigure);
    }
};

// Función para dibujar figuras
const draw = (figure) => {
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
};

// Evento para el clic en el canvas
canvas.addEventListener("mousedown", (event) => {
    if (currentFigure !== "line") {
        // Dibuja otras figuras al hacer clic
        const figure = {
            type: currentFigure,
            color: colorInput.value,
            size: sizeInput.value,
            filled: fillFigureCheckbox.checked,
            coordinates: [{ x: event.offsetX, y: event.offsetY }],
        };
        figures.push(figure);
        render(figures);
        // Llama a la función saveFigures() cada vez que agregas una figura
        //saveFigures();
    } else {
        // Comienza el dibujo de línea
        isDrawingLine = true;
        currentPath = [{ x: event.offsetX, y: event.offsetY }];
    }
});

// Evento para el movimiento del ratón en el canvas
canvas.addEventListener("mousemove", (event) => {
    if (isDrawingLine) {
        // Agrega puntos a la ruta mientras se está dibujando la línea
        currentPath.push({ x: event.offsetX, y: event.offsetY });
        // Renderiza las figuras actualizadas
        render(figures);
    }
});

// Evento para el final del dibujo
canvas.addEventListener("mouseup", () => {
    if (isDrawingLine) {
        // Finaliza el dibujo de línea y agrega la línea a la lista de figuras
        isDrawingLine = false;
        if (currentPath.length > 1) {
            const lineFigure = {
                type: "line",
                color: colorInput.value,
                size: sizeInput.value,
                filled: fillFigureCheckbox.checked,
                coordinates: [...currentPath],
            };
            figures.push(lineFigure);
            currentPath = [];
            render(figures);
            // Llama a la función saveFigures() cada vez que agregas una figura
            //saveFigures();
        }
    }
});

// Evento para el cambio de figura
figureSelect.addEventListener("change", (event) => {
    currentFigure = event.target.value;
    isDrawingLine = false;
});

// Evento para el botón de dibujar
drawButton.addEventListener("click", () => {
    currentFigure = "line";
    isDrawingLine = false;
});

// Evento para el botón de borrar
clearButton.addEventListener("click", () => {
    figures = [];
    render(figures);
});

//Al cargar la pagina
window.addEventListener('load', () => {
    // Recuperamos valores si existen
    const storedFigure = localStorage.getItem('figure');
    const storedColor = localStorage.getItem('color');
    const storedSize = localStorage.getItem('size');
    const storedFillFigure = localStorage.getItem('fillFigure');
    const storedVisibility = localStorage.getItem('visibility');

    // Establece los valores recuperados en los elementos del formulario
    if (storedFigure) {
        figureSelect.value = storedFigure;
    }
    if (storedColor) {
        colorInput.value = storedColor;
    }
    if (storedSize) {
        sizeInput.value = storedSize;
    }
    if (storedFillFigure) {
        fillFigureCheckbox.checked = storedFillFigure === 'true';
    }
});

// Agregamos event listeners para detectar cambios y guardar en localStorage
figureSelect.addEventListener('change', function () {
    localStorage.setItem('figure', figureSelect.value);
});

colorInput.addEventListener('input', function () {
    localStorage.setItem('color', colorInput.value);
});

sizeInput.addEventListener('input', function () {
    localStorage.setItem('size', sizeInput.value);
});

fillFigureCheckbox.addEventListener('change', function () {
    localStorage.setItem('fillFigure', fillFigureCheckbox.checked);
});


// Event listener para el botón de guardar
saveButton.addEventListener("click", () => {
    saveFigures();
});

// Función para guardar figuras
async function saveFigures() {
    try {
        // Convertimos el array "figures" a una cadena JSON
        var figuresJSON = JSON.stringify(figures);

        const formData = new FormData();
        // Adjuntar las figuras en formato JSON
        formData.append("figures", figuresJSON);

        // Adjuntar la visibilidad
        formData.append("visibility", visibility);

        // Obtener el valor del nombre
        const imageName = document.getElementById("NomImage").value;
        formData.append("NomImage", imageName);

        // Enviar las figuras y la imagen al servidor
        const response = await fetch('/CanvasDraw', {
            method: 'POST',
            body: formData,
        });
       // Manejar la respuesta del servidor
        if (response.ok) {
            alert("¡Se ha guardado correctamente!");
        } else {
            alert("Error al guardar. Por favor, inténtalo de nuevo.");
        }
    } catch (error) {
            alert("Error de red. Por favor, inténtalo de nuevo más tarde.");
    }
}