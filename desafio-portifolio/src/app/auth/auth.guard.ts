import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { inject } from '@angular/core';
import { Auth } from './auth';

export const authGuard: CanActivateFn = (): boolean | UrlTree => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true; // deixa passar
  }

  // retorna a rota de login como UrlTree (sem navegar manualmente)
  return router.createUrlTree(['/login']);
};
