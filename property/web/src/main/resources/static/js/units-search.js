let unitSearchTimer = null;

function getUnitSearchInput() {
    return document.getElementById("unitSearchInput");
}

function getUnitSearchKeyword() {
    const input = getUnitSearchInput();
    return input ? input.value.trim() : "";
}

function updateUnitsUrl(page, keyword) {
    const params = new URLSearchParams();

    if (page > 0) {
        params.set("page", page);
    }

    if (keyword && keyword.trim() !== "") {
        params.set("keyword", keyword.trim());
    }

    const newUrl = params.toString()
        ? "/units?" + params.toString()
        : "/units";

    window.history.replaceState({}, "", newUrl);
}

function loadUnitsPage(page = 0) {
    if (page < 0) return;

    const keyword = getUnitSearchKeyword();

    const params = new URLSearchParams();
    params.set("page", page);

    if (keyword !== "") {
        params.set("keyword", keyword);
    }

    fetch("/units/table?" + params.toString())
        .then(response => {
            if (!response.ok) {
                throw new Error("Error loading units table");
            }

            return response.text();
        })
        .then(html => {
            const container = document.getElementById("unitsTableContainer");

            if (!container) return;

            container.outerHTML = html;
            updateUnitsUrl(page, keyword);
        })
        .catch(error => {
            console.error(error);
        });
}

function clearUnitSearch() {
    const input = getUnitSearchInput();

    if (input) {
        input.value = "";
        input.focus();
    }

    loadUnitsPage(0);
}

document.addEventListener("DOMContentLoaded", function () {
    const input = getUnitSearchInput();
    const clearBtn = document.getElementById("clearUnitSearchBtn");

    if (input) {
        input.addEventListener("input", function () {
            clearTimeout(unitSearchTimer);

            unitSearchTimer = setTimeout(function () {
                loadUnitsPage(0);
            }, 300);
        });

        input.addEventListener("keydown", function (event) {
            if (event.key === "Escape") {
                clearUnitSearch();
            }

            if (event.key === "Enter") {
                event.preventDefault();
                clearTimeout(unitSearchTimer);
                loadUnitsPage(0);
            }
        });
    }

    if (clearBtn) {
        clearBtn.addEventListener("click", function () {
            clearUnitSearch();
        });
    }
});