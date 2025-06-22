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

    @GetMapping("/aceitar-frete")
    public String aceitarFrete(@RequestParam Long cargaId,
                               HttpSession session,
                               Model model) {

        Carga carga = cargaService.buscarPorId(cargaId);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Motorista motorista = motoristaService.buscarPorId(usuarioLogado.getId());
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

        return "redirect:/motorista/dashboard";
    }

    @PostMapping("/frete/{id}/cancelar-frete")
    @Transactional
    public String cancelarFrete(@PathVariable Long id, HttpSession session){
        System.out.println("=== MÉTODO CANCELAR FRETE CHAMADO - ID: " + id + " ===");

        Optional<Frete> freteOptional = freteService.buscarPorId(id);

        if (!freteOptional.isPresent()) {
            return "redirect:/motorista/dashboard";
        }

        Frete frete = freteOptional.get();
        System.out.println("Frete encontrado: " + frete.getId());
        System.out.println("frete.getCargas() é null? " + (frete.getCargas() == null));

        frete.setStatus(TipoEstadoFrete.CANCELADO);

        Optional<Carga> cargaOptional = cargaRepository.findByFreteId(id);
        System.out.println("Busca direta por frete_id " + id + " encontrou: " + cargaOptional.isPresent());

        if (cargaOptional.isPresent()) { // <- Mudança aqui
            Carga carga = cargaOptional.get();
            System.out.println("Carga encontrada: " + carga.getId());
            System.out.println("Estado da carga ANTES: " + carga.getTipoEstadoCarga());
            carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
            carga.setFrete(null);
            System.out.println("Estado da carga DEPOIS: " + carga.getTipoEstadoCarga());

            try {
                cargaService.salvar(carga);
                freteService.salvar(frete);
                System.out.println("Salvamento realizado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao salvar: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("PROBLEMA: Nenhuma carga encontrada para o frete " + id);
            freteService.salvar(frete);
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

//        Optional<Empresa> empresa = empresaService.buscarEmpresaPorId(empresaId);
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
