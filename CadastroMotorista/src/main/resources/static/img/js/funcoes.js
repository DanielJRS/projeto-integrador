document.addEventListener('DOMContentLoaded', function() {
    const souTransportadoraCheckbox = document.getElementById('souTransportadora');
    const transportadoraFields = document.querySelectorAll('.div-test input, .div-test select, .div-test textarea');

    function atualizarCamposObrigatorios() {
      transportadoraFields.forEach(field => {
        if (field.type !== 'checkbox') {
          if (souTransportadoraCheckbox.checked) {
            field.setAttribute('required', '');
          } else {
            field.removeAttribute('required');
          }
        }
      });
    }

    atualizarCamposObrigatorios();

    souTransportadoraCheckbox.addEventListener('change', atualizarCamposObrigatorios);

  });