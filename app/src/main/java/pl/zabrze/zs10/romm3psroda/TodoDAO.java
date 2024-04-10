package pl.zabrze.zs10.romm3psroda;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDAO {

    @Insert
    public void insertTodo(Todo todo);

    @Update
    public void  updateTodo(Todo todo);

    @Delete
    public void  deleteTodo(Todo todo);

    @Query("Select * from todos")
    public List<Todo> getAllTodo();
}
