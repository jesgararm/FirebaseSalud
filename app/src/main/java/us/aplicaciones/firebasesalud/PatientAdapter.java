package us.aplicaciones.firebasesalud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        // Evento del botón Eliminar
        holder.buttonDeletePatient.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pacientes");
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Eliminar paciente")
                    .setMessage("¿Estás seguro de que deseas eliminar este paciente?")
                    .setPositiveButton("Sí", (dialog, which) -> databaseReference.child(patient.getNuhsa()).removeValue()
                            .addOnSuccessListener(unused -> {
                                patientList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, patientList.size());
                            }).addOnSuccessListener(unused -> Toast.makeText(v.getContext(), "Paciente eliminado", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(v.getContext(), "Error al eliminar el paciente", Toast.LENGTH_SHORT).show()))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewName;

        Button buttonDeletePatient;

        @SuppressLint("CutPasteId")
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(android.R.id.text1);
            textViewName = itemView.findViewById(android.R.id.text2);
            buttonDeletePatient = itemView.findViewById(R.id.buttonDeletePatient);
        }
    }
}