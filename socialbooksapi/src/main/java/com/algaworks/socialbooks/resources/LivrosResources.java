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
	public ResponseEntity<Void> salvar(@RequestBody Livro livro) {
		// Salvamos o livro
		livro = livrosRepository.save(livro);
		
		// Criou uma uri para representar a uri do objeto salvo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id").buildAndExpand(livro.getId()).toUri();
		
		// Usando o build para criar o response da forma correta
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Optional<Livro> livro = livrosRepository.findById(id);
        
		// se o livro nao for encontrado ele retorna status 404 
		if (livro.isEmpty() ==  true) {
           return ResponseEntity.notFound().build();
		}
		
        // Aqui é retornado um status = 200 e o objeto livro no corpo da requisiçao
		return ResponseEntity.status(HttpStatus.OK).body(livro);
	}

	@DeleteMapping("/{id}")
	public void deletar(@PathVariable("id") Long id) {
		livrosRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public void atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
		livro.setId(id);
		livrosRepository.save(livro);
	}

}
