package us.aplicaciones.firebasesalud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuscaActivity extends AppCompatActivity {

    //Atributos de la clase
    String nuhsa;
    TextView nombre,apellidos,grupoSanguineo;
    FirebaseDatabase database;
    DatabaseReference puntoAcceso;

    private Button buttonEditPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        //Recupero el String de nuhsa que me pasó la anterior activity
        nuhsa=getIntent().getStringExtra(Constantes.nuhsa);

        //Busco las vistas que tengo que modificar en mi interfaz
        nombre=findViewById(R.id.tvNombre);
        apellidos=findViewById(R.id.tvApellidos);
        grupoSanguineo=findViewById(R.id.tvGrupoSanguineo);

        //Instancia de mi base de datos
        database = FirebaseDatabase.getInstance();

        //Punto de acceso al nuhsa que se nos ha pasado
        puntoAcceso = database.getReference(Constantes.pacientes+"/"+nuhsa);

        //Listener que escucha si hay algún elemento colgando de ese punto de acceso
        puntoAcceso.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Si no es un nuhsa vacío (se podría controlar en la activity anterior) Y hay algo colgando..
                if(!nuhsa.equals("") && dataSnapshot.getValue()!=null) {//Nuhsa encontrado
                    //Recuperamos el paciente y lo mostramos en cada uno de los text view
                    Paciente paciente = dataSnapshot.getValue(Paciente.class);
                    nombre.setText(paciente.getNombre());
                    apellidos.setText(paciente.getApellidos());
                    grupoSanguineo.setText(paciente.getGrupoSanguineo());
                }
                else{
                    //Mostramos un mensaje de no encontrado y volvemos eliminando la activity
                    Toast.makeText(BuscaActivity.this,"Paciente no encontrado",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //Botón para editar
        // Inicializar el botón
        buttonEditPatient = findViewById(R.id.buttonEditPatient);
        // Añadir un listener al botón
        // Configurar el clic en el botón
        buttonEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditPatientActivity();
            }
        });
    }
    private void openEditPatientActivity() {
        // Crear el Intent para abrir EditPatientActivity
        Intent intent = new Intent(BuscaActivity.this, EditPatientActivity.class);
        intent.putExtra("nuhsa", this.nuhsa);
        intent.putExtra("nombre", this.nombre.getText().toString());
        intent.putExtra("apellidos", this.apellidos.getText().toString());
        intent.putExtra("grupoSanguineo", this.grupoSanguineo.getText().toString());
        startActivity(intent);
    }
}
