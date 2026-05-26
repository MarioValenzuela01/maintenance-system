document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("tenantSearchInput");
    const rows = document.querySelectorAll(".tenant-row");

    if (!searchInput || rows.length === 0) return;

    searchInput.addEventListener("input", function () {

        const search = searchInput.value.toLowerCase().trim();

        rows.forEach(row => {

            const text = row.innerText.toLowerCase();

            row.style.display =
                text.includes(search)
                    ? ""
                    : "none";

        });

    });

});