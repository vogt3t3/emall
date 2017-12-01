package com.emall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ResponseCartVo {
    private List<CartProductVoList> CartProductVoList;
    private boolean allChecked;
    private BigDecimal cartTotalPrice;

    public List<com.emall.vo.CartProductVoList> getCartProductVoList() {
        return CartProductVoList;
    }

    public void setCartProductVoList(List<com.emall.vo.CartProductVoList> cartProductVoList) {
        CartProductVoList = cartProductVoList;
    }

    public boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }
}
