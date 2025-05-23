package br.com.projeto.principal;

import br.com.projeto.dao.*;
import br.com.projeto.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final IAlunoDAO alunoDao = new AlunoDAO();
    private static final ICursoDAO cursoDao = new CursoDAO();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int opt;
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Criar Curso");
            System.out.println("2. Listar Cursos");
            System.out.println("3. Atualizar Curso");
            System.out.println("4. Deletar Curso");
            System.out.println("5. Criar Aluno");
            System.out.println("6. Listar Alunos");
            System.out.println("7. Atualizar Aluno");
            System.out.println("8. Deletar Aluno");
            System.out.println("9. Sair");
            System.out.print("Opção: ");
            opt = Integer.parseInt(in.nextLine());
            switch (opt) {
                case 1: createCurso();    break;
                case 2: listCursos();     break;
                case 3: updateCurso();    break;
                case 4: deleteCurso();    break;
                case 5: createAluno();    break;
                case 6: listAlunos();     break;
                case 7: updateAluno();    break;
                case 8: deleteAluno();    break;
                case 9: System.out.println("Tchau!"); break;
                default: System.out.println("Inválido");
            }
        } while (opt != 9);
        in.close();
    }

    // --- Cursos ---
    private static void createCurso() {
        System.out.print("Nome: ");
        String nome = in.nextLine();
        System.out.print("Sigla: ");
        String sigla = in.nextLine();
        System.out.print("Area (EXATAS/HUMANAS/BIOLOGICAS/ARTES): ");
        Area area = Area.valueOf(in.nextLine().toUpperCase());
        Curso c = new Curso(nome, sigla, area);
        cursoDao.create(c);
        System.out.println("Criado: " + c);
    }

    private static void listCursos() {
        List<Curso> l = cursoDao.findAll();
        l.forEach(System.out::println);
    }

    private static void updateCurso() {
        System.out.print("ID do Curso: ");
        Long id = Long.parseLong(in.nextLine());
        Curso c = cursoDao.findById(id);
        if (c == null) { System.out.println("Não existe"); return; }
        System.out.print("Novo nome ("+c.getNome()+"): ");
        String n = in.nextLine(); if (!n.isEmpty()) c.setNome(n);
        System.out.print("Nova sigla ("+c.getSigla()+"): ");
        String s = in.nextLine(); if (!s.isEmpty()) c.setSigla(s);
        System.out.print("Nova area ("+c.getArea()+"): ");
        String a = in.nextLine(); if (!a.isEmpty()) c.setArea(Area.valueOf(a.toUpperCase()));
        cursoDao.update(c);
        System.out.println("Atualizado: " + c);
    }

    private static void deleteCurso() {
        System.out.print("ID do Curso: ");
        cursoDao.delete(Long.parseLong(in.nextLine()));
        System.out.println("Removido");
    }

    // --- Alunos ---
    private static void createAluno() {
        System.out.print("Nome: ");
        String nome  = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(in.nextLine());
        System.out.print("Sigla do Curso: ");
        String sig  = in.nextLine();
        Curso c = cursoDao.findBySigla(sig);
        if (c == null) { System.out.println("Curso inválido"); return; }
        System.out.print("Sexo (M/F): ");
        String sexo = in.nextLine().equalsIgnoreCase("M") ? "Masculino" : "Feminino";
        System.out.print("Maioridade (1=Sim,0=Não): ");
        int m = Integer.parseInt(in.nextLine());
        Aluno a = new Aluno(nome, email, idade, c, sexo, m);
        alunoDao.create(a);
        System.out.println("Criado: " + a);
    }

    private static void listAlunos() {
        alunoDao.findAll().forEach(System.out::println);
    }

    private static void updateAluno() {
        System.out.print("ID do Aluno: ");
        int id = Integer.parseInt(in.nextLine());
        Aluno a = alunoDao.findById(id);
        if (a == null) { System.out.println("Não existe"); return; }
        System.out.print("Nome ("+a.getNome()+"): ");
        String n = in.nextLine(); if (!n.isEmpty()) a.setNome(n);
        System.out.print("Email ("+a.getEmail()+"): ");
        String e = in.nextLine(); if (!e.isEmpty()) a.setEmail(e);
        System.out.print("Idade ("+a.getIdade()+"): ");
        String idd = in.nextLine(); if (!idd.isEmpty()) a.setIdade(Integer.parseInt(idd));
        System.out.print("Sigla curso ("+a.getCurso().getSigla()+"): ");
        String sig = in.nextLine();
        if (!sig.isEmpty()) {
            Curso c2 = cursoDao.findBySigla(sig);
            if (c2 != null) a.setCurso(c2);
            else System.out.println("Sigla inválida, mantida.");
        }
        System.out.print("Sexo ("+a.getSexo()+"): ");
        String sx = in.nextLine(); if (!sx.isEmpty())
            a.setSexo(sx.equalsIgnoreCase("M")?"Masculino":"Feminino");
        System.out.print("Maioridade ("+a.getMaioridade()+"): ");
        String mm = in.nextLine(); if (!mm.isEmpty()) a.setMaioridade(Integer.parseInt(mm));

        alunoDao.update(a);
        System.out.println("Atualizado: " + a);
    }

    private static void deleteAluno() {
        System.out.print("ID do Aluno: ");
        alunoDao.delete(Integer.parseInt(in.nextLine()));
        System.out.println("Removido");
    }
}
