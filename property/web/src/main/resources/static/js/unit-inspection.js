document.addEventListener("DOMContentLoaded", function () {
    const table = document.getElementById("inspectionChecklistTable");
    const toggleOnlyIssuesButton = document.getElementById("toggleOnlyIssues");

    if (!table) return;

    let onlyIssues = false;

    function isIssueStatus(status) {
        return status !== "OK" && status !== "N/A" && status !== "";
    }

    function getDetailRow(row) {
        const nextRow = row.nextElementSibling;

        if (nextRow && nextRow.classList.contains("condition-detail-row")) {
            return nextRow;
        }

        return null;
    }

    function updateRow(row) {
        const statusSelect = row.querySelector(".inspection-status");
        const notes = row.querySelector(".inspection-notes");

        if (!statusSelect || !notes) return;

        const status = statusSelect.value;
        const hasIssue = isIssueStatus(status);

        if (hasIssue) {
            row.classList.add("table-warning");
            notes.classList.remove("d-none");
        } else {
            row.classList.remove("table-warning");

            if (!notes.value || notes.value.trim() === "") {
                notes.classList.add("d-none");
            }
        }

        const detailRow = getDetailRow(row);

        if (onlyIssues) {
            row.classList.toggle("d-none", !hasIssue);

            if (detailRow) {
                detailRow.classList.add("d-none");
            }
        } else {
            row.classList.remove("d-none");

            if (detailRow) {
                detailRow.classList.add("d-none");
            }
        }
    }

    function updateAllRows() {
        const rows = table.querySelectorAll(".checklist-row");
        rows.forEach(updateRow);
    }

    table.addEventListener("change", function (event) {
        if (event.target.classList.contains("inspection-status")) {
            const row = event.target.closest(".checklist-row");
            updateRow(row);
        }
    });

    table.addEventListener("click", function (event) {
        if (event.target.classList.contains("toggle-condition-details")) {
            const row = event.target.closest(".checklist-row");
            const detailRow = getDetailRow(row);

            if (!detailRow) return;

            detailRow.classList.toggle("d-none");

            event.target.textContent = detailRow.classList.contains("d-none")
                ? "Details"
                : "Hide";
        }
    });

    if (toggleOnlyIssuesButton) {
        toggleOnlyIssuesButton.addEventListener("click", function () {
            onlyIssues = !onlyIssues;

            toggleOnlyIssuesButton.textContent = onlyIssues
                ? "Show All Items"
                : "Show Only Issues";

            updateAllRows();
        });
    }

    updateAllRows();

    const inspectionForm = document.getElementById("inspectionForm");
    const saveInspectionBtn = document.getElementById("saveInspectionBtn");

    if (inspectionForm) {
        inspectionForm.addEventListener("submit", function () {
            if (saveInspectionBtn) {
                saveInspectionBtn.disabled = true;
                saveInspectionBtn.textContent = "Saving inspection...";
            }

            if (typeof showLoading === "function") {
                showLoading("Saving inspection...");
            }
        });
    }
});