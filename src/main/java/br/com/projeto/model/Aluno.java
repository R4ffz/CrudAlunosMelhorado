package br.com.projeto.model;

public class Aluno {
    private int id;
    private String nome;
    private String email;
    private int idade;
    private String curso;

    public Aluno() {}

    public Aluno(String nome, String email, int idade, String curso) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.curso = curso;
    }

    public Aluno(int id, String nome, String email, int idade, String curso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.curso = curso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", curso='" + curso + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno aluno = (Aluno) o;
        return id == aluno.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
