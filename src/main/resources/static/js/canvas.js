import { canvas, ctx ,draw } from './draw.js';
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

// Event listener para el cambio en los elementos radio
document.querySelectorAll('.visibility').forEach(function (radio) {
   radio.addEventListener('change', function () {
   setVisibility(this.value);
     });
});

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

// Función para renderizar las figuras y actualizar los registros
const render = (figures) => {
    logs.innerHTML = "";
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    figures.forEach((figure, i) => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `Tipo: ${figure.type} - Color: ${figure.color}
        <button data-index="${i}" class="Delete-Button">Eliminar</button>
        <button data-index="${i}" class="Modify-Button">Modify</button>`;

        const deleteButton = listItem.querySelector(".Delete-Button");
        deleteButton.addEventListener("click", () => {
            removeFigure(i);
        });

        const modifyButton = listItem.querySelector(".Modify-Button");
        modifyButton.addEventListener("click", () => {
            modifyFigure(i);
        });
        logs.appendChild(listItem);
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


// Event listener para el cambio en los campos del formulario
colorInput.addEventListener('input', () => modifyFigure(figures.length - 1));
sizeInput.addEventListener('input', () => modifyFigure(figures.length - 1));
fillFigureCheckbox.addEventListener('input', () => modifyFigure(figures.length - 1));

// Función para actualizar la vista de una figura específica
const modifyFigure = (index) => {
    const figure = figures[index];

    // Obtén los elementos del formulario
    const colorInput = document.getElementById("color");
    const sizeInput = document.getElementById("size");
    const fillCheckbox = document.getElementById("fillFigure");

    // Actualiza los valores de la figura con los nuevos valores del formulario
    figure.color = colorInput.value;
    figure.size = sizeInput.value;
    figure.filled = fillCheckbox.checked;

    // Borra el lienzo y vuelve a dibujar todas las figuras
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    figures.forEach(draw);

    // Dibuja la figura modificada
    draw(figure);
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
         alert("¡Se ha creado correctamente!");
       } else {
          alert("Error al crear. Tiene que haber una figura");
       }
    } catch (error) {
            alert("Error de red. Por favor, inténtalo de nuevo más tarde.");
    }
}