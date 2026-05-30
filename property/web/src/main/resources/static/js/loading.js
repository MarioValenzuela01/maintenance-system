document.addEventListener("DOMContentLoaded", () => {

    const overlay = document.createElement("div");
    overlay.id = "loadingOverlay";

    overlay.innerHTML = `
        <div class="loading-box">
            Retrieving property data...
            <br>
            <small>Please wait.</small>
        </div>
    `;

    document.body.appendChild(overlay);

    function showLoading() {
        overlay.style.display = "flex";
    }

    document.addEventListener("click", function (event) {

        const link = event.target.closest("a");

        if (!link) return;

        const href = link.getAttribute("href");

        if (!href || href.startsWith("#") || href.startsWith("javascript:")) {
            return;
        }

        showLoading();
    });

    document.addEventListener("submit", function () {
        showLoading();
    });

});