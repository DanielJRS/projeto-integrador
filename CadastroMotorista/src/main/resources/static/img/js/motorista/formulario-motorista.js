function toggleSenhaVisibility() {
        const senhaInput = document.getElementById('senha');
        const iconSenha = document.getElementById('iconSenha');

        if (senhaInput.type === 'password') {
            senhaInput.type = 'text';
            iconSenha.textContent = '🙈';
        } else {
            senhaInput.type = 'password';
            iconSenha.textContent = '👁️';
        }
    }

    // Máscaras para formatação
    document.getElementById('cpf').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})/, '$1-$2');
        e.target.value = value;
    });

    document.getElementById('celular').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/^(\d{2})(\d)/, '($1) $2');
        value = value.replace(/(\d{5})(\d)/, '$1-$2');
        e.target.value = value;
    });

    // Preview da foto selecionada
    document.getElementById('foto').addEventListener('change', function(e) {
        const file = e.target.files[0];
        const uploadSection = document.querySelector('.file-upload-section');
        const uploadText = document.querySelector('.file-upload-text');

        if (file) {
            uploadText.textContent = `Arquivo selecionado: ${file.name}`;
            uploadSection.style.borderColor = '#f9b000';
            uploadSection.style.backgroundColor = '#fff9e6';
        } else {
            uploadText.textContent = 'Selecione uma foto recente (formatos: JPG, PNG, GIF)';
            uploadSection.style.borderColor = '#dee2e6';
            uploadSection.style.backgroundColor = '#f8f9fa';
        }
    });