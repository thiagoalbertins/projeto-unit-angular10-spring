import { Component, OnInit } from '@angular/core';
import { ClienteService } from 'src/app/services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-cliente-details',
  templateUrl: './cliente-details.component.html',
  styleUrls: ['./cliente-details.component.css']
})
export class ClienteDetailsComponent implements OnInit {
  currentCliente = null;
  message = '';

  constructor(
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.message = '';
    this.getCliente(this.route.snapshot.paramMap.get('id'));
  }

  getCliente(id): void {
    this.clienteService.get(id)
      .subscribe(
        data => {
          this.currentCliente = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  updatePublished(status): void {
    const data = {
      title: this.currentCliente.title,
      description: this.currentCliente.description,
      published: status
    };

    this.clienteService.update(this.currentCliente.id, data)
      .subscribe(
        response => {
          this.currentCliente.published = status;
          console.log(response);
        },
        error => {
          console.log(error);
        });
  }

  updateCliente(): void {
    this.clienteService.update(this.currentCliente.id, this.currentCliente)
      .subscribe(
        response => {
          console.log(response);
          this.message = 'Cliente foi atualizado com sucesso!';
        },
        error => {
          console.log(error);
          this.message = error.error;
        });
  }

  deleteCliente(): void {
    this.clienteService.delete(this.currentCliente.id)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/cliente']);
        },
        error => {
          console.log(error);
        });
  }
}