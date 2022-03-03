package com.escorpion.primeiroprojetorest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MeuResource {

    // @RequestMapping(value = "/api/clientes/{id}", method = RequestMethod.GET)
    @GetMapping("/api/clientes/{id}")
    public Cliente obter(@PathVariable Long id) {
        return new Cliente("Fulaninho", "000.0000.000-00");
    }

    @PostMapping("/api/clientes")
    public Cliente salvar(@RequestBody Cliente cliente) {
        return cliente;
    }

    @DeleteMapping("/api/clientes/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {

    }

    @PutMapping("/api/clientes/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return cliente;
    }
}
