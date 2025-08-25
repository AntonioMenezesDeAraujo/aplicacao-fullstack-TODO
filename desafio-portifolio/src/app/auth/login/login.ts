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
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  login = '';
  senha = '';

  constructor(private auth: Auth, private router: Router) {}

  onSubmit() {
    this.auth.login({ login: this.login, senha: this.senha }).subscribe(() => {
      this.router.navigate(['/tasks']);
    });
  }

  goRegister() {
    this.router.navigate(['/register']);
  }
}
