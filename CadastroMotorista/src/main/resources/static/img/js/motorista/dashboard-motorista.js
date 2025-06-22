xfunction toggleSidebar() {
      const sidebar = document.querySelector('.sidebar');
      sidebar.classList.toggle('mobile-open');
  }

  document.addEventListener('click', function(event) {
      const sidebar = document.querySelector('.sidebar');
      const menuToggle = document.querySelector('.mobile-menu-toggle');

      if (window.innerWidth <= 768) {
          if (!sidebar.contains(event.target) && !menuToggle.contains(event.target)) {
              sidebar.classList.remove('mobile-open');
          }
      }
  });

  function animateCards() {
      const cards = document.querySelectorAll('.stat-card, .frete-item');

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

  document.addEventListener('DOMContentLoaded', function() {
      setTimeout(animateCards, 300);
  });

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

    document.addEventListener('DOMContentLoaded', function() {
        const welcomeTitle = document.querySelector('.dashboard-welcome h2');
        if (welcomeTitle) {
            const originalText = welcomeTitle.textContent;
            setTimeout(() => typeWriter(welcomeTitle, originalText, 100), 500);
        }
    });

    function animateCards() {
        const cards = document.querySelectorAll('.stat-card, .frete-item');

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

    document.addEventListener('DOMContentLoaded', function() {
        setTimeout(animateCards, 300);
    });

      document.addEventListener('DOMContentLoaded', function() {
        setTimeout(() => {
          if (typeof lucide !== 'undefined') {
            lucide.createIcons();
          } else {
            console.error('Lucide não foi carregado corretamente');
          }
        }, 100);
      });

      function toggleMobileMenu() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('sidebar-open');
      }