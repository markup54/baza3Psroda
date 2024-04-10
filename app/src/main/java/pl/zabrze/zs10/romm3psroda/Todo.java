package pl.zabrze.zs10.romm3psroda;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "todos")
public class Todo {

    @ColumnInfo(name="id_todo")
    @PrimaryKey(autoGenerate = true)
    int id; //getttery i settery???

    @ColumnInfo(name = "nazwaRzeczy")
    private  String nazwa;


    private  Boolean zrobione;

    public Todo(String nazwa) {
        this.nazwa = nazwa;
        this.zrobione = false;
        id =0;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Boolean getZrobione() {
        return zrobione;
    }

    public void setZrobione(Boolean zrobione) {
        this.zrobione = zrobione;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
