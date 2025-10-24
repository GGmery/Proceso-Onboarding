package es.iesagora.procesoonboarding.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import es.iesagora.procesoonboarding.databinding.FragmentLoginBinding;
import es.iesagora.procesoonboarding.SharedViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private SharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout con ViewBinding
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el SharedViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inicialmente el botón "Continuar" está deshabilitado
        binding.btnContinue.setEnabled(false);

        // TextWatcher para habilitar el botón solo si ambos campos tienen texto
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        binding.etUsername.addTextChangedListener(watcher);
        binding.etPassword.addTextChangedListener(watcher);

        // Botón para mostrar/ocultar contraseña
        binding.btnToggle.setOnClickListener(v -> {
            if (binding.etPassword.getTransformationMethod() == null) {
                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                binding.etPassword.setTransformationMethod(null);
            }
            // Mantener el cursor al final
            binding.etPassword.setSelection(binding.etPassword.getText().length());
        });

        // Botón "Continuar" guarda datos y navega al fragment siguiente
        binding.btnContinue.setOnClickListener(v -> {
            viewModel.userName = binding.etUsername.getText().toString().trim();
            viewModel.password = binding.etPassword.getText().toString().trim();
            Navigation.findNavController(v)
                    .navigate(es.iesagora.procesoonboarding.R.id.action_loginFragment_to_contactDataFragment);
        });
    }

    // Habilita o deshabilita el botón "Continuar"
    private void checkInputs() {
        boolean filled = !binding.etUsername.getText().toString().isEmpty() &&
                !binding.etPassword.getText().toString().isEmpty();
        binding.btnContinue.setEnabled(filled);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Evitar fugas de memoria
        binding = null;
    }
}
