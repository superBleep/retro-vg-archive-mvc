function setupSelectableCollection({
    containerId,       // Badges container
    selectId,          // Selector
    hiddenInputId,     // Selected ids string
    errorDivId,        // Error message (if it's necessary)
    minSelected = 1    // Min. nr. of items
}) {
    const container = document.getElementById(containerId);
    const select = document.getElementById(selectId);
    const hiddenInput = document.getElementById(hiddenInputId);
    const errorDiv = document.getElementById(errorDivId);

    updateHidden();

    const initiallySelected = Array.from(container.querySelectorAll('[data-id]')).map(el => el.getAttribute('data-id'));

    initiallySelected.forEach(id => {
        const option = select.querySelector(`option[value="${id}"]`);
        if (option) option.remove();
    });

    window[`addTo_${containerId}`] = function () {
        if (errorDiv) {
            errorDiv.style.display = "none";
            errorDiv.textContent = "";
        }

        const selectedId = select.value;
        if (!selectedId || container.querySelector(`[data-id='${selectedId}']`)) return;

        const selectedOption = select.options[select.selectedIndex];
        const selectedText = selectedOption.text;

        const badge = document.createElement('span');
        badge.className = 'badge bg-info text-dark fs-6 px-3 py-2 d-flex align-items-center';
        badge.setAttribute('data-id', selectedId);
        badge.innerHTML = `<span class="me-2">${selectedText}</span><i class="fas fa-times cursor-pointer ms-1" onclick="removeFrom_${containerId}(this)"></i>`;

        container.appendChild(badge);
        selectedOption.remove();

        updateHidden();
    };

    window[`removeFrom_${containerId}`] = function (icon) {
        const badges = container.querySelectorAll('[data-id]');
        if (badges.length <= minSelected) {
            errorDiv.textContent = `At least ${minSelected} item(s) must remain.`;
            errorDiv.style.display = "block";
            return;
        }

        errorDiv.style.display = "none";
        errorDiv.textContent = "";

        const badge = icon.parentElement;
        const itemId = badge.getAttribute('data-id');
        const itemName = badge.querySelector('span').innerText;

        const newOption = document.createElement('option');
        newOption.value = itemId;
        newOption.text = itemName;
        select.appendChild(newOption);

        badge.remove();
        updateHidden();
    };

    function updateHidden() {
        const selected = Array.from(container.querySelectorAll('[data-id]')).map(b => b.getAttribute('data-id'));
        hiddenInput.value = selected.join(',');
    }
}
