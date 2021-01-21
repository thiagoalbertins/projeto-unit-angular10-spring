import { Component, OnInit } from '@angular/core';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-add-cliente',
  templateUrl: './add-cliente.component.html',
  styleUrls: ['./add-cliente.component.css']
})
export class AddClienteComponent implements OnInit {
  cliente = {
    nome: '',
	  cpf: '',
	  email: '',
	  dataNascimento: '',
	  sexo: '',
	  nomeSocial: '',
	  apelido: '',
	  telefone: ''
  };
  submitted = false;
  message = '';

  constructor(private clienteService: ClienteService) { }

  ngOnInit(): void {
  }

  saveCliente(): void {
    const data = {
      nome: this.cliente.nome,
      cpf: this.cliente.cpf,
      email: this.cliente.email,
      dataNascimento: this.cliente.dataNascimento,
      sexo: this.cliente.sexo,
      nomeSocial: this.cliente.nomeSocial,
      apelido: this.cliente.apelido,
      telefone: this.cliente.telefone
    };

    this.clienteService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
        },
        error => {
          this.message = error.error
          console.log(error);
          
        });
  }

  newCliente(): void {
    this.submitted = false;
    this.cliente = {
      nome: '',
      cpf: '',
      email: '',
      dataNascimento: '',
      sexo: '',
      nomeSocial: '',
      apelido: '',
      telefone: ''
    };
  }

}