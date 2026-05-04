let panzoom = null;
const contentWidth = 1000;
const contentHeight = 1065;

function toggleMap() {
    const container = document.getElementById("inlineMapContainer");
    const btn = document.getElementById("mapToggleBtn");

    if (container.style.display === "none") {
        container.style.display = "block";
        btn.innerText = "Hide Map";
        btn.classList.replace("btn-outline-success", "btn-danger");

        // Ejecución inmediata y una de respaldo para asegurar renderizado en pantallas grandes
        centerMap();
        setTimeout(centerMap, 60);
    } else {
        container.style.display = "none";
        btn.innerText = "View Interactive Map";
        btn.classList.replace("btn-danger", "btn-outline-success");
    }
}

function centerMap() {
    const element = document.getElementById("mapContent");
    const wrapper = document.getElementById("mapWrapper");
    if (!element || !wrapper) return;

    // Obtener dimensiones exactas del contenedor visible
    const rect = wrapper.getBoundingClientRect();
    const w = rect.width;
    const h = rect.height;

    if (w === 0 || h === 0) return;

    // Calcular escala para que el mapa entre perfecto (Zoom Out)
    const scale = Math.min(w / contentWidth, h / contentHeight);

    if (panzoom) {
        panzoom.destroy();
    }

    // Inicializar Panzoom
    panzoom = Panzoom(element, {
        maxScale: 5,
        minScale: scale,
        initialScale: scale,
        contain: 'outside',
        cursor: "grab",
        animate: false // Evita el deslizamiento al cargar
    });

    // Activar zoom con rueda del ratón
    wrapper.addEventListener('wheel', panzoom.zoomWithWheel);

    // CENTRADO MATEMÁTICO:
    // Calculamos el espacio sobrante en X e Y para centrar el bloque escalado
    const x = (w - (contentWidth * scale)) / 2;
    const y = (h - (contentHeight * scale)) / 2;

    // Forzar posición sin animaciones
    panzoom.pan(x, y, { force: true });

    // Mostrar el mapa
    element.style.visibility = "visible";
    element.style.display = "block";
}

window.addEventListener("resize", function() {
    const container = document.getElementById("inlineMapContainer");
    if (container && container.style.display === "block") {
        centerMap();
    }
});



//     // Click para copiar coordenadas
//     document.addEventListener("pointerup", function (e) {
//
//         console.log("DOCUMENT POINTERUP");
//
//         if (!wrapper.contains(e.target)) return;
//
//         if (isDragging) {
//             console.log("Ignored: dragging");
//             return;
//         }
//
//         const link = e.target.closest(".unit-btn, .unit-btn-v, .unit-btn-hall");
//         if (link) {
//             console.log("Ignored: unit button");
//             return;
//         }
//
//         const rect = element.getBoundingClientRect();
//         const scale = panzoom.getScale();
//
//         const x = Math.round((e.clientX - rect.left) / scale);
//         const y = Math.round((e.clientY - rect.top) / scale);
//
//         console.log("X:", x, "Y:", y);
//
//         const unitId = prompt("Enter Unit ID:");
//         if (!unitId) return;
//
//         const snippet = `<a th:href="@{/units/${unitId}}" class="unit-btn" style="top: ${y}px; left: ${x}px;"></a>`;
//
//         console.log("COPIED:", snippet);
//
//         navigator.clipboard.writeText(snippet)
//             .then(() => alert("Copied:\n" + snippet))
//             .catch(err => console.error("Copy failed", err));
//
//     }, true);
//
//     wrapper.addEventListener("pointerup", function () {
//         setTimeout(() => {
//             isDragging = false;
//         }, 100);
//     }, true);
//
