package br.com.projeto.model;

public class Aluno {
    private int id;
    private String nome;
    private String email;
    private int idade;
    private Curso curso;
    private String sexo;
    private int maioridade;    // 0 ou 1

    public Aluno() { }

    public Aluno(String nome, String email, int idade,
                 Curso curso, String sexo, int maioridade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.curso = curso;
        this.sexo = sexo;
        this.maioridade = maioridade;
    }

    public Aluno(int id, String nome, String email, int idade,
                 Curso curso, String sexo, int maioridade) {
        this(nome, email, idade, curso, sexo, maioridade);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public int getMaioridade() { return maioridade; }
    public void setMaioridade(int maioridade) { this.maioridade = maioridade; }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", curso=" + curso.getSigla() +
                ", sexo='" + sexo + '\'' +
                ", maioridade=" + maioridade +
                '}';
    }
}
