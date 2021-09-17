package consultant.eyecon.models;

public class PartyLedgerModel {
    String partyTypeID;
    String partyName;
    String balance;
    String quantity = "0";
    boolean add;
    //  String remarks;

    public String getPartyTypeID() {
        return partyTypeID;
    }

    public void setPartyTypeID(String partyTypeID) {
        this.partyTypeID = partyTypeID;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}
