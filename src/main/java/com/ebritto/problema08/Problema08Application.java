package com.ebritto.problema08;



import java.util.ArrayList;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@SpringBootApplication
public class Problema08Application {

	public static void main(String[] args) {
		SpringApplication.run(Problema08Application.class, args);
	}
}

@Data
class Funcionario
{
	private long id;
	private String nome;
	private int idade;
	private float salario;
}

@RestController
class FuncionarioDAO
{
	private ArrayList<Funcionario> funcionarios;

	FuncionarioDAO()
	{
		funcionarios = new ArrayList<Funcionario>();
	}

	@GetMapping("/funcionario/{id}")
	public Funcionario read (@PathVariable long id){
		return funcionarios.stream().filter(v -> v.getId() == id).findFirst().get();
	}

	@PostMapping("/funcionario")
	public Funcionario create(@RequestBody Funcionario f) throws Exception{
		if(funcionarios.stream().anyMatch(e -> e.getId() == f.getId()))
			throw new Exception("Funcionario já cadastrado");

		funcionarios.add(f);
		return f;
	}

	@PutMapping("/funcionario")
	public Funcionario update(@RequestBody Funcionario f) throws Exception{
		Funcionario func = funcionarios.stream().filter(e -> e.getId() == f.getId()).findAny().get();
		if(func == null)
			throw new Exception("Funcionario não encontrado");

		funcionarios.set(funcionarios.indexOf(func), f);
		return f;
	}

	@DeleteMapping("/funcionario/{id}")
	public boolean delete(@PathVariable long id) throws Exception
	{
		Funcionario func = funcionarios.stream().filter(e -> e.getId() == id).findAny().get();
		if(func == null)
			throw new Exception("Funcionario não encontrado");

		return funcionarios.remove(func);
	}

}