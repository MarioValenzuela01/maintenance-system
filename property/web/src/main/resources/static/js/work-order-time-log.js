document.addEventListener("DOMContentLoaded", function () {
    const errorModalElement = document.getElementById("timeLogErrorModal");

    if (errorModalElement && typeof bootstrap !== "undefined") {
        const errorModal = new bootstrap.Modal(errorModalElement);
        errorModal.show();
    }
});