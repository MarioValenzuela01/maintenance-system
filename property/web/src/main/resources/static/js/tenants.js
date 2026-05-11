function createCatInputs() {
    const catsCount = document.getElementById("catsCount");
    const container = document.getElementById("catNamesContainer");
    const hidden = document.getElementById("catNames");

    if (!catsCount || !container || !hidden) return;

    function render() {
        const count = parseInt(catsCount.value) || 0;
        container.innerHTML = "";

        for (let i = 0; i < count; i++) {
            const div = document.createElement("div");
            div.className = "mb-2";

            div.innerHTML = `
                <label>Cat ${i + 1} Name</label>
                <input type="text" class="form-control cat-name">
            `;

            container.appendChild(div);
        }
    }

    function updateHidden() {
        const values = Array.from(container.querySelectorAll(".cat-name"))
            .map(input => input.value.trim())
            .filter(value => value !== "");

        hidden.value = values.join(", ");
    }

    catsCount.addEventListener("input", render);
    container.addEventListener("input", updateHidden);
    document.querySelector("form").addEventListener("submit", updateHidden);

    render();
}

function createDogInputs() {
    const dogsCount = document.getElementById("dogsCount");
    const container = document.getElementById("dogNamesContainer");
    const hiddenDogInfo = document.getElementById("dogInfo");
    const hiddenDogNames = document.getElementById("dogNames");

    if (!dogsCount || !container || !hiddenDogInfo || !hiddenDogNames) return;

    function render() {
        const count = parseInt(dogsCount.value) || 0;
        container.innerHTML = "";

        for (let i = 0; i < count; i++) {
            const div = document.createElement("div");
            div.className = "row mb-2";

            div.innerHTML = `
                <div class="col-md-6">
                    <label>Dog ${i + 1} Name</label>
                    <input type="text" class="form-control dog-name">
                </div>

                <div class="col-md-6">
                    <label>Dog ${i + 1} Breed</label>
                    <input type="text" class="form-control dog-breed">
                </div>
            `;

            container.appendChild(div);
        }
    }

    function updateHidden() {
        const names = container.querySelectorAll(".dog-name");
        const breeds = container.querySelectorAll(".dog-breed");

        const dogInfoValues = [];
        const dogNameValues = [];

        for (let i = 0; i < names.length; i++) {
            const name = names[i].value.trim();
            const breed = breeds[i].value.trim();

            if (name !== "") {
                dogNameValues.push(name);
            }

            if (name !== "" || breed !== "") {
                dogInfoValues.push(name + ":" + breed);
            }
        }

        hiddenDogNames.value = dogNameValues.join(", ");
        hiddenDogInfo.value = dogInfoValues.join(", ");
    }

    dogsCount.addEventListener("input", render);
    container.addEventListener("input", updateHidden);
    document.querySelector("form").addEventListener("submit", updateHidden);

    render();
}

function formatPhoneNumber(input) {

    let numbers = input.value.replace(/\D/g, '');

    // Limit to 10 digits
    numbers = numbers.substring(0, 10);

    // 1-3 digits
    if (numbers.length <= 3) {
        input.value = numbers;
    }

    // 4-6 digits
    else if (numbers.length <= 6) {
        input.value =
            `(${numbers.substring(0, 3)}) ${numbers.substring(3)}`;
    }

    // Exactly 7 digits → unknown area code
    else if (numbers.length === 7) {
        input.value =
            `(---) ${numbers.substring(0, 3)}-${numbers.substring(3)}`;
    }

    // 8-10 digits → normal phone format
    else {
        input.value =
            `(${numbers.substring(0, 3)}) ${numbers.substring(3, 6)}-${numbers.substring(6)}`;
    }
}

document.addEventListener("DOMContentLoaded", function () {
    createDogInputs();
    createCatInputs();
});