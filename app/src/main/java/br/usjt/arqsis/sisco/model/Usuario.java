package br.usjt.arqsis.sisco.model;

import java.io.Serializable;

import br.usjt.arqsis.sisco.TipoDeUsuario;

public class Usuario implements Serializable
{
	private int id;
	private TipoDeUsuario tipo;
	private String nome;
	private long cpf;
	private String expediente;
	private boolean livreAcesso;
	private boolean alteraAr;
	private String usuario;
	private String senha;

	public void setId(int id)
	{
		this.id = id;
	}

	public void setTipo(TipoDeUsuario tipo)
	{
		this.tipo = tipo;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public void setCpf(long cpf)
	{
		this.cpf = cpf;
	}

	public void setExpediente(String expediente)
	{
		this.expediente = expediente;
	}

	public void setLivreAcesso(boolean livreAcesso)
	{
		this.livreAcesso = livreAcesso;
	}

	public void setAlteraAr(boolean alteraAr)
	{
		this.alteraAr = alteraAr;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public int getId()
	{

		return id;
	}

	public TipoDeUsuario getTipo()
	{
		return tipo;
	}

	public String getNome()
	{
		return nome;
	}

	public long getCpf()
	{
		return cpf;
	}

	public String getExpediente()
	{
		return expediente;
	}

	public boolean isLivreAcesso()
	{
		return livreAcesso;
	}

	public boolean isAlteraAr()
	{
		return alteraAr;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public String getSenha()
	{
		return senha;
	}

	@Override
	public String toString()
	{
		return "Usuario{" + "id=" + id + ", tipo=" + tipo + ", nome='" + nome + '\'' + ", cpf=" + cpf + ", expediente='" + expediente + '\'' + ", livreAcesso=" + livreAcesso + ", alteraAr=" + alteraAr + ", usuario='" + usuario + '\'' + ", senha='" + senha + '\'' + '}';
	}
}
