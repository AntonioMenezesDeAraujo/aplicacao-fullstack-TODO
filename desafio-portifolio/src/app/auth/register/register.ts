import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Auth } from '../auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  template: `
    <div class="background">
      <div class="container">
        <mat-card>
          <h2 class="title">Cadastro</h2>
          <form (ngSubmit)="onSubmit()">
            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Nome</mat-label>
              <input matInput [(ngModel)]="nome" name="nome" required />
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Usuário</mat-label>
              <input matInput [(ngModel)]="login" name="login" required />
            </mat-form-field>

            <mat-form-field appearance="outline" class="full-width">
              <mat-label>Senha</mat-label>
              <input matInput [(ngModel)]="senha" name="senha" type="password" required />
            </mat-form-field>

            <div class="actions">
              <button 
                mat-raised-button 
                color="primary" 
                type="button"
                (click)="onSubmit()">
                Cadastrar
              </button>
              <button mat-button color="accent" type="button" (click)="goLogin()">Voltar</button>
            </div>
          </form>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .background {
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background: #fdf6e3;
      position: relative;
      overflow: hidden;
      background-image:
        repeating-linear-gradient(
          to bottom,
          #fdf6e3,
          #fdf6e3 23px,
          #a2c3f0 24px
        );
    }

    .background::before {
      content: '';
      position: absolute;
      left: 80px;
      top: 0;
      bottom: 0;
      width: 2px;
      background: #e57373;
    }

    .background::after {
      content: '';
      position: absolute;
      left: 40px;
      top: 50px;
      width: 12px;
      height: 12px;
      border-radius: 50%;
      background: #f0f0f0;
      box-shadow:
        0 80px 0 #f0f0f0,
        0 160px 0 #f0f0f0,
        0 240px 0 #f0f0f0,
        0 320px 0 #f0f0f0;
    }

    .container {
      display: flex;
      justify-content: center;
      width: 100%;
      position: relative;
      z-index: 2;
    }

    mat-card {
      width: 400px;
      padding: 30px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.15);
      border-radius: 12px;
      background: rgba(255,255,255,0.9);
    }

    .title {
      text-align: center;
      margin-bottom: 20px;
      font-weight: 600;
      color: #3f51b5;
    }

    .full-width {
      width: 100%;
    }

    .actions {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
  `]
})
export class Register {
  nome = '';
  login = '';
  senha = '';

  constructor(private auth: Auth, private router: Router) {}

onSubmit() {
  console.log('Chamando backend com:', this.nome, this.login, this.senha);
  this.auth.register({ nome: this.nome, login: this.login, senha: this.senha })
    .subscribe({
      next: (res) => {
        console.log('Sucesso:', res);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Erro no cadastro:', err);
      }
    });
}

  goLogin() {
    this.router.navigate(['/login']);
  }
}

