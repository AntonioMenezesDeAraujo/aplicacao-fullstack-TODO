import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private apiUrl = environment.apiUrl; // URL do backend

  constructor(private http: HttpClient, private router: Router) {}

  // Login -> recebe login e senha, e salva o token
  login(credentials: { login: string; senha: string }) {
    return this.http.post<{ token: string }>(`${this.apiUrl}/usuario/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
      })
    );
  }

  // Cadastro -> envia dados para o backend
  register(data: { nome: string; login: string; senha: string }) {
    return this.http.post(`${this.apiUrl}/usuario`, data);
  }

  // Logout -> remove token e volta para login
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  // Obter token atual
  getToken() {
    return localStorage.getItem('token');
  }

  // Verificar se está logado
  isAuthenticated(): boolean {
    console.log("autenticado: ", !!this.getToken())
    console.log("autenticado 1111: ", this.getToken())
    return !!this.getToken();
  }
}
