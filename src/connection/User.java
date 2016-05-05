package connection;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 2L;

	String nome, email, senha;

	public User(String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


}
