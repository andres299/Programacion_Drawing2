const canvas = document.querySelector(".canvas");
const ctx = canvas.getContext("2d");

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
export { canvas, ctx , draw};



