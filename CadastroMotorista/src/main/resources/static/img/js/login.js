// Loading animation for login button
  function showLoading(button) {
      button.classList.add('loading');
      button.innerHTML = '';
  }

  // Form validation and enhancement
  document.addEventListener('DOMContentLoaded', function() {
      const inputs = document.querySelectorAll('.form-input');

      inputs.forEach(input => {
          // Add focus animation
          input.addEventListener('focus', function() {
              this.parentElement.parentElement.classList.add('focused');
          });

          input.addEventListener('blur', function() {
              this.parentElement.parentElement.classList.remove('focused');
          });

          // Real-time validation
          input.addEventListener('input', function() {
              if (this.validity.valid) {
                  this.style.borderColor = '#28a745';
              } else if (this.value.length > 0) {
                  this.style.borderColor = '#dc3545';
              } else {
                  this.style.borderColor = '#e9ecef';
              }
          });
      });

      // Animate registration buttons
      const registerButtons = document.querySelectorAll('.btn-register');
      registerButtons.forEach((button, index) => {
          button.style.opacity = '0';
          button.style.transform = 'translateY(20px)';

          setTimeout(() => {
              button.style.transition = 'all 0.6s ease';
              button.style.opacity = '1';
              button.style.transform = 'translateY(0)';
          }, 1000 + (index * 150));
      });

      // Add ripple effect to buttons
      document.querySelectorAll('.btn-login, .btn-register').forEach(button => {
          button.addEventListener('click', function(e) {
              const ripple = document.createElement('span');
              const rect = this.getBoundingClientRect();
              const size = Math.max(rect.width, rect.height);
              const x = e.clientX - rect.left - size / 2;
              const y = e.clientY - rect.top - size / 2;

              ripple.style.width = ripple.style.height = size + 'px';
              ripple.style.left = x + 'px';
              ripple.style.top = y + 'px';
              ripple.classList.add('ripple');

              this.appendChild(ripple);

              setTimeout(() => {
                  ripple.remove();
              }, 600);
          });
      });
  });

  // Add CSS for ripple effect
  const style = document.createElement('style');
  style.textContent = `
      .ripple {
          position: absolute;
          border-radius: 50%;
          background: rgba(255, 255, 255, 0.6);
          transform: scale(0);
          animation: ripple-animation 0.6s linear;
          pointer-events: none;
      }

      @keyframes ripple-animation {
          to {
              transform: scale(2);
              opacity: 0;
          }
      }

      .btn-login, .btn-register {
          position: relative;
          overflow: hidden;
      }
  `;
  document.head.appendChild(style);