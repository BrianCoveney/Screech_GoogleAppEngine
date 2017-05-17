package persistor;

import model.CarBean;

import java.util.ArrayList;

/**
 * Created by brian on 20/03/17.
 */
public interface IPersistor {

    void writeCars(ArrayList<CarBean> carBean);

}
