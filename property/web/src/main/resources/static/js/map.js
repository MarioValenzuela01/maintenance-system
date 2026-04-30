document.addEventListener("DOMContentLoaded", function () {

    console.log("MAP JS LOADED");

    const element = document.getElementById("mapContent");
    const wrapper = document.getElementById("mapWrapper");

    console.log("element:", element);
    console.log("wrapper:", wrapper);

    if (!element || !wrapper) {
        console.error("mapContent or mapWrapper not found");
        return;
    }

    // TEST GLOBAL
    document.addEventListener("click", function () {
        console.log("DOCUMENT CLICK TEST");
    }, true);

    document.addEventListener("pointerdown", function () {
        console.log("DOCUMENT POINTERDOWN TEST");
    }, true);

    const panzoom = Panzoom(element, {
        maxScale: 5,
        minScale: 1,
        contain: "outside",
        disableDoubleClickZoom: true
    });

    wrapper.addEventListener("wheel", panzoom.zoomWithWheel);

    let startX = 0;
    let startY = 0;
    let isDragging = false;

    wrapper.addEventListener("pointerdown", function (e) {
        isDragging = false;
        startX = e.clientX;
        startY = e.clientY;
    }, true);

    wrapper.addEventListener("pointermove", function (e) {
        const diffX = Math.abs(e.clientX - startX);
        const diffY = Math.abs(e.clientY - startY);

        if (diffX > 8 || diffY > 8) {
            isDragging = true;
        }
    }, true);

    // Doble click para abrir unidad
    document.addEventListener("dblclick", function (e) {
        const link = e.target.closest(".unit-btn, .unit-btn-v, .unit-btn-hall");

        if (!link) return;
        if (!wrapper.contains(link)) return;

        window.location.href = link.href;
    }, true);

    function fitToScreen() {
        const wrapperWidth = wrapper.clientWidth;
        const wrapperHeight = wrapper.clientHeight;

        const contentWidth = element.offsetWidth;
        const contentHeight = element.offsetHeight;

        const scaleX = wrapperWidth / contentWidth;
        const scaleY = wrapperHeight / contentHeight;
        const scale = Math.min(scaleX, scaleY);

        const newWidth = contentWidth * scale;
        const newHeight = contentHeight * scale;

        const x = (wrapperWidth - newWidth) / 2;
        const y = (wrapperHeight - newHeight) / 2;

        panzoom.setOptions({ minScale: scale });

        panzoom.zoom(scale, { animate: false });
        panzoom.pan(x / scale, y / scale, { animate: false });
    }

    setTimeout(fitToScreen, 100);
    window.addEventListener("resize", fitToScreen);

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
});