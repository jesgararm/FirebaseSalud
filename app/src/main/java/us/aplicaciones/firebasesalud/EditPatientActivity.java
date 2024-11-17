package us.aplicaciones.firebasesalud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPatientActivity extends AppCompatActivity {

    private EditText editTextPatientId, editTextPatientName, editTextPatientSurname, editTextPatientGroup;
    private Button buttonSaveChanges;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_patient);

        // Inicializa las vistas
        editTextPatientId = findViewById(R.id.editTextPatientId);
        editTextPatientName = findViewById(R.id.editTextPatientName);
        editTextPatientSurname = findViewById(R.id.editTextPatientSurname);
        editTextPatientGroup = findViewById(R.id.editTextPatientGroup);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);

        // Referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Pacientes");

        // Obtén los datos del Intent
        String patientId = getIntent().getStringExtra("nuhsa");
        String patientName = getIntent().getStringExtra("nombre");
        String patientSurname = getIntent().getStringExtra("apellidos");
        String patientGroup = getIntent().getStringExtra("grupoSanguineo");

        // Configura los campos con los datos existentes
        editTextPatientId.setText(patientId);
        editTextPatientName.setText(patientName);
        editTextPatientSurname.setText(patientSurname);
        editTextPatientGroup.setText(patientGroup);

        // Botón para guardar los cambios
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        String patientId = editTextPatientId.getText().toString().trim();
        String patientName = editTextPatientName.getText().toString().trim();
        String patientSurname = editTextPatientSurname.getText().toString().trim();
        String patientGroup = editTextPatientGroup.getText().toString().trim();

        if (TextUtils.isEmpty(patientName) || TextUtils.isEmpty(patientSurname) || TextUtils.isEmpty(patientGroup)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea un objeto Patient con los datos actualizados
        Paciente updatedPatient = new Paciente(patientName, patientSurname, patientGroup, patientId);

        // Actualiza en Firebase
        databaseReference.child(patientId).setValue(updatedPatient)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(EditPatientActivity.this, "Paciente actualizado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditPatientActivity.this, "Error al actualizar paciente", Toast.LENGTH_SHORT).show());
    }
}
