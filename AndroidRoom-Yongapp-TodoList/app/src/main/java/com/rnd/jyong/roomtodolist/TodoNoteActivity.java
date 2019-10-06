package com.rnd.jyong.roomtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoNoteActivity extends AppCompatActivity implements TodoRepository.TodoRepositoryListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.inTitle) EditText inTitle;
    @BindView(R.id.inDescription) EditText inDesc;
    @BindView(R.id.btnDone) Button btnDone;
    @BindView(R.id.btnDelete) Button btnDelete;

    private boolean isNewTodo = false;
    private Todo updateTodo;

    String[] categories = {
            "All",
            "Work",
            "Sports",
            "TvShow",
            "Habit"
    };

    public ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(categories));
    private MyDatabase myDatabase;
    private TodoRepository todoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        myDatabase = MyDatabase.getAppDatabase(getApplicationContext());
        todoRepository = new TodoRepository(getApplicationContext());

        int todo_id = getIntent().getIntExtra("id", -100);

        if (todo_id == -100)
            isNewTodo = true;

        if (!isNewTodo) {
            todoRepository.fetchTodoById(todo_id,this);
            btnDelete.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btnDone,R.id.btnDelete})
    public void mClicked(View v) {
        switch (v.getId()) {
            case R.id.btnDone:

                if (isNewTodo) {
                    Todo todo = new Todo();
                    todo.name = inTitle.getText().toString();
                    todo.description = inDesc.getText().toString();
                    todo.category = spinner.getSelectedItem().toString();

                    todoRepository.insertTodo(todo,this);
                } else {
                    updateTodo.name = inTitle.getText().toString();
                    updateTodo.description = inDesc.getText().toString();
                    updateTodo.category = spinner.getSelectedItem().toString();
                    todoRepository.updateTodo(updateTodo,this);
                }

                break;
            case R.id.btnDelete:
                todoRepository.deleteTodo(updateTodo,this);
                break;
        }
    }

    @Override
    public void deleteTodo(int number) {
        Intent intent = getIntent();
        intent.putExtra("isDeleted", true).putExtra("number", number);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void updateTodo(int number) {
        Intent intent = getIntent();
        intent.putExtra("isNew", false).putExtra("number", number);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void insertTodo(int id) {
        Intent intent = getIntent();
        intent.putExtra("isNew", true).putExtra("id", id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void fetchTodoListById(Todo todo) {
        inTitle.setText(todo.name);
        inDesc.setText(todo.description);
        spinner.setSelection(spinnerList.indexOf(todo.category));

        updateTodo = todo;
    }

    @Override
    public void getAllTodos(List<Todo> todos) {
    }

    @Override
    public void getListByCategory(List<Todo> todos) {
    }
}
