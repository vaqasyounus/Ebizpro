package consultant.eyecon.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.content.ClipData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RealEstateDao {
    String a = "";

    @Insert
    void insert(RealEstate... items);

    @Query("select * from realestate_table order by area")
    List<RealEstate> getAll();

    @Query("select * from realestate_table where ID = :id")
    RealEstate getById(String id);

    //Spinner Two
    @Query("select distinct Area from realestate_table")
    List<String> getAreasForSpinnerTwo();

    @Query("select distinct Project from realestate_table")
    List<String> getProjectsForSpinnerTwo();

    @Query("select distinct Size from realestate_table")
    List<String> getSizesForSpinnerTwo();

    @Query("select distinct FileType from realestate_table")
    List<String> getFileTypesForSpinnerTwo();

    @Query("select distinct SellerDetail from realestate_table")
    List<String> getSellerDetailsForSpinnerTwo();

    //Grid
    @Query("select * from realestate_table order by area")
    List<RealEstate> getAllForGrid();

    @Query("select * from realestate_table where Area = :spinnerTwoValue order by area")
    List<RealEstate> getAreaForGrid(String spinnerTwoValue);

    @Query("select * from realestate_table where Project = :spinnerTwoValue order by area")
    List<RealEstate> getProjectForGrid(String spinnerTwoValue);

    @Query("select * from realestate_table where Size = :spinnerTwoValue order by area")
    List<RealEstate> getSizeForGrid(String spinnerTwoValue);

    @Query("select * from realestate_table where FileType = :spinnerTwoValue order by area")
    List<RealEstate> getFileTypeForGrid(String spinnerTwoValue);

    @Query("select * from realestate_table where SellerDetail = :spinnerTwoValue order by area")
    List<RealEstate> getSellerDetailForGrid(String spinnerTwoValue);

    @Query("delete from realestate_table")
    void deleteAll();
}

