package es.iesagora.procesoonboarding;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * SharedViewModel
 * --------------------
 * ViewModel compartido por todos los fragments de la Activity.
 * Guarda datos introducidos por el usuario y seleccionados en cada fragment.
 */
public class SharedViewModel extends ViewModel {

    // Datos de LoginFragment
    public String userName = "";
    public String password = "";

    // Datos de ContactDataFragment
    public String phone = "";
    public String birthDate = "";

    // Datos de SportsSelectionFragment
    public List<String> selectedSports = new ArrayList<>();

    // Método auxiliar para calcular la cuota mensual según los deportes
    public int calcularCuota() {
        int total = 0;
        for (String deporte : selectedSports) {
            switch (deporte) {
                case "Pádel": total += 70; break;
                case "Tenis": total += 60; break;
                case "Baloncesto": total += 40; break;
                case "Fútbol": total += 30; break;
            }
        }
        return total;
    }
}
