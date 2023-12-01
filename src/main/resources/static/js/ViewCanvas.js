//Guardar variables
const newCanvas = document.getElementById("ViewCanvas");
const newCtx = newCanvas.getContext("2d");
const figuresDataElement = document.getElementById("selectedFigures").value;
const parsedFigures = JSON.parse(figuresDataElement);

// Función para dibujar figuras
const draw = (parsedFigures,ctx) => {
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

//Llamo funcion draw pasandole las figuras que recibo
draw(parsedFigures,newCtx);

document.addEventListener('DOMContentLoaded', function () {
        const visibilityRadios = document.querySelectorAll('.visibility');
        const visibilityHiddenInput = document.querySelector('#visibility');

        // Marcar automáticamente el radio button basado en el valor de visibilityHiddenInput
        const visibilityValue = visibilityHiddenInput.value;
        const selectedRadio = Array.from(visibilityRadios).find((radio) => radio.value === visibilityValue);

        if (selectedRadio) {
            selectedRadio.checked = true;
        }

        // Actualizar el campo oculto cuando cambia manualmente el radio button
        visibilityRadios.forEach((radio) => {
            radio.addEventListener('change', () => {
                visibilityHiddenInput.value = radio.value;
            });
        });
    });
