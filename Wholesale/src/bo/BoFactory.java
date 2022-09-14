package bo;

import bo.impl.CustomerBOIImpl;
import bo.impl.ItemBOIImpl;
import bo.impl.ManageOrderBOIImpl;
import bo.impl.OrderBOIImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOIImpl();
            case CUSTOMER:
                return new CustomerBOIImpl();
            case MANAGE_ORDER:
                return new ManageOrderBOIImpl();
            case ORDER:
                return new OrderBOIImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER, MANAGE_ORDER
    }
}
