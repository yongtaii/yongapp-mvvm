package com.rnd.jyong.roomtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    private MyDatabase myDatabase;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TodoRepository todoRepository;

    public static final int NEW_TODO_REQUEST_CODE = 200;
    public static final int UPDATE_TODO_REQUEST_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        myDatabase = MyDatabase.getAppDatabase(getApplicationContext());
        todoRepository = new TodoRepository(getApplicationContext());

        // 0번째 spinner로 설정
        spinner.setSelection(0);

        // 앱 최초 실행시 ,DB에 더미값을 넣어 준다
        checkIfAppLaunchedFirstTime();
        initViews();
    }

    private void initViews() {

        // spinner에 category 설정
        String[] categories = {
                "All",
                "Work",
                "Sports",
                "TvShow",
                "Habit"
        };

        ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(categories));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @OnClick({R.id.fab})
    public void mClicked(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivityForResult(new Intent(MainActivity.this, TodoNoteActivity.class), NEW_TODO_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void launchIntent(int id) {
        // listener를 통해 click event를 받고 처리한다
        startActivityForResult(new Intent(MainActivity.this, TodoNoteActivity.class).putExtra("id", id), UPDATE_TODO_REQUEST_CODE);
    }

    @OnItemSelected(R.id.spinner)
    public void onSpinnerItemSelected(Spinner spinner, int position) {
        if (position == 0) {
            todoRepository.getAllTodos(listener);

        } else {
            String category = spinner.getItemAtPosition(position).toString();
            todoRepository.fetchTodoListByCategory(category,listener);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //reset spinners
            spinner.setSelection(0);

            if (requestCode == NEW_TODO_REQUEST_CODE) {
                int id = data.getIntExtra("id", -1);
                todoRepository.fetchTodoById((int)id,listener);


            } else if (requestCode == UPDATE_TODO_REQUEST_CODE) {

                boolean isDeleted = data.getBooleanExtra("isDeleted", false);
                int number = data.getIntExtra("number", -1);
                Log.d("yong","isDeleted : " + isDeleted);
                Log.d("yong","number : " + number);
                todoRepository.getAllTodos(listener);

            }


        }
    }


    private void checkIfAppLaunchedFirstTime() {
        final String PREFS_NAME = "SharedPrefs";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("firstTime", true)) {
            settings.edit().putBoolean("firstTime", false).apply();
            buildDummyTodos();
        }
    }

    private void buildDummyTodos() {

        ArrayList<Todo> todoArrayList = new ArrayList<>();

        Todo todo = new Todo();
        todo.name = "Work Sample Name";
        todo.description = "Work Sample Description";
        todo.category = "Work";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.name = "Sports Sample Name";
        todo.description = "Sports Sample Description";
        todo.category = "Sports";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.name = "Habit Sample Name";
        todo.description = "Habit Sample Description";
        todo.category = "Habit";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.name = "TvShow Sample Name";
        todo.description = "TvShow Sample Description";
        todo.category = "TvShow";

        todoArrayList.add(todo);

        todoRepository.insertTodoList(todoArrayList);
    }

    TodoRepository.TodoRepositoryListener listener = new TodoRepository.TodoRepositoryListener() {
        @Override
        public void deleteTodo(int number) {
        }

        @Override
        public void updateTodo(int number) {
        }

        @Override
        public void insertTodo(int id) {
        }

        @Override
        public void fetchTodoListById(Todo todo) {
            recyclerViewAdapter.addRow(todo);
        }

        @Override
        public void getAllTodos(List<Todo> todos) {
            recyclerViewAdapter.updateTodoList(todos);
        }

        @Override
        public void getListByCategory(List<Todo> todos) {
            recyclerViewAdapter.updateTodoList(todos);
        }
    };
}
