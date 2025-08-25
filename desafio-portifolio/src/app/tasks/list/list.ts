import { Component, Inject, ViewChild, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { FormsModule } from '@angular/forms';
import { TaskService} from '../task';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';

export interface Task {
  id: number;
  titulo: string;
  descricao: string;
  status: string;
  dataCriacao: Date;
}


export interface TarefaPageResponse {
  content: Task[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Component({
  selector: 'app-tasks-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatDialogModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatPaginatorModule,
    MatSortModule,
    MatPaginator, 
    MatSort
  ],
  templateUrl: './list.html',
  styleUrls: ['./list.css']
})
export class List implements AfterViewInit {
  displayedColumns: string[] = ['titulo', 'status', 'dataCriacao', 'acoes'];
  dataSource = new MatTableDataSource<Task>([]);
  totalPaginas = 1;
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;

  constructor(private dialog: MatDialog, private taskService: TaskService, private router: Router) {}

   ngOnInit() {
    console.log("executando consulta")
    this.loadTasks(0, 10);
    console.log("resultado: ", this.dataSource.data )
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadTasks(this.pageIndex, this.pageSize);
  }

  loadTasks(pageIndex: number, pageSize: number) {
    this.taskService.getTasks("", pageIndex, pageSize).subscribe(tasks => {
      console.log("tarefas: ", tasks.tarefas)
      this.dataSource.data = tasks.tarefas;
      this.totalPaginas = tasks.totalPages;
      this.totalElements = tasks.totalElements;

      // Atualiza as informações do paginator
      if (this.paginator) {
        this.paginator.length = this.totalElements;
        this.paginator.pageIndex = pageIndex;
        this.paginator.pageSize = pageSize;
      }
    });
  }

  logout() {
    localStorage.removeItem('token'); // ou sessionStorage
    this.router.navigate(['/login']); // redireciona para tela de login

    // Impede voltar para a tela anterior
    history.pushState(null, '', window.location.href);
    window.onpopstate = () => {
      history.pushState(null, '', window.location.href);
    };
  }

  ngAfterViewInit() {
    setTimeout(() => {
      if (this.paginator) {
        this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;

        this.paginator.page.subscribe(event => {
          this.loadTasks(event.pageIndex, event.pageSize);
        });
      }
    });
  }


  openCreateDialog() {
    const dialogRef = this.dialog.open(TaskCreateDialog, { width: '400px' });
    dialogRef.afterClosed().subscribe((result?: Task) => {
      if (result) {
        this.taskService.createTask(result).subscribe({
          next: () => {
            this.loadTasks(this.pageIndex, this.pageSize); // recarrega tabela
          },
          error: (err) => {
            console.error("Erro ao salvar tarefa", err);
          }
        });
      }
    });
  }

  openViewDialog(task: Task) {
    this.dialog.open(TaskViewDialog, { data: task });
  }

  openEditDialog(task: Task) {
    const dialogRef = this.dialog.open(TaskEditDialog, { data: { ...task }, width: '400px' });

    dialogRef.afterClosed().subscribe((result?: Task) => {
      if (result) {
        this.taskService.updateTask(result).subscribe({
          next: () => {
            this.loadTasks(this.pageIndex, this.pageSize); // recarrega lista
          },
          error: (err) => {
            console.error("Erro ao atualizar tarefa", err);
          }
        });
      }
    });
  }

  openDeleteDialog(task: Task) {
    const dialogRef = this.dialog.open(TaskDeleteDialog, { data: task });

    dialogRef.afterClosed().subscribe(confirm => {
      if (confirm) {
        this.taskService.deleteTask(task.id).subscribe({
          next: () => {
            // após exclusão no backend, recarrega lista
            this.loadTasks(this.pageIndex, this.pageSize);
          },
          error: (err) => {
            console.error("Erro ao excluir tarefa", err);
          }
        });
      }
    });
  }

}

/* ---- Dialogs ---- */

@Component({
  selector: 'task-create-dialog',
  standalone: true,
  imports: [FormsModule, CommonModule, MatFormFieldModule, MatInputModule, MatDialogModule, MatButtonModule, MatCardModule],
  template: `
    <mat-card>
      <h2 class="title">Nova Tarefa</h2>

      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Título</mat-label>
        <input matInput [(ngModel)]="task.titulo" />
      </mat-form-field>

      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Descrição</mat-label>
        <textarea matInput [(ngModel)]="task.descricao"></textarea>
      </mat-form-field>

      <div class="actions">
        <button mat-button mat-dialog-close>Cancelar</button>
        <button mat-raised-button color="primary" [mat-dialog-close]="task">Cadastrar</button>
      </div>
    </mat-card>
  `,
  styles: [`
    mat-card { padding: 20px; border-radius: 12px; background: #fff; }
    .title { color: #3f51b5; margin-bottom: 15px; text-align: center; }
    .full-width { width: 100%; }
    .actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 15px; }
  `]
})
export class TaskCreateDialog {
  task: Partial<Task> = { titulo: '', descricao: '' };
}

@Component({
  selector: 'task-view-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, CommonModule, MatCardModule],
  template: `
    <mat-card>
      <h2 class="title">{{data.titulo}}</h2>
      <p><b>Descrição:</b> {{data.descricao}}</p>
      <p><b>Status:</b> {{data.status}}</p>
      <div class="actions">
        <button mat-raised-button color="primary" (click)="finalizar()">Finalizar</button>
        <button mat-button mat-dialog-close>Fechar</button>
      </div>
    </mat-card>
  `,
  styles: [`
    mat-card { padding: 20px; border-radius: 12px; background: #fff; }
    .title { color: #3f51b5; margin-bottom: 15px; text-align: center; }
    .actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
  `]
})
export class TaskViewDialog {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Task,
    private dialogRef: MatDialogRef<TaskViewDialog>
  ) {}
  finalizar() {
    this.data.status = 'Finalizada';
    this.dialogRef.close(this.data);
  }
}

@Component({
  selector: 'task-edit-dialog',
  standalone: true,
  imports: [FormsModule, CommonModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatDialogModule, MatButtonModule, MatCardModule],
  template: `
    <mat-card>
      <h2 class="title">Editar Tarefa</h2>
      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Título</mat-label>
        <input matInput [(ngModel)]="data.titulo" />
      </mat-form-field>

      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Descrição</mat-label>
        <textarea matInput [(ngModel)]="data.descricao"></textarea>
      </mat-form-field>

      <mat-form-field appearance="outline" class="full-width">
        <mat-label>Status</mat-label>
        <mat-select [(ngModel)]="data.status">
          <mat-option value="PENDENTE">Pendente</mat-option>
          <mat-option value="EM_ANDAMENTO">Em andamento</mat-option>
          <mat-option value="CONCLUIDA">Concluída</mat-option>
        </mat-select>
      </mat-form-field>

      <div class="actions">
        <button mat-raised-button color="primary" [mat-dialog-close]="data">Atualizar</button>
        <button mat-button mat-dialog-close>Cancelar</button>
      </div>
    </mat-card>
  `,
  styles: [`
    mat-card { padding: 20px; border-radius: 12px; background: #fff; }
    .title { color: #3f51b5; margin-bottom: 15px; text-align: center; }
    .full-width { width: 100%; }
    .actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
  `]
})
export class TaskEditDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Task) {}
}

@Component({
  selector: 'task-delete-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, CommonModule, MatCardModule],
  template: `
    <mat-card>
      <h2 class="title">Confirmar Exclusão</h2>
      <p>Tem certeza que deseja deletar a tarefa <b>{{data.titulo}}</b>?</p>
      <div class="actions">
        <button mat-raised-button color="warn" [mat-dialog-close]="true">Deletar</button>
        <button mat-button mat-dialog-close>Cancelar</button>
      </div>
    </mat-card>
  `,
  styles: [`
    mat-card { padding: 20px; border-radius: 12px; background: #fff; }
    .title { color: #e53935; margin-bottom: 15px; text-align: center; }
    .actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
  `]
})
export class TaskDeleteDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Task) {}
}
