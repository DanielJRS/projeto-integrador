package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import com.cadastroMot.CadastroMotorista.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FreteController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private CargaService cargaService;

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private CargaRepository cargaRepository;

    @Transactional
    @GetMapping("/aceitar-frete")
    public String aceitarFrete(@RequestParam Long cargaId,
                               HttpSession session,
                               Model model) {

        Carga carga = cargaService.buscarPorId(cargaId);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        Long motoristaId = usuarioLogado.getMotorista().getId();

        Motorista motorista = motoristaService.buscarPorId(motoristaId);

        motorista.getCargas().size();

        Empresa empresa = carga.getEmpresaCarga();

        Frete frete = new Frete();
        frete.setCargas(carga);
        frete.setMotoristaFrete(motorista);
        frete.setEmpresaFrete(empresa);
        frete.setOrigemCidade(carga.getOrigemCidade());
        frete.setOrigemEstado(carga.getOrigemEstado());
        frete.setDestinoCidade(carga.getDestinoCidade());
        frete.setDestinoEstado(carga.getDestinoEstado());
        frete.setProduto(carga.getProduto());
        frete.setPesoTotal(carga.getPesoTotal());
        frete.setValor(carga.getPrecoFrete().toString());
        frete.setStatus(TipoEstadoFrete.ATIVO);
        frete.setValorCarga(carga.getPreco().toString());

        carga.setFrete(frete);
        carga.setTipoEstadoCarga(TipoEstadoCarga.ANDAMENTO);

        freteService.salvar(frete);
        cargaService.salvar(carga);

        return "redirect:/motorista/dashboard";
    }

    @PostMapping("/frete/{id}/finalizar-frete")
    public String finalizarFrete(@PathVariable Long id, HttpSession session){

        Optional<Frete> frete = freteService.buscarPorId(id);
        frete.get().setStatus(TipoEstadoFrete.FINALIZADO);
        freteService.salvar(frete.orElse(null));

        if(frete.get().getCargas() != null) {
            Carga carga = frete.get().getCargas();
            carga.setTipoEstadoCarga(TipoEstadoCarga.INDISPONIVEL);
            cargaService.salvar(carga);
        }

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario != null && tipoUsuario.toString().equals("ADMIN")) {
            return "redirect:/dashboard/fretes-listartodos";
        }
        return "redirect:/motorista/dashboard";
    }

    @PostMapping("/frete/{id}/cancelar-frete")
    @Transactional
    public String cancelarFrete(@PathVariable Long id, HttpSession session){

        Optional<Frete> freteOptional = freteService.buscarPorId(id);

        if (!freteOptional.isPresent()) {
            return "redirect:/motorista/dashboard";
        }

        Frete frete = freteOptional.get();

        frete.setStatus(TipoEstadoFrete.CANCELADO);

        Optional<Carga> cargaOptional = cargaRepository.findByFreteId(id);

        if (cargaOptional.isPresent()) {
            Carga carga = cargaOptional.get();
            carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
            carga.setFrete(null);

            try {
                cargaService.salvar(carga);
                freteService.salvar(frete);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            freteService.salvar(frete);
        }

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario != null && tipoUsuario.toString().equals("ADMIN")) {
            return "redirect:/dashboard/fretes-listartodos";
        }
        return "redirect:/motorista/dashboard";
    }

    @GetMapping("/frete/{id}")
    @Transactional
    public String detalheFrete(@PathVariable Long id,
                               Model model,
                               HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        model.addAttribute("TipoUsuario", usuarioLogado.getTipo());

        Frete frete = freteService.buscarPorId(id).orElse(null);

        if (frete == null) {
            return "redirect:/motorista";
        }

        Empresa empresa = frete.getEmpresaFrete();

        frete.setNomeFantasia(empresa.getNomeFantasia());
        frete.setTelefone(empresa.getTelefone());
        frete.setEmail(empresa.getEmail());
        frete.setCnpj(empresa.getCnpj());

        model.addAttribute("empresa", empresa);
        model.addAttribute("frete", frete);

        return "/fretes/dashboard-fretes";
    }
}
