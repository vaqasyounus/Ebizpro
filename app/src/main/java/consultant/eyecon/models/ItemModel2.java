package consultant.eyecon.models;

import android.support.annotation.NonNull;

/**
 * Created by Muhammad Waqas on 02-Feb-2017.
 */

public class ItemModel2 implements Comparable<ItemModel2> {
    String itemId;
    String itemName;
    String itemDesc;
    String parentId;
    String barCode;
    String purchasePrice;
    String salePrice;
    String unitMeasure;


    String tittle;
    byte[] image;
    String quantity;
    String remarks;
    boolean isAssembly;
    double tax;
    private boolean expanded;


    public ItemModel2() {
        this.expanded = false;

    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isAssembly() {
        return isAssembly;
    }

    public void setAssembly(boolean assembly) {
        isAssembly = assembly;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    @Override
    public int compareTo(@NonNull ItemModel2 o) {
        return 0;
    }
    @Override
    public String toString() {
        return "ItemModel2{" +
                "itemName='" + itemName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", remarks='" + remarks + '\'' +
                ", expanded=" + expanded +
                '}';
    }

}
