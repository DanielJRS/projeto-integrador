package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/relatorios")
@PreAuthorize("hasRole('ADMIN')")
public class RelatorioAdminController {

    @Autowired private MotoristaService motoristaService;
    @Autowired private VeiculoService veiculoService;
    @Autowired private CargaService cargaService;
    @Autowired private EmpresaService empresaService;
    @Autowired private TransportadoraService transportadoraService;

    @GetMapping
    public String index() {
        return "relatorios/index";
    }

    // ============================ RELATÓRIO MOTORISTAS ============================

    @GetMapping("/motoristas")
    public String relatorioMotoristas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            Model model) {

        List<Motorista> motoristas = motoristaService.filtrar(nome, cidade, estado);
        model.addAttribute("motoristas", motoristas);
        model.addAttribute("nome", nome);
        model.addAttribute("cidade", cidade);
        model.addAttribute("estado", estado);
        return "relatorios/relatorio-motoristas";
    }

    @GetMapping("/motoristas/pdf")
    public void exportarMotoristasPdf(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            HttpServletResponse response) throws IOException {

        List<Motorista> motoristas = motoristaService.filtrar(nome, cidade, estado);
        prepararRespostaPdf(response, "motoristas.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        adicionarTitulo(document, "Relatório de Motoristas");

        PdfPTable tabela = new PdfPTable(5);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{3, 3, 2, 2, 3});
        adicionarCabecalhos(tabela, "Nome", "CPF", "Cidade", "Estado", "Celular");

        for (Motorista m : motoristas) {
            tabela.addCell(m.getNome());
            tabela.addCell(m.getCpf());
            tabela.addCell(m.getCidade());
            tabela.addCell(m.getEstado());
            tabela.addCell(m.getCelular());
        }

        document.add(tabela);
        document.close();
    }

    // ============================ RELATÓRIO VEÍCULOS ============================

    @GetMapping("/veiculos")
    public String relatorioVeiculos(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String marca,
            Model model) {

        List<Veiculo> veiculos = veiculoService.filtrar(placa, modelo, marca);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("placa", placa);
        model.addAttribute("modelo", modelo);
        model.addAttribute("marca", marca);
        return "relatorios/relatorio-veiculos";
    }

    @GetMapping("/veiculos/pdf")
    public void exportarVeiculosPdf(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String marca,
            HttpServletResponse response) throws IOException {

        List<Veiculo> veiculos = veiculoService.filtrar(placa, modelo, marca);
        prepararRespostaPdf(response, "veiculos.pdf");

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        adicionarTitulo(document, "Relatório de Veículos");

        PdfPTable tabela = new PdfPTable(5);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{2, 3, 3, 2, 3});
        adicionarCabecalhos(tabela, "Placa", "Modelo", "Marca", "Capacidade", "Chassi");

        for (Veiculo v : veiculos) {
            tabela.addCell(v.getPlaca());
            tabela.addCell(v.getModelo());
            tabela.addCell(v.getMarca());
            tabela.addCell(Optional.ofNullable(v.getCapacidadeCarga()).map(Object::toString).orElse(""));
            tabela.addCell(v.getChassi());
        }

        document.add(tabela);
        document.close();
    }

    // ============================ RELATÓRIO CARGAS ============================

    @GetMapping("/cargas")
    public String relatorioCargas(
            @RequestParam(required = false) String origemCidade,
            @RequestParam(required = false) String destinoCidade,
            @RequestParam(required = false) TipoCarga tipoCarga,
            Model model) {

        List<Carga> cargas = cargaService.filtrar(origemCidade, destinoCidade, tipoCarga);
        model.addAttribute("cargas", cargas);
        model.addAttribute("origemCidade", origemCidade);
        model.addAttribute("destinoCidade", destinoCidade);
        model.addAttribute("tipoCarga", tipoCarga);
        return "relatorios/relatorio-cargas";
    }

    @GetMapping("/cargas/pdf")
    public void exportarCargasPdf(
            @RequestParam(required = false) String origemCidade,
            @RequestParam(required = false) String destinoCidade,
            @RequestParam(required = false) TipoCarga tipoCarga,
            HttpServletResponse response) throws IOException {

        List<Carga> cargas = cargaService.filtrar(origemCidade, destinoCidade, tipoCarga);
        prepararRespostaPdf(response, "cargas.pdf");

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        adicionarTitulo(document, "Relatório de Cargas");

        PdfPTable tabela = new PdfPTable(7);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{3, 3, 3, 2, 3, 2, 2});
        adicionarCabecalhos(tabela, "Origem", "Destino", "Produto", "Espécie", "Veículo", "Preço", "Tipo");

        for (Carga c : cargas) {
            tabela.addCell(c.getOrigemCidade() + "/" + c.getOrigemEstado());
            tabela.addCell(c.getDestinoCidade() + "/" + c.getDestinoEstado());
            tabela.addCell(c.getProduto());
            tabela.addCell(c.getEspecie());
            tabela.addCell(c.getVeiculo());
            tabela.addCell(Optional.ofNullable(c.getPreco()).map(Object::toString).orElse(""));
            tabela.addCell(Optional.ofNullable(c.getTipoCarga()).map(Enum::toString).orElse(""));
        }

        document.add(tabela);
        document.close();
    }

    // ============================ RELATÓRIO EMPRESAS ============================

    @GetMapping("/empresas")
    public String relatorioEmpresas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String cnpj,
            Model model) {

        List<Empresa> empresas = empresaService.filtrar(nome, cidade, cnpj);
        model.addAttribute("empresas", empresas);
        model.addAttribute("nome", nome);
        model.addAttribute("cidade", cidade);
        model.addAttribute("cnpj", cnpj);
        return "relatorios/relatorio-empresas";
    }

    @GetMapping("/empresas/pdf")
    public void exportarEmpresasPdf(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String cnpj,
            HttpServletResponse response) throws IOException {

        List<Empresa> empresas = empresaService.filtrar(nome, cidade, cnpj);
        prepararRespostaPdf(response, "empresas.pdf");

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        adicionarTitulo(document, "Relatório de Empresas");

        PdfPTable tabela = new PdfPTable(5);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{3, 3, 3, 2, 2});
        adicionarCabecalhos(tabela, "Razão Social", "Nome Fantasia", "CNPJ", "Cidade", "Estado");

        for (Empresa e : empresas) {
            tabela.addCell(e.getRazaoSocial());
            tabela.addCell(e.getNomeFantasia());
            tabela.addCell(e.getCnpj());
            tabela.addCell(e.getCidade());
            tabela.addCell(e.getEstado());
        }

        document.add(tabela);
        document.close();
    }

    // ============================ RELATÓRIO TRANSPORTADORAS ============================

    @GetMapping("/transportadoras")
    public String relatorioTransportadoras(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String cnpj,
            Model model) {

        List<Transportadora> transportadoras = transportadoraService.filtrar(nome, cidade, cnpj);
        model.addAttribute("transportadoras", transportadoras);
        model.addAttribute("nome", nome);
        model.addAttribute("cidade", cidade);
        model.addAttribute("cnpj", cnpj);
        return "relatorios/relatorio-transportadoras";
    }

    @GetMapping("/transportadoras/pdf")
    public void exportarTransportadorasPdf(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String cnpj,
            HttpServletResponse response) throws IOException {

        List<Transportadora> transportadoras = transportadoraService.filtrar(nome, cidade, cnpj);
        prepararRespostaPdf(response, "transportadoras.pdf");

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        adicionarTitulo(document, "Relatório de Transportadoras");

        PdfPTable tabela = new PdfPTable(5);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{3, 3, 3, 2, 2});
        adicionarCabecalhos(tabela, "Razão Social", "Nome Fantasia", "CNPJ", "Cidade", "Estado");

        for (Transportadora t : transportadoras) {
            tabela.addCell(t.getRazaoSocial());
            tabela.addCell(t.getNomeFantasia());
            tabela.addCell(t.getCnpj());
            tabela.addCell(t.getCidade());
            tabela.addCell(t.getEstado());
        }

        document.add(tabela);
        document.close();
    }

    // ============================ MÉTODOS AUXILIARES ============================

    private void prepararRespostaPdf(HttpServletResponse response, String nomeArquivo) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivo);
    }

    private void adicionarTitulo(Document document, String titulo) throws DocumentException {
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
        Paragraph title = new Paragraph(titulo, fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));
    }

    private void adicionarCabecalhos(PdfPTable tabela, String... colunas) {
        for (String coluna : colunas) {
            PdfPCell header = new PdfPCell(new Phrase(coluna));
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setPadding(5);
            tabela.addCell(header);
        }
    }
}
