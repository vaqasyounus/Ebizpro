package consultant.eyecon.models;

/**
 * Created by Muhammad on 1/7/2017.
 */

public class SettingModel {
    String Database;
    String ipAddress;
    String userName;
    String password;
    String buid;
    String terminalid;

//    public SettingModel(String database, String ipAddress, String userName, String password) {
//        Database = database;
//        this.ipAddress = ipAddress;
//        this.userName = userName;
//        this.password = password;
//    }

    public SettingModel(String database, String ipAddress, String userName, String password, String buid, String terminalid) {
        Database = database;
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
        this.buid = buid;
        this.terminalid = terminalid;
    }

    public String getDatabase() {
        return Database;
    }

    public void setDatabase(String database) {
        Database = database;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }
}
