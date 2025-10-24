package es.iesagora.procesoonboarding.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import es.iesagora.procesoonboarding.SharedViewModel;

public class SummaryFragment extends Fragment {

    private SharedViewModel viewModel;
    private TextView tvSummary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        tvSummary = v.findViewById(R.id.tvSummary);

        StringBuilder resumen = new StringBuilder();
        resumen.append("Usuario: ").append(
                viewModel.username != null ? viewModel.username : "Pendiente de especificar").append("\n");
        resumen.append("Fecha de nacimiento: ").append(
                viewModel.birthDate != null ? viewModel.birthDate : "Pendiente de especificar").append("\n");
        resumen.append("Teléfono: ").append(
                viewModel.phone != null ? viewModel.phone : "Pendiente de especificar").append("\n\n");

        if (viewModel.selectedSports.isEmpty()) {
            resumen.append("No se han seleccionado deportes.");
        } else {
            resumen.append("Deportes: ").append(viewModel.selectedSports).append("\n");
            resumen.append("Cuota mensual: ").append(calcularCuota()).append("€");
        }

        tvSummary.setText(resumen.toString());
        return v;
    }

    private int calcularCuota() {
        int total = 0;
        for (String sport : viewModel.selectedSports) {
            switch (sport) {
                case "Pádel": total += 70; break;
                case "Tenis": total += 60; break;
                case "Baloncesto": total += 40; break;
                case "Fútbol": total += 30; break;
            }
        }
        return total;
    }
}
