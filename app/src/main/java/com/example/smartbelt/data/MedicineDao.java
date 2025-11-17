package com.example.smartbelt.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Medicine medicine);
    @Update
    void update(Medicine medicine);
    @Delete()
    void delete(Medicine medicine);
    @Query("SELECT * FROM medicines ORDER BY id DESC")
    List<Medicine> getAllMedicines();
    @Query("DELETE FROM medicines")
    void deleteAll();
}
