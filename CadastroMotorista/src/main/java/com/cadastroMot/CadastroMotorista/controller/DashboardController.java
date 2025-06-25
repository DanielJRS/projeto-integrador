package com.cadastroMot.CadastroMotorista.controller;

    import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.domain.Frete;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import com.cadastroMot.CadastroMotorista.service.FreteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private CargaService cargaService;

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TransportadoraService transportadoraService;

    @Autowired
    private FreteService freteService;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("Entrou no dashboard");
        List<Carga> cargas = cargaService.listarTodos();
        model.addAttribute("cargas", cargas);

        long driverCount = motoristaService.listarTodos().size();
        long vehicleCount = veiculoService.listarTodos().size();
        long cargaCount = cargaService.listarTodos().size();
        long transpCount = transportadoraService.listarTodos().size();
        long empresaCount = empresaService.listarTodos().size();
        long freteCount = freteService.listarTodos().size();

        model.addAttribute("driverCount", driverCount);
        model.addAttribute("vehicleCount", vehicleCount);
        model.addAttribute("cargaCount", cargaCount);
        model.addAttribute("transpCount", transpCount);
        model.addAttribute("empresaCount", empresaCount);
        model.addAttribute("freteCount", freteCount);


        return "dashboard/index";
    }

    @GetMapping("/motoristas")
    public String dashboardMotoristas() {
        return "dashboard/motorista-index";
    }

    @GetMapping("/cargas")
    public String dashboardCargas() {
        return "dashboard/carga-index";
    }

    @GetMapping("/empresas")
    public String dashboardEmpresa() {
        return "dashboard/empresa-index";
    }

    @GetMapping("/veiculos")
    public String dashboardVeiculos() {
        return "dashboard/veiculo-index";
    }

    @GetMapping("/transportadoras")
    public String dashboardTransportadoras() {
        return "dashboard/transportadora-index";
    }

    @GetMapping("/fretes")
    public String dashboardFretes() {
        return "dashboard/frete-index";
    }

    @GetMapping("/motoristas-listartodos")
    public String listarTodosMotoristas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String transportadora,
            HttpSession session,
            Model model) {

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }

        List<Motorista> motoristas = motoristaService.buscarMotoristas(nome, transportadora);
        model.addAttribute("motoristas", motoristas);
        model.addAttribute("nome", nome);
        model.addAttribute("transportadora", transportadora);

        return "dashboard/mototristas-listartodos";
    }

    @GetMapping("/veiculos-listartodos")
    public String listarTodosVeiculos(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String modelo,
            Model model,
            HttpSession session) {

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }

        List<Veiculo> veiculos = veiculoService.buscarVeiculos(placa, modelo);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("placa", placa);
        model.addAttribute("modelo", modelo);

        return "dashboard/veiculos-listartodos";
    }

    @GetMapping("/empresas-listartodos")
    public String listarTodasEmpresas(
            @RequestParam(required = false) String razaoSocial,
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam(required = false) String cnpj,
            Model model,
            HttpSession session) {

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }

        List<Empresa> empresas = empresaService.buscarEmpresas(razaoSocial, nomeFantasia, cnpj);
        model.addAttribute("empresas", empresas);
        model.addAttribute("razaoSocial", razaoSocial);
        model.addAttribute("nomeFantasia", nomeFantasia);
        model.addAttribute("cnpj", cnpj);

        return "dashboard/empresas-listartodos";
    }

    @GetMapping("/cargas-listartodos")
    public String listarTodasCargas(Model model, HttpSession session) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }
        List<Carga> cargas = cargaService.listarTodos();
        model.addAttribute("cargas", cargas);
        return "dashboard/cargas-listartodos";
    }

    @GetMapping("/transportadoras-listartodos")
    public String listarTodasTransportadoras(
            @RequestParam(required = false) String razaoSocial,
            @RequestParam(required = false) String nomeFantasia,
            @RequestParam(required = false) String cnpj,
            Model model,
            HttpSession session) {
            
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }
    
        List<Transportadora> transportadoras = transportadoraService.buscarTransportadoras(
            razaoSocial, nomeFantasia, cnpj
        );
        model.addAttribute("transportadoras", transportadoras);
        model.addAttribute("razaoSocial", razaoSocial);
        model.addAttribute("nomeFantasia", nomeFantasia);
        model.addAttribute("cnpj", cnpj);
    
        return "dashboard/transportadoras-listartodos";
    }

    @GetMapping("/fretes-listartodos")
    public String listarTodosFretes(Model model, HttpSession session) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }
        List<Frete> fretes = freteService.listarTodos();
        model.addAttribute("fretes", fretes);
        return "dashboard/fretes-listartodos";
    }

    @GetMapping("/fretes-detalhar/{id}")
    public String detalharFrete(@PathVariable Long id, Model model, HttpSession session) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }
        Frete frete = freteService.buscarPorId(id).orElse(null);
        if (frete == null) {
            return "redirect:/dashboard/fretes-listartodos";
        }
        model.addAttribute("frete", frete);
        return "dashboard/detalhar-frete";
    }

}