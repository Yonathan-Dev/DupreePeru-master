package com.dupreincaperu.dupree.mh_fragments_menu.pedidos;

/**
 * Created by marwuinh@gmail.com on 3/6/19.
 */

public interface BasePedido {
    void offersEditable(boolean offersEditable);
    void productsEditable(boolean productsEditable);
    void updateTotal();
    void updateCarrito();
}
