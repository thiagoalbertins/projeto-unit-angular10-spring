import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientesListComponent } from './components/clientes-list/clientes-list.component';
import { ClienteDetailsComponent } from './components/cliente-details/cliente-details.component';
import { AddClienteComponent } from './components/add-cliente/add-cliente.component';

const routes: Routes = [
  { path: '', redirectTo: 'cliente', pathMatch: 'full' },
  { path: 'cliente', component: ClientesListComponent },
  { path: 'cliente/:id', component: ClienteDetailsComponent },
  { path: 'add', component: AddClienteComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }