let endLeaseModal;

document.addEventListener("DOMContentLoaded", function () {

    const modal =
        document.getElementById("endLeaseModal");

    if (!modal) return;

    endLeaseModal =
        new bootstrap.Modal(modal);

});

function openEndLeaseModal(button) {

    const leaseId =
        button.dataset.id;

    const form =
        document.getElementById("endLeaseForm");

    if (!form) return;

    form.action =
        "/leases/end/" + leaseId;

    document
        .getElementById("customEndDate")
        .disabled = true;

    document
        .querySelector(
            "input[value='today']"
        )
        .checked = true;

    endLeaseModal.show();

}

function toggleEndDate() {

    const enabled =
        document
            .querySelector(
                "input[value='custom']"
            )
            .checked;

    document
        .getElementById(
            "customEndDate"
        )
        .disabled =
        !enabled;

}