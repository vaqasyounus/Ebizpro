package consultant.eyecon.interfaces;

import java.sql.ResultSet;

/**
 * Created by Muhammad on 1/4/2017.
 */

public interface IDatabaseCallback {

    void onRequestSuccess(ResultSet resultSet, int requestCode);
}
