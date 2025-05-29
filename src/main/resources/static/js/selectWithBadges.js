function selectWithBadges({
    containerId, selectorId, hiddenStringId, funcSuffix, minOne
}) {
    const container = document.getElementById(containerId);
    const selector = document.getElementById(selectorId);
    const hiddenString = document.getElementById(hiddenStringId);

    updateHiddenString();

    // Remove already present items from the selector
    const initialSelected = Array
        .from(container.querySelectorAll('[data-id]'))
        .map(el => el.getAttribute('data-id'));

    initialSelected.forEach(id => {
        const option = selector.querySelector(`option[value="${id}"]`)

        if (option)
            option.remove();
    });

    // Add function
    window[`add_${funcSuffix}`] = function () {
        const errorDiv = document.getElementById('errorDiv');

        // Clear the previous error, if it exists
        errorDiv.style.display = "none";
        errorDiv.textContent = "";

        const selectedId = selector.value;
        const selectedOption = selector.options[selector.selectedIndex];
        const selectedText = selectedOption.text;

        // No value or already present
        if (!selectedId || container.querySelector(`[data-id='${selectedId}']`))
            return;

        // Create selected item badge
        const badge = document.createElement('span')

        badge.className = 'badge bg-info text-dark fs-6 px-3 py-2 d-flex align-items-center';
        badge.setAttribute('data-id', selectedId);
        badge.innerHTML = `<span class="me-2">${selectedText}</span><i class="fas fa-times cursor-pointer ms-1" onclick="remove_${funcSuffix}(this)"></i>`;

        container.appendChild(badge);
        selectedOption.remove();

        // Flush hidden list
        updateHiddenString();
    }

    // Remove badge
    window[`remove_${funcSuffix}`] = function (icon) {
        const badges = container.querySelectorAll('[data-id]');
        const errorDiv = document.getElementById('errorDiv');

        // Check if there should be at least one option
        if (minOne && badges.length <= 1) {
            errorDiv.textContent = "At least one option must remain.";
            errorDiv.style.display = "block";

            return;
        }

        // Clear the previous error, if it exists
        errorDiv.style.display = "none";
        errorDiv.textContent = "";

        const delBadge = icon.parentElement;
        const delBadgeId = delBadge.getAttribute('data-id');
        const delBadgeText = delBadge.querySelector('span').innerText;

        // Add option back to the list
        const newOption = document.createElement('option');
        newOption.value = delBadgeId;
        newOption.text = delBadgeText;
        selector.appendChild(newOption);

        // Remove badge and flush hidden list
        delBadge.remove();

        updateHiddenString();
    }

    // Flush hidden list
    function updateHiddenString() {
        const selected = Array
            .from(container.querySelectorAll('[data-id]'))
            .map(sel => sel.getAttribute('data-id'));

        hiddenString.value = selected.join(',');
    }
}