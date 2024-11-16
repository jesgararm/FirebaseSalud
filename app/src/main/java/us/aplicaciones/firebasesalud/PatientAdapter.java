package us.aplicaciones.firebasesalud;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private final List<Paciente> patientList;

    public PatientAdapter(List<Paciente> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new PatientViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Paciente patient = patientList.get(position);
        holder.textViewId.setText("Nuhsa: " + patient.getNuhsa());
        holder.textViewName.setText("Nombre: " + patient.getNombre() + " " + patient.getApellidos() + "\n"
                + "Grupo sanguíneo: " + patient.getGrupoSanguineo());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewName;
        @SuppressLint("CutPasteId")
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(android.R.id.text1);
            textViewName = itemView.findViewById(android.R.id.text2);
        }
    }
}