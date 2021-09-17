package consultant.eyecon.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "realestate_table")
public class RealEstate {
    @PrimaryKey
    @NonNull
    String ID;
    String Date;
    String Code;
    String Area;
    String Project;
    String PlotNO;
    String Size;
    String FileType;
    String Demand;
    String SellerDetail;
    String IsWestOpen;
    String IsCorner;
    String IsRoadFacing;
    String Status;
    String IsNegotiable;
    String FileTypeID;
    String AreaID;
    String ProjectID;
    String AddUserID;
    String PropertyID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String project) {
        Project = project;
    }

    public String getPlotNO() {
        return PlotNO;
    }

    public void setPlotNO(String plotNO) {
        PlotNO = plotNO;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getDemand() {
        return Demand;
    }

    public void setDemand(String demand) {
        Demand = demand;
    }

    public String getSellerDetail() {
        return SellerDetail;
    }

    public void setSellerDetail(String sellerDetail) {
        SellerDetail = sellerDetail;
    }

    public String getIsWestOpen() {
        return IsWestOpen;
    }

    public void setIsWestOpen(String isWestOpen) {
        IsWestOpen = isWestOpen;
    }

    public String getIsCorner() {
        return IsCorner;
    }

    public void setIsCorner(String isCorner) {
        IsCorner = isCorner;
    }

    public String getIsRoadFacing() {
        return IsRoadFacing;
    }

    public void setIsRoadFacing(String isRoadFacing) {
        IsRoadFacing = isRoadFacing;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIsNegotiable() {
        return IsNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        IsNegotiable = isNegotiable;
    }

    public String getFileTypeID() {
        return FileTypeID;
    }

    public void setFileTypeID(String fileTypeID) {
        FileTypeID = fileTypeID;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String areaID) {
        AreaID = areaID;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getAddUserID() {
        return AddUserID;
    }

    public void setAddUserID(String addUserID) {
        AddUserID = addUserID;
    }

    public String getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(String propertyID) {
        PropertyID = propertyID;
    }
}