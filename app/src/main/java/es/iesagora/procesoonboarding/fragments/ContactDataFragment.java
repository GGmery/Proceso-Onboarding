package es.iesagora.procesoonboarding.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Calendar;

import es.iesagora.procesoonboarding.R;
import es.iesagora.procesoonboarding.SharedViewModel;
import es.iesagora.procesoonboarding.databinding.FragmentContactDataBinding;

/**
 * ContactDataFragment
 * --------------------
 * Fragment que solicita al usuario su número de teléfono y fecha de nacimiento.
 * Demuestra el ciclo de vida básico de un fragment y el uso de ViewBinding + Navigation Component.
 */
public class ContactDataFragment extends Fragment {

    // Enlace al layout mediante ViewBinding
    private FragmentContactDataBinding binding;

    // ViewModel compartido para mantener los datos del usuario entre fragments
    private SharedViewModel viewModel;

    // Controlador de navegación (NavController)
    private NavController navController;

    /**
     * onCreateView()
     * ----------------
     * Se encarga de inflar el layout XML y devolver la vista raíz.
     * Aquí solo se crea la interfaz, pero aún no se configura la lógica.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout y obtenemos el binding
        binding = FragmentContactDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * onViewCreated()
     * ----------------
     * Se llama justo después de crear la vista.
     * Aquí ya podemos acceder a todos los elementos visuales y configurar los listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos el ViewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Obtenemos el NavController (controla la navegación entre fragments)
        navController = Navigation.findNavController(view);

        // Desactivar edición manual del campo de fecha
        binding.etBirthDate.setFocusable(false);

        // Mostrar DatePickerDialog al pulsar sobre el campo de fecha
        binding.etBirthDate.setOnClickListener(v -> showDatePicker());

        // Acción del botón "Continuar"
        binding.btnContinue.setOnClickListener(v -> {
            String phone = binding.etPhone.getText().toString().trim();
            String birthDate = binding.etBirthDate.getText().toString().trim();

            if (phone.isEmpty() || birthDate.isEmpty()) {
                Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Guardamos los datos en el ViewModel
                viewModel.phone = phone;
                viewModel.birthDate = birthDate;

                // Navegamos al siguiente fragment (Selección de deportes)
                navController.navigate(R.id.action_contactDataFragment_to_sportsSelectionFragment);
            }
        });

        // Acción del botón "Omitir"
        binding.btnSkip.setOnClickListener(v ->
                navController.navigate(R.id.action_contactDataFragment_to_summaryFragment)
        );
    }

    /**
     * Método auxiliar para mostrar el selector de fecha (DatePickerDialog).
     */
    private void showDatePicker() {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                (view, y, m, d) -> binding.etBirthDate.setText(d + "/" + (m + 1) + "/" + y),
                year, month, day);

        dialog.show();
    }

    /**
     * onDestroyView()
     * ----------------
     * Se ejecuta cuando la vista del fragment se destruye.
     * Es el momento de limpiar el binding para evitar fugas de memoria.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
