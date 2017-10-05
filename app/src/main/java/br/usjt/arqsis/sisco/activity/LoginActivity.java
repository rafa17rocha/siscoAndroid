package br.usjt.arqsis.sisco.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import br.usjt.arqsis.sisco.R;
import br.usjt.arqsis.sisco.model.Usuario;
import br.usjt.arqsis.sisco.util.JsonFacade;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity
{
	// COLOQUE AQUI O CAMINHO DO TOMCAT
	private static final String PATH_DO_TOMCAT = "http://192.168.15.10:8080/";

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

		// Check for a valid user.
		if (TextUtils.isEmpty(user))
		{
			mUserView.setError(getString(R.string.error_field_required));
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

			String url = PATH_DO_TOMCAT + "sisco/login";

			mAuthTask = new UserLoginTask(url, user, password);
			mAuthTask.execute((Void) null);
		}
	}

	private void startMain(Usuario usuario)
	{
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("usuario", usuario);
		startActivity(intent);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class UserLoginTask extends AsyncTask<Void, Void, Boolean>
	{

		private final String mUrl;
		private final String mLogin;
		private final String mSenha;
		public Usuario usuario;

		UserLoginTask(String url, String login, String senha)
		{
			mUrl = url;
			mLogin = login;
			mSenha = senha;
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			String path = mUrl + "?login=" + mLogin + "&senha=" + mSenha;

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(path).build();

			Response response;

			try
			{
				response = client.newCall(request).execute();
				usuario = JsonFacade.jsonToUsuario(response.body().string());

				if (usuario != null)
				{
					return true;
				}

			}
			catch (IOException e)
			{
				Log.e("Erro: ", e.getMessage());
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean validado)
		{
			mAuthTask = null;

			if (validado)
			{
				startMain(usuario);
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

