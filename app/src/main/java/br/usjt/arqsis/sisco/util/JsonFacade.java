package br.usjt.arqsis.sisco.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import br.usjt.arqsis.sisco.TipoDeUsuario;
import br.usjt.arqsis.sisco.model.Usuario;

public class JsonFacade
{

	public static Usuario jsonToUsuario(String json)
	{
		try
		{
			JSONObject reg = new JSONObject(json);


			TipoDeUsuario tipo = null;

			// Workaround
			switch (reg.getInt("tipo"))
			{
				case 0:
					tipo = TipoDeUsuario.FUNCIONARIO;
					break;
				case 1:
					tipo = TipoDeUsuario.ATENDENTE;
					break;
				case 2:
					tipo = TipoDeUsuario.SINDICO;
					break;
			}

			int id = reg.getInt("id");
			String nome = reg.getString("nome");
			long cpf = reg.getLong("cpf");
			String expediente = reg.getString("expediente");
			boolean livreAcesso = reg.getBoolean("livreAcesso");
			boolean alteraArEmpresa = reg.getBoolean("alteraAr");
			String usuario = reg.getString("usuario");
			String senha = reg.getString("senha");

			Usuario u = new Usuario();
			u.setId(id);
			u.setTipo(tipo);
			u.setNome(nome);
			u.setCpf(cpf);
			u.setExpediente(expediente);
			u.setLivreAcesso(livreAcesso);
			u.setAlteraAr(alteraArEmpresa);
			u.setUsuario(usuario);
			u.setSenha(senha);
			return u;
		}
		catch (JSONException e)
		{
			Log.e("Erro: ", e.getMessage());
		}

		return null;
	}
}
