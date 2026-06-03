document.addEventListener("DOMContentLoaded", () => {

    let overlay = document.getElementById("loadingOverlay");

    if (!overlay) {
        overlay = document.createElement("div");
        overlay.id = "loadingOverlay";

        overlay.innerHTML = `
            <div class="loading-box">
                Retrieving property data...
                <br>
                <small>Please wait.</small>
            </div>
        `;

        document.body.appendChild(overlay);
    }

    function showLoading() {
        overlay.style.display = "flex";
    }

    function hideLoading() {
        overlay.style.display = "none";
    }

    // Important: always hide it when the page finishes loading normally
    hideLoading();

    document.addEventListener("click", function (event) {

        const link = event.target.closest("a");

        if (!link) return;

        const href = link.getAttribute("href");
        const target = link.getAttribute("target");

        // Do not show loading for empty links, anchors, javascript links,
        // Bootstrap modal buttons, new tabs, or downloads.
        if (
            !href ||
            href.startsWith("#") ||
            href.startsWith("javascript:") ||
            target === "_blank" ||
            link.hasAttribute("download") ||
            link.hasAttribute("data-bs-toggle")
        ) {
            return;
        }

        showLoading();
    });

    document.addEventListener("submit", function () {
        showLoading();
    });

    // This fixes the browser Back button problem.
    // When the browser restores a page from cache, DOMContentLoaded may not run again.
    // pageshow does run, so we hide the overlay here.
    window.addEventListener("pageshow", function () {
        hideLoading();
    });

    // Extra safety: if the user leaves and returns to the tab, hide stuck overlay.
    document.addEventListener("visibilitychange", function () {
        if (!document.hidden) {
            hideLoading();
        }
    });
});