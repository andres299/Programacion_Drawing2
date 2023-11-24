//Guardar variables del jsp
const canvas = document.getElementById("ModifyCanvas");
const ctx = canvas.getContext("2d");
const figureSelect = document.getElementById("figureSelect");
const colorInput = document.getElementById("color");
const sizeInput = document.getElementById("size");
const fillFigureCheckbox = document.getElementById("fillFigure");
const drawButton = document.getElementById("drawButton");
const clearButton = document.getElementById("clearButton");
const saveButton = document.getElementById("sendButton");
const logs = document.getElementById("logs");
const figuresDataElement = document.getElementById("selectedFigures").value;
const parsedFigures = JSON.parse(figuresDataElement);

let figures = [];
let isDrawingLine = false;
let currentPath = [];
let currentFigure = "circle";

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

// Agrega figuras almacenadas en parsedFigures al array figures
parsedFigures.forEach((figure) => {
    figures.push(figure);
});

// Renderiza las figuras en el canvas
render(figures);

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

// Evento de escucha para el botón de borrar
clearButton.addEventListener("click", () => {
    figures = [];
    render(figures);
});

//Enviar los datos
saveButton.addEventListener("click", () => {
    // Convierte el array "figures" a una cadena JSON
    var figuresJSON = JSON.stringify(figures);
    // Asigna la cadena JSON al campo oculto "figures"
    document.getElementById("figures").value = figuresJSON;
    // Enviar el formulario para procesar los datos en el servlet
    document.getElementById("Formulario").submit();
});
