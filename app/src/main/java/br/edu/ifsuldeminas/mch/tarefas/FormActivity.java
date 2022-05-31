package br.edu.ifsuldeminas.mch.tarefas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsuldeminas.mch.tarefas.db.TaskDAO;
import br.edu.ifsuldeminas.mch.tarefas.domain.Task;

public class FormActivity extends AppCompatActivity {

    private Task task = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intentChamadora = getIntent();
        try{
            task = (Task) intentChamadora.getSerializableExtra("task");
            TextInputEditText descriptionText = findViewById(R.id.task_description);
            descriptionText.setText(task.getDescription());
        } catch (Exception e){
            task = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_task: {
                TextInputEditText descriptionText = findViewById(R.id.task_description);
                String description = descriptionText.getText().toString();
                description = description != null ? description : "";

                if(description.equals("")){
                    Toast.makeText(this, R.string.task_description_empty, Toast.LENGTH_SHORT).show();
                } else{
                    if(task != null){
                        TaskDAO taskDAO = new TaskDAO(this);
                        taskDAO.save(task);
                        Toast.makeText(getBaseContext(), R.string.task_update, Toast.LENGTH_SHORT).show();
                    }else{
                        Task task = new Task(0, description);
                        TaskDAO taskDAO = new TaskDAO(this);
                        taskDAO.update(task);
                        Toast.makeText(getBaseContext(), R.string.task_saved, Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}