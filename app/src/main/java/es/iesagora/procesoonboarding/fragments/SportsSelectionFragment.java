package es.iesagora.procesoonboarding.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import es.iesagora.procesoonboarding.R;
import es.iesagora.procesoonboarding.SharedViewModel;

public class SportsSelectionFragment extends Fragment {

    private SharedViewModel viewModel;
    private CheckBox cbPadel, cbTenis, cbBasket, cbFutbol;
    private Button btnContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sports_selection, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        cbPadel = v.findViewById(R.id.cbPadel);
        cbTenis = v.findViewById(R.id.cbTenis);
        cbBasket = v.findViewById(R.id.cbBasket);
        cbFutbol = v.findViewById(R.id.cbFutbol);
        btnContinue = v.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(view -> {
            viewModel.selectedSports.clear();
            if (cbPadel.isChecked()) viewModel.selectedSports.add("Pádel");
            if (cbTenis.isChecked()) viewModel.selectedSports.add("Tenis");
            if (cbBasket.isChecked()) viewModel.selectedSports.add("Baloncesto");
            if (cbFutbol.isChecked()) viewModel.selectedSports.add("Fútbol");
            Navigation.findNavController(view)
                    .navigate(R.id.action_sportsSelectionFragment_to_summaryFragment);
        });

        return v;
    }
}