package br.com.projeto.model;

public class Curso {
    private Long id;
    private String nome;
    private String sigla;
    private Area area;

    public Curso() {}

    // para criação (sem id)
    public Curso(String nome, String sigla, Area area) {
        this.nome = nome;
        this.sigla = sigla;
        this.area  = area;
    }

    // para leitura completa
    public Curso(Long id, String nome, String sigla, Area area) {
        this.id    = id;
        this.nome  = nome;
        this.sigla = sigla;
        this.area  = area;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", area=" + area +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso c = (Curso) o;
        return id != null && id.equals(c.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
