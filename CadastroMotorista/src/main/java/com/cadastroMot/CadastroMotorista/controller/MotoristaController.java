package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
    @RequestMapping("/motoristas")

    public class MotoristaController {

        private final MotoristaService motoristaService;

        @Autowired
        public MotoristaController(MotoristaService motoristaService){
            this.motoristaService = motoristaService;
        }

        @GetMapping("/")
        public String index (){

            return "index";
        };

        @GetMapping("/novo")
        public String formulario (Model model) {
            model.addAttribute("motorista", new Motorista());
            return "/motoristas/formulario-motorista";
        }

    @PostMapping("/salvar")
    public String salvar(Motorista motorista, @RequestParam("arquivoFoto") MultipartFile arquivoFoto) throws IOException {
        if (!arquivoFoto.isEmpty()) {
            motorista.setFoto(arquivoFoto.getBytes());
        }

        motoristaService.salvar(motorista);
        return "redirect:/motoristas/lista";
    }

        @GetMapping("/lista")
        public String listar (Model model){
            model.addAttribute("motoristas", motoristaService.listarTodos());
            return "/motoristas/lista-motorista";
        }

        @GetMapping("/motoristas/foto/{id}")
        @ResponseBody
        public ResponseEntity<byte[]> exibirFoto(@PathVariable Long id) {
            Motorista motorista = motoristaService.buscarPorId(id);
            if (motorista.getFoto() != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(motorista.getTipoFoto()));
                return new ResponseEntity<>(motorista.getFoto(), headers, HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        }

    }
