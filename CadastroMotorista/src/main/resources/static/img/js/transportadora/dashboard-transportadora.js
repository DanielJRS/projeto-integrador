function toggleSidebar() {
        const sidebar = document.querySelector('.sidebar');
        sidebar.classList.toggle('mobile-open');
    }

    // Fechar sidebar ao clicar fora (mobile)
    document.addEventListener('click', function(event) {
        const sidebar = document.querySelector('.sidebar');
        const menuToggle = document.querySelector('.mobile-menu-toggle');

        if (window.innerWidth <= 768) {
            if (!sidebar.contains(event.target) && !menuToggle.contains(event.target)) {
                sidebar.classList.remove('mobile-open');
            }
        }
    });

    // Animação de entrada para cards
    function animateCards() {
        const cards = document.querySelectorAll('.stat-card, .action-card, .frete-item, .activity-item');

        cards.forEach((card, index) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(30px)';

            setTimeout(() => {
                card.style.transition = 'all 0.6s ease';
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, index * 100);
        });
    }

    // Executar animação quando a página carregar
    document.addEventListener('DOMContentLoaded', function() {
        setTimeout(animateCards, 300);
    });

    // Efeito de typing para o título de boas-vindas
    function typeWriter(element, text, speed = 50) {
        let i = 0;
        element.innerHTML = '';

        function type() {
            if (i < text.length) {
                element.innerHTML += text.charAt(i);
                i++;
                setTimeout(type, speed);
            }
        }
        type();
    }

    // Aplicar efeito de typing no título principal
    document.addEventListener('DOMContentLoaded', function() {
        const welcomeTitle = document.querySelector('.dashboard-welcome h2');
        if (welcomeTitle) {
            const originalText = welcomeTitle.textContent;
            setTimeout(() => typeWriter(welcomeTitle, originalText, 80), 1000);
        }
    });