package ku.cs.service;

import ku.cs.model.Order;
import ku.cs.model.OrderList;

public class OrderFilterer implements Filterer<OrderList> {

    private int cid;
    public OrderFilterer(int cid) {
        this.cid = cid;
    }

    @Override
    public OrderList filter(OrderList orderList) {
        OrderList filtererd = new OrderList();
        for (Order order : orderList.getOrders()) {
            if (order.getCid() == cid) {
                filtererd.addOrder(order);
            }
        }
        return filtererd;
    }
}
