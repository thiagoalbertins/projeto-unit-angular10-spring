import { Component, OnInit } from '@angular/core';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-clientes-list',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.css']
})
export class ClientesListComponent implements OnInit {

  clientes: any;
  currentCliente = null;
  currentIndex = -1;
  title = '';
  nome= '';

  constructor(private clienteService: ClienteService) { }

  ngOnInit(): void {
    this.retrieveClientes();
  }

  retrieveClientes(): void {
    this.clienteService.getAll()
      .subscribe(
        data => {
          this.clientes = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  refreshList(): void {
    this.retrieveClientes();
    this.currentCliente = null;
    this.currentIndex = -1;
  }

  setActiveCliente(cliente, index): void {
    this.currentCliente = cliente;
    this.currentIndex = index;
  }

  removeAllClientes(): void {
    this.clienteService.deleteAll()
      .subscribe(
        response => {
          console.log(response);
          this.retrieveClientes();
        },
        error => {
          console.log(error);
        });
  }

  searchTitle(): void {
    this.clienteService.findByTitle(this.nome)
      .subscribe(
        data => {
          this.clientes = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }
}