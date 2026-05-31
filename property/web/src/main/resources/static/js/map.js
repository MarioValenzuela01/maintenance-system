let panzoom = null;

const contentWidth = 1000;
const contentHeight = 1065;

function centerMap() {
    const element = document.getElementById("mapContent");
    const wrapper = document.getElementById("mapWrapper");

    if (!element || !wrapper) return;

    const rect = wrapper.getBoundingClientRect();
    const w = rect.width;
    const h = rect.height;

    if (w === 0 || h === 0) return;

    const scale = Math.min(w / contentWidth, h / contentHeight);

    if (panzoom) {
        panzoom.destroy();
    }

    panzoom = Panzoom(element, {
        maxScale: 5,
        minScale: scale,
        initialScale: scale,
        contain: "outside",
        cursor: "grab",
        animate: false
    });

    wrapper.addEventListener("wheel", panzoom.zoomWithWheel);

    const x = (w - (contentWidth * scale)) / 2;
    const y = (h - (contentHeight * scale)) / 2;

    panzoom.pan(x, y, { force: true });

    element.style.visibility = "visible";
    element.style.display = "block";
}

function toggleMap() {
    const container = document.getElementById("inlineMapContainer");
    const btn = document.getElementById("mapToggleBtn");

    if (!container || !btn) {
        centerMap();
        return;
    }

    if (container.style.display === "none" || container.style.display === "") {
        container.style.display = "block";
        btn.innerText = "Hide Map";
        btn.classList.replace("btn-outline-success", "btn-danger");

        centerMap();
        setTimeout(centerMap, 60);
    } else {
        container.style.display = "none";
        btn.innerText = "View Interactive Map";
        btn.classList.replace("btn-danger", "btn-outline-success");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const wrapper = document.getElementById("mapWrapper");

    if (wrapper) {
        centerMap();
        setTimeout(centerMap, 100);
    }
});

window.addEventListener("resize", function () {
    const wrapper = document.getElementById("mapWrapper");

    if (wrapper) {
        centerMap();
    }
});