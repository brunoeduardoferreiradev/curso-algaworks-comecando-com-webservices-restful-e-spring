package com.algaworks.socialbooks.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosRepository livrosRepository;

	@GetMapping
	public List<Livro> listar() {
		return livrosRepository.findAll();
	}

	@PostMapping
	public void salvar(@RequestBody Livro livro) {
		livrosRepository.save(livro);
	}

	@GetMapping("/{id}")
	public Optional<Livro> buscar(@PathVariable("id") Long id) {
		return livrosRepository.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable("id") Long id) {
		livrosRepository.deleteById(id);
	}
	
}
