import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { List } from './tasks/list/list';
import { authGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: 'login', component: Login},
  { path: 'register', component: Register },
  { 
    path: 'tasks',
    component: List,
    canActivate: [authGuard] 
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
