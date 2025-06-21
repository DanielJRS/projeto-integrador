function toggleSidebar() {
        const sidebar = document.getElementById('sidebarFilters');
        sidebar.classList.toggle('show');
    }

    // Fecha o sidebar ao clicar fora dele (mobile)
    document.addEventListener('click', function(event) {
        const sidebar = document.getElementById('sidebarFilters');
        const toggleButton = document.querySelector('.mobile-filter-toggle');

        if (window.innerWidth <= 768) {
            if (!sidebar.contains(event.target) && !toggleButton.contains(event.target)) {
                sidebar.classList.remove('show');
            }
        }
    });