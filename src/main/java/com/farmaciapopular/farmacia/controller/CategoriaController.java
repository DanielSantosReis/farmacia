package com.farmaciapopular.farmacia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.farmaciapopular.farmacia.model.Categoria;
import com.farmaciapopular.farmacia.repository.CategoriaRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoria")

public class CategoriaController {

		@Autowired
		private CategoriaRepository repository;
		
		@GetMapping
		public ResponseEntity<List<Categoria>> GetAll(){
			return ResponseEntity.ok(repository.findAll());
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<Categoria> GetById(@PathVariable long id) {
			return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
		}
		
		@GetMapping("/tipo/{tipo}")
		public ResponseEntity<List<Categoria>> GetByTipo(@PathVariable String tipo) {
			return ResponseEntity.ok(repository.findAllByTipoContainingIgnoreCase(tipo));
		}
		
		@PostMapping
		public ResponseEntity<Categoria> post(@RequestBody Categoria categoria) {
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
		}
		
		@PutMapping
		public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
			return repository.findById(categoria.getId())
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(categoria)))
			.orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id não encontrado");
			});
		}
		
		@SuppressWarnings("rawtypes")
		@DeleteMapping("/{id}")
		public ResponseEntity delete(@PathVariable long id) {
			return repository.findById(id).map(response -> {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();})
			.orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
	 	}
	}
