import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Auth } from '../auth';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  login = '';
  senha = '';

  constructor(private auth: Auth, private router: Router, private snackBar: MatSnackBar) {}

  onSubmit() {
    this.auth.login({ login: this.login, senha: this.senha }).subscribe({
      next: () => {
        this.router.navigate(['/tasks']);
      },
      error: (err) => {
        console.log(err.error)
        this.snackBar.open(err.error?.mensagem || 'Erro ao fazer login', 'X', {
          duration: 5000,
          panelClass: ['error-snackbar'],
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
      }
    });
  }


  goRegister() {
    this.router.navigate(['/register']);
  }
}
