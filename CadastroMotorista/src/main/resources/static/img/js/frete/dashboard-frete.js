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
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

lucide.createIcons();