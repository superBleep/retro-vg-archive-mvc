document.addEventListener("DOMContentLoaded", () => {
    const selectedPlatformsContainer = document.getElementById('selectedPlatforms');
    const platformSelect = document.getElementById('platformSelect');
    const platformsHiddenInput = document.getElementById('platformsHidden');

    const initialSelected = Array
        .from(selectedPlatformsContainer.querySelectorAll('[data-id]'))
        .map(el => el.getAttribute('data-id'));

    initialSelected.forEach(id => {
        const option = platformSelect.querySelector(`option[value="${id}"]`);
        if (option) option.remove();
    });

    window.addPlatform = function () {
        const errorDiv = document.getElementById('platformError');
        errorDiv.style.display = "none";
        errorDiv.textContent = "";

        const selectedId = platformSelect.value;
        const selectedOption = platformSelect.options[platformSelect.selectedIndex];
        const selectedText = selectedOption.text;

        if (!selectedId || selectedPlatformsContainer.querySelector(`[data-id='${selectedId}']`)) return;

        const badge = document.createElement('span');

        badge.className = 'badge bg-info text-dark fs-6 px-3 py-2 d-flex align-items-center';
        badge.setAttribute('data-id', selectedId);
        badge.innerHTML = `<span class="me-2">${selectedText}</span><i class="fas fa-times cursor-pointer ms-1" onclick="removePlatform(this)"></i>`;

        selectedPlatformsContainer.appendChild(badge);
        selectedOption.remove();

        updateHiddenPlatforms();
    };

    window.removePlatform = function (icon) {
        const badges = selectedPlatformsContainer.querySelectorAll('[data-id]');
        const errorDiv = document.getElementById('platformError');

        if (badges.length <= 1) {
            errorDiv.textContent = "At least one platform must remain.";
            errorDiv.style.display = "block";
            return;
        }

        errorDiv.style.display = "none";
        errorDiv.textContent = "";

        const badge = icon.parentElement;
        const platformId = badge.getAttribute('data-id');
        const platformName = badge.querySelector('span').innerText;

        const newOption = document.createElement('option');
        newOption.value = platformId;
        newOption.text = platformName;
        platformSelect.appendChild(newOption);

        badge.remove();
        updateHiddenPlatforms();
    };

    function updateHiddenPlatforms() {
        const selected = Array
            .from(selectedPlatformsContainer.querySelectorAll('[data-id]'))
            .map(b => b.getAttribute('data-id'));

        platformsHiddenInput.value = selected.join(',');
    }
});