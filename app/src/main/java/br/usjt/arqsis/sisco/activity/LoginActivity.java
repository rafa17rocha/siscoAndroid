package br.usjt.arqsis.sisco.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.usjt.arqsis.sisco.R;
import br.usjt.arqsis.sisco.TipoDeUsuario;
import br.usjt.arqsis.sisco.model.Usuario;

/**
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity
{

	private static final String[] USUARIOS_TEMPORARIOS = new String[]{
			"sindico:sindico:" + TipoDeUsuario.SINDICO,
			"atendente:atendente:" + TipoDeUsuario.ATENDENTE,
			"funcionario:funcionario:" + TipoDeUsuario.FUNCIONARIO};

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mUserView = (EditText) findViewById(R.id.login_usuario);

		mPasswordView = (EditText) findViewById(R.id.login_senha);

		Button btLogin = (Button) findViewById(R.id.login_logar);
		btLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				attemptLogin();
			}
		});
	}


	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin()
	{
		if (mAuthTask != null)
		{
			return;
		}

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String user = mUserView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(password))
		{
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}

		else if (!isPasswordValid(password))
		{
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid user.
		if (TextUtils.isEmpty(user))
		{
			mUserView.setError(getString(R.string.error_field_required));
			focusView = mUserView;
			cancel = true;
		}
		else if (!isUserValid(user))
		{
			mUserView.setError(getString(R.string.error_invalid_user));
			focusView = mUserView;
			cancel = true;
		}

		if (cancel)
		{
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else
		{
			// Kick off a background task to
			// perform the user login attempt.
			mAuthTask = new UserLoginTask(user, password);
			mAuthTask.execute((Void) null);
		}
	}

	private void startMain(Usuario user)
	{
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("usuario", user);
		startActivity(intent);
	}

	private boolean isUserValid(String user)
	{
		return user.length() >= 6;
	}

	private boolean isPasswordValid(String password)
	{
		return password.length() >= 4;
	}


	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class UserLoginTask extends AsyncTask<Void, Void, Boolean>
	{

		private final String mUsuario;
		private final String mSenha;
		private TipoDeUsuario mTipo;

		UserLoginTask(String user, String password)
		{
			mUsuario = user;
			mSenha = password;
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			for (String tupla : USUARIOS_TEMPORARIOS)
			{
				String[] login = tupla.split(":");
				if (login[0].equals(mUsuario))
				{
					// Conta exite, retorna 'True' se a senha for correta
					if (login[1].equals(mSenha))
					{
						mTipo = TipoDeUsuario.valueOf(login[2]);
						return true;
					}
				}
			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success)
		{
			mAuthTask = null;

			if (success)
			{
				Usuario user = new Usuario();

				user.setUsuario(mUsuario);
				user.setSenha(mSenha);
				user.setTipo(mTipo);

				startMain(user);
//				finish();
			}
			else
			{
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled()
		{
			mAuthTask = null;
		}
	}
}

