package br.com.projeto.principal;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.dao.IAlunoDAO;
import br.com.projeto.model.Aluno;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final IAlunoDAO dao = new AlunoDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n--- CRUD Alunos ---");
            System.out.println("1. Criar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar Aluno");
            System.out.println("5. Deletar Aluno");
            System.out.println("6. Listar por Curso");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1: create(); break;
                case 2: listAll(); break;
                case 3: findById(); break;
                case 4: update(); break;
                case 5: delete(); break;
                case 6: findByCurso(); break;
                case 7: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida");
            }
        } while (option != 7);
        scanner.close();
    }

    private static void create() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Curso: ");
        String curso = scanner.nextLine();
        Aluno aluno = new Aluno(nome, email, idade, curso);
        dao.create(aluno);
        System.out.println("Criado: " + aluno);
    }

    private static void listAll() {
        List<Aluno> alunos = dao.findAll();
        alunos.forEach(System.out::println);
    }

    private static void findById() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Aluno aluno = dao.findById(id);
        System.out.println(aluno != null ? aluno : "Aluno não encontrado");
    }

    private static void update() {
        System.out.print("ID do Aluno a atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Aluno aluno = dao.findById(id);
        if (aluno != null) {
            System.out.print("Novo nome (" + aluno.getNome() + "): ");
            String nome = scanner.nextLine();
            System.out.print("Novo email (" + aluno.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Nova idade (" + aluno.getIdade() + "): ");
            int idade = Integer.parseInt(scanner.nextLine());
            System.out.print("Novo curso (" + aluno.getCurso() + "): ");
            String curso = scanner.nextLine();
            if (!nome.isEmpty()) aluno.setNome(nome);
            if (!email.isEmpty()) aluno.setEmail(email);
            aluno.setIdade(idade);
            if (!curso.isEmpty()) aluno.setCurso(curso);
            dao.update(aluno);
            System.out.println("Atualizado: " + aluno);
        } else {
            System.out.println("Aluno não encontrado");
        }
    }

    private static void delete() {
        System.out.print("ID do Aluno a deletar: ");
        int id = Integer.parseInt(scanner.nextLine());
        dao.delete(id);
        System.out.println("Aluno deletado");
    }

    private static void findByCurso() {
        System.out.print("Curso: ");
        String curso = scanner.nextLine();
        List<Aluno> alunos = dao.findByCurso(curso);
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado para o curso: " + curso);
        } else {
            alunos.forEach(System.out::println);
        }
    }
}
