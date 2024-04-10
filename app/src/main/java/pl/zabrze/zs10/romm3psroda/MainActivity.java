package pl.zabrze.zs10.romm3psroda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    TodoDatabase todoDB;
    List<Todo> todoList = new ArrayList<>();
    ArrayAdapter<Todo> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        todoDB =  Room.databaseBuilder(getApplicationContext(),
                TodoDatabase.class, "todo_db")
                .addCallback(myCallback)
                .build();
        Button button = findViewById(R.id.button);
        EditText editText =findViewById(R.id.editTextTodo);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dodajTodoWTle(new Todo(editText.getText().toString()));
                    }
                }
        );
        wypiszWszystkoZbazy();


    }

    public void wypiszWszystkoZbazy(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                       todoList = todoDB.getTodoDAO().getAllTodo();
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               ListView listView = findViewById(R.id.listView);
                               arrayAdapter = new ArrayAdapter<>(
                                       getApplicationContext(),
                                       android.R.layout.simple_list_item_1,
                                       todoList
                               );
                               listView.setAdapter(arrayAdapter);
                           }
                       });
                    }
                }
        );
    }
    public void dodajTodoWTle(Todo todo){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        todoDB.getTodoDAO().insertTodo(todo);
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "dodano do bazy", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                }
        );
    }


}