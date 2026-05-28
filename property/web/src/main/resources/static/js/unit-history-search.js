document.addEventListener("DOMContentLoaded", function () {

    const input = document.getElementById("unitHistorySearchInput");
    const form = document.getElementById("unitHistorySearchForm");

    if (!input || !form) return;

    input.focus();

    const valueLength = input.value.length;
    input.setSelectionRange(valueLength, valueLength);

    let timeout;

    input.addEventListener("input", function () {

        clearTimeout(timeout);

        timeout = setTimeout(() => {
            form.submit();
        }, 300);

    });

});