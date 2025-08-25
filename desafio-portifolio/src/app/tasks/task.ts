import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Auth } from '../auth/auth';
import { environment } from '../../environments/environment';

export interface Task {
  id: number;
  titulo: string;
  descricao: string;
  status: string;
  dataCriacao: Date;
}

export interface TarefaPageResponse {
  tarefas: Task[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = `${environment.apiUrl.replace(/\/$/, '')}/tarefa`;

  constructor(private http: HttpClient, private auth: Auth) {}

  getTasks(titulo?: string, page?: number, size?: number): Observable<TarefaPageResponse> {
    const token = this.auth.getToken();
    console.log("valor do token ", token)
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    let params = new HttpParams()
      .set('page', page ?? 0)
      .set('size', size ?? 10)
      .set('sortBy', "titulo")
      .set('direction', "asc");

    // if (titulo) {
    //   params = params.set('titulo', titulo);
    // }

    this.http.get<Task[]>(this.apiUrl, { headers, params }).subscribe({
      next: (resultado) => {
        console.log("resultado da API: ", resultado);
      },
      error: (erro) => {
        console.log("Erro na chamada da API: ", erro);
      }
    });

    return this.http.get<TarefaPageResponse>(this.apiUrl, { headers, params });
  }


  createTask(task: Partial<Task>): Observable<Task> {
    const token = this.auth.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.post<Task>(this.apiUrl, task, { headers });
  }

  updateTask(task: Task): Observable<Task> {
    const token = this.auth.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.put<Task>(`${this.apiUrl}`, task, { headers });
  }

  deleteTask(id: number): Observable<void> {
    const token = this.auth.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    
    return this.http.delete<void>(`${this.apiUrl}/${id}`,  { headers });
  }
}
