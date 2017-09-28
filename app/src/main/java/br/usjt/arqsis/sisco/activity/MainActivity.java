package br.usjt.arqsis.sisco.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.usjt.arqsis.sisco.R;
import br.usjt.arqsis.sisco.TipoDeUsuario;
import br.usjt.arqsis.sisco.model.Usuario;

public class MainActivity extends AppCompatActivity
{
	private Usuario user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (Usuario) getIntent().getSerializableExtra("usuario");
		setPermissoes();
	}

	private void setPermissoes()
	{
		TextView tipo = (TextView) findViewById(R.id.tv_tipo_value);
		Button btAcessCat = (Button) findViewById(R.id.bt_acessar_catraca);
		Button btEmpresas = (Button) findViewById(R.id.bt_empresas);
		Button btUsuarios = (Button) findViewById(R.id.bt_usuarios);
		Button btEnviaArq = (Button) findViewById(R.id.bt_enviar_arq_acesso);
		Button btEnviaRec = (Button) findViewById(R.id.bt_enviar_reconfig_temp);
		Button btConAcess = (Button) findViewById(R.id.bt_consultar_acessos);

		switch (user.getTipo())
		{
			case FUNCIONARIO:
				tipo.setText(getString(R.string.tipo_funcionario));
				btAcessCat.setEnabled(true);
				btEmpresas.setEnabled(false);
				btUsuarios.setEnabled(false);
				btEnviaArq.setEnabled(false);
				btEnviaRec.setEnabled(false);
				btConAcess.setEnabled(false);
				btConAcess.setEnabled(false);
				break;

			case ATENDENTE:
				tipo.setText(getString(R.string.tipo_atendente));
				btAcessCat.setEnabled(true);
				btEmpresas.setEnabled(true);
				btUsuarios.setEnabled(true);
				btEnviaArq.setEnabled(true);
				btEnviaRec.setEnabled(true);
				btConAcess.setEnabled(false);
				break;

			case SINDICO:
				tipo.setText(getString(R.string.tipo_sindico));
				btAcessCat.setEnabled(true);
				btEmpresas.setEnabled(true);
				btUsuarios.setEnabled(true);
				btEnviaArq.setEnabled(true);
				btEnviaRec.setEnabled(true);
				btConAcess.setEnabled(true);
				break;
		}
	}
}
