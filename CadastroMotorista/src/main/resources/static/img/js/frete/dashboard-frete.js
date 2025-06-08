function confirmarCancelamento() {
      document.getElementById('cancelarModal').style.display = 'flex';
    }

    function confirmarFinalizacao() {
      document.getElementById('finalizarModal').style.display = 'flex';
    }

    function fecharModal() {
      document.getElementById('cancelarModal').style.display = 'none';
      document.getElementById('finalizarModal').style.display = 'none';
    }

    window.onclick = function(event) {
      const cancelarModal = document.getElementById('cancelarModal');
      const finalizarModal = document.getElementById('finalizarModal');
      if (event.target === cancelarModal) {
        cancelarModal.style.display = 'none';
      }
      if (event.target === finalizarModal) {
        finalizarModal.style.display = 'none';
      }
    }