class SortTable {
	constructor(tableId) {
		this.table = document.getElementById(tableId);
		this.tbody = this.table.tBodies[0];
		this.sortDirections = {};

		this.init();
	}

	init() {
		const headers = this.table.querySelectorAll('th[data-sortable="true"]');

		headers.forEach((th, index) => {
			th.style.cursor = "pointer";

			if (!th.querySelector('i')) {
				const icon = document.createElement('i');

				icon.id = `${this.table.id}_sortIcon_${index}`;

				th.appendChild(icon);
			}

			th.addEventListener('click', () => this.sortColumn(index));
		});
	}

	sortColumn(colIndex) {
		const rows = Array.from(this.tbody.rows);
		const currentDir = this.sortDirections[colIndex] === "asc" ? "desc" : "asc";

		this.sortDirections[colIndex] = currentDir;

		rows.sort((a, b) => {
			let aText = a.cells[colIndex].textContent.trim();
			let bText = b.cells[colIndex].textContent.trim();

			const aVal = Date.parse(aText) || parseFloat(aText) || aText.toLowerCase();
			const bVal = Date.parse(bText) || parseFloat(bText) || bText.toLowerCase();

			if (aVal < bVal) return currentDir === "asc" ? -1 : 1;
			if (aVal > bVal) return currentDir === "asc" ? 1 : -1;

			return 0;
		});

	    rows.forEach(row => this.tbody.appendChild(row));

	    const icons = this.table.querySelectorAll('i[id^="' + this.table.id + '_sortIcon_"]');
		icons.forEach(icon => (icon.className = ""));

    	const currentIcon = document.getElementById(`${this.table.id}_sortIcon_${colIndex}`);
    	currentIcon.className = currentDir === "asc" ? "fa-solid fa-sort-up" : "fa-solid fa-sort-down";
  }
}