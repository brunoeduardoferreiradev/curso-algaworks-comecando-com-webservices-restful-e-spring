package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.LivrosService;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

@RestController
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;

	// LISTAR
	@GetMapping
	public ResponseEntity<List<Livro>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
	}

	// SALVAR
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
		// Salvamos o livro
		livro = livrosService.salvar(livro);

		// Criou uma uri para representar a uri do objeto salvo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(livro.getId()).toUri();

		// Usando o build para criar o response da forma correta
		return ResponseEntity.created(uri).build();
	}

	// BUSCAR
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Optional<Livro> livro = null;
		try {
			livro = livrosService.buscar(id);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}

	// DELETAR
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		try {
			livrosService.deletar(id);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		// Retorna 204 no content
		return ResponseEntity.noContent().build();

	}

	// ATUALIZAR
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);

		try {
			livrosService.atualizar(livro);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
