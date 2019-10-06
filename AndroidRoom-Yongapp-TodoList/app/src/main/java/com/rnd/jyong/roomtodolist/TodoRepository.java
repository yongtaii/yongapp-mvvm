package com.rnd.jyong.roomtodolist;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;

    TodoRepository(Context context) {
        MyDatabase db = MyDatabase.getAppDatabase(context);
        todoDao = db.todoDao();
    }

    public void fetchTodoById(int todo_id, TodoRepositoryListener listener) {
        new fetchTodoListByIdAsyncTask(todoDao,listener).execute(todo_id);
    }

    public void getAllTodos(TodoRepositoryListener listener){
        new getAllTodosAsyncTask(todoDao,listener).execute();
    }

    public void fetchTodoListByCategory(String category,TodoRepositoryListener listener){
        new getListByCategoryAsyncTask(todoDao,listener).execute(category);
    }

    public void insertTodo(Todo todo,TodoRepositoryListener listener) {
        new insertTodoAsyncTask(todoDao,listener).execute(todo);
    }

    public void insertTodoList(List<Todo> todoList) {
        new insertTodoListAsyncTask(todoDao).execute(todoList);
    }

    public void updateTodo(Todo todo,TodoRepositoryListener listener) {
        new updateTodoAsyncTask(todoDao,listener).execute(todo);
    }

    public void deleteTodo(Todo todo,TodoRepositoryListener listener) {
        new deleteTodoAsyncTask(todoDao,listener).execute(todo);
    }

    private static class insertTodoListAsyncTask extends AsyncTask<List<Todo>, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertTodoListAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Todo>... params) {
            mAsyncTaskDao.insertTodoList(params[0]);
            return null;
        }
    }


    private static class getListByCategoryAsyncTask extends AsyncTask<String, Void, List<Todo>> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        getListByCategoryAsyncTask(TodoDao dao,TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected List<Todo> doInBackground(final String... params) {
            return mAsyncTaskDao.fetchTodoListByCategory(params[0]);
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {
            super.onPostExecute(todos);
            if(listener != null){
                listener.getListByCategory(todos);
            }
        }
    }

    private static class getAllTodosAsyncTask extends AsyncTask<Void, Void, List<Todo>> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        getAllTodosAsyncTask(TodoDao dao,TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected List<Todo> doInBackground(final Void... params) {
            return mAsyncTaskDao.fetchAllTodos();
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {
            super.onPostExecute(todos);
            if(listener != null){
                listener.getAllTodos(todos);
            }
        }
    }

    private static class fetchTodoListByIdAsyncTask extends AsyncTask<Integer, Void, Todo> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        fetchTodoListByIdAsyncTask(TodoDao dao,TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Todo doInBackground(final Integer... params) {
            return mAsyncTaskDao.fetchTodoListById(params[0]);
        }

        @Override
        protected void onPostExecute(Todo todo) {
            super.onPostExecute(todo);
            if(listener != null){
                listener.fetchTodoListById(todo);
            }
        }
    }

    private static class insertTodoAsyncTask extends AsyncTask<Todo, Void, Integer> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        insertTodoAsyncTask(TodoDao dao,TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(final Todo... params) {
            return (int)mAsyncTaskDao.insertTodo(params[0]);
        }

        @Override
        protected void onPostExecute(Integer number) {
            super.onPostExecute(number);
            if(listener != null){
                listener.insertTodo(number);
            }
        }
    }

    private static class updateTodoAsyncTask extends AsyncTask<Todo, Void, Integer> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        updateTodoAsyncTask(TodoDao dao,TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(final Todo... params) {
            return mAsyncTaskDao.updateTodo(params[0]);
        }

        @Override
        protected void onPostExecute(Integer number) {
            super.onPostExecute(number);
            if(listener != null){
                listener.updateTodo(number);
            }
        }

    }

    private static class deleteTodoAsyncTask extends AsyncTask<Todo, Void, Integer> {

        private TodoDao mAsyncTaskDao;
        private TodoRepositoryListener listener;

        deleteTodoAsyncTask(TodoDao dao, TodoRepositoryListener listener) {
            mAsyncTaskDao = dao;
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(final Todo... params) {
            return mAsyncTaskDao.deleteTodo(params[0]);
        }

        @Override
        protected void onPostExecute(Integer number) {
            super.onPostExecute(number);
            if(listener != null){
                listener.deleteTodo(number);
            }
        }
    }

    public interface TodoRepositoryListener {
        void deleteTodo(int number);
        void updateTodo(int number);
        void insertTodo(int id);
        void fetchTodoListById(Todo todo);
        void getAllTodos(List<Todo> todos);
        void getListByCategory(List<Todo> todos);
    }
}
