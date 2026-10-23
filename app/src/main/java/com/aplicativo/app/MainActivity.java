package com.aplicativo.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ListView listView;

    private SQLiteDatabase db;
    private ArrayList<String> listNotes;
    private ArrayList<Integer> listIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        listNotes = new ArrayList<>();
        listIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNotes);
        listView.setAdapter(adapter);

        setupDatabase();
        loadNotes();

        button.setOnClickListener(v -> addNote());

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteNote(position);
            return true;
        });
    }

    private void setupDatabase() {
        try {
            db = openOrCreateDatabase("bdnotas", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS notas (id INTEGER PRIMARY KEY AUTOINCREMENT, conteudo TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNotes() {
        listNotes.clear();
        listIds.clear();
        try {
            Cursor cursor = db.rawQuery("SELECT id, conteudo FROM notas ORDER BY id DESC", null);
            int idIndex = cursor.getColumnIndex("id");
            int conteudoIndex = cursor.getColumnIndex("conteudo");

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(idIndex);
                    String conteudo = cursor.getString(conteudoIndex);
                    listIds.add(id);
                    listNotes.add(conteudo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNote() {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Por favor, digite algum texto para a nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO notas (conteudo) VALUES (?)");
            stmt.bindString(1, text);
            stmt.executeInsert();

            editText.setText("");
            Toast.makeText(this, "Nota adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            loadNotes();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao salvar nota.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote(int position) {
        if (position < 0 || position >= listIds.size()) return;
        int noteId = listIds.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Excluir Nota")
                .setMessage("Deseja realmente excluir esta nota?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    try {
                        db.execSQL("DELETE FROM notas WHERE id = ?", new Object[]{noteId});
                        Toast.makeText(MainActivity.this, "Nota removida!", Toast.LENGTH_SHORT).show();
                        loadNotes();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        super.onDestroy();
    }
}