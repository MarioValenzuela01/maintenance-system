
document.addEventListener("DOMContentLoaded", function () {

    console.log("MAP JS LOADED");

    const element = document.getElementById('mapContent');
    const wrapper = document.getElementById('mapWrapper');

    if (!element || !wrapper) {
        console.error("mapContent or mapWrapper not found");
        return;
    }

    const panzoom = Panzoom(element, {
        maxScale: 5,
        minScale: 1,
        contain: 'outside',
        disableDoubleClickZoom: true


    });

    let isDragging = false;
    let startX = 0;
    let startY = 0;

    wrapper.addEventListener('mousedown', function (e) {
        isDragging = false;
        startX = e.clientX;
        startY = e.clientY;
    });

    wrapper.addEventListener('mousemove', function (e) {
        const diffX = Math.abs(e.clientX - startX);
        const diffY = Math.abs(e.clientY - startY);

        if (diffX > 8 || diffY > 8) {
            isDragging = true;
        }
    });

    element.addEventListener('dblclick', function (e) {
        const link = e.target.closest('.unit-btn, .unit-btn-v');

        if (!link) return;

        window.location.href = link.href;
    });


    wrapper.addEventListener('wheel', panzoom.zoomWithWheel);

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

    // window.addEventListener('load', fitToScreen);
    setTimeout(fitToScreen, 100);
    window.addEventListener('resize', fitToScreen);

    // Click para copiar coordenada
    // element.addEventListener('click', function (e) {
    //
    //     if (e.target.classList.contains('unit-btn') ||
    //         e.target.classList.contains('unit-btn-v')) {
    //         return;
    //     }
    //
    //     console.log("CLICK DETECTED");
    //
    //     const rect = element.getBoundingClientRect();
    //     const scale = panzoom.getScale();
    //
    //     let x = Math.round((e.clientX - rect.left) / scale);
    //     let y = Math.round((e.clientY - rect.top) / scale);
    //
    //     const unitId = prompt("Enter Unit ID:");
    //
    //     if (!unitId) return;
    //
    //     const snippet = `<a th:href="@{/units/${unitId}}" class="unit-btn" style="top: ${y}px; left: ${x}px;"></a>`;
    //
    //     console.log("COPIED:", snippet);
    //
    //     navigator.clipboard.writeText(snippet)
    //         .then(() => console.log("Copied!"))
    //         .catch(err => console.error("Copy failed", err));
    // });

});