package br.com.treinaweb.twprojects.web.clients.controllers;

import br.com.treinaweb.twprojects.core.models.Client;
import br.com.treinaweb.twprojects.core.repositories.ClientRepository;
import br.com.treinaweb.twprojects.web.clients.dtos.ClientViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.treinaweb.twprojects.web.clients.dtos.ClientForm;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Controller
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {


    private final ClientRepository clientRepository;

    @GetMapping

    public ModelAndView index() {
        var clients = clientRepository.findAll()
            .stream()
            .map(ClientViewModel::of)
            .toList();
        var model = Map.of("clients", clients);
        return new ModelAndView("clients/index", model);
    }

    //Listar clientes no formato JSON
    @GetMapping("/listar")
    @ResponseBody
    public List<ClientViewModel> listar() {
        List<ClientViewModel> clients = clientRepository.findAll()
                .stream()
                .map(ClientViewModel::of)
                .toList();
        return clients;
    }


    @GetMapping("/create")
    public ModelAndView create() {
        var model = Map.of("clientForm", new ClientForm());
        return new ModelAndView("clients/create", model);
    }

    @PostMapping("/create")
    public String create(ClientForm clientForm) {
        var client = clientForm.toClient();
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var clientForm = clientRepository.findById(id)
                .map(ClientViewModel::of)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));
        var model = Map.of("clientForm", clientForm);
        return new ModelAndView("clients/edit", model);

    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ClientForm clientForm) {
         if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado");
        }
        var client = clientForm.toClient();
        client.setId(id);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado");
        }
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}


