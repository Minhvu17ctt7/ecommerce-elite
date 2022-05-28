import * as types from './index';

export const addToCartAction = (data) => {
    return {
        type: types.ADD_TO_CART,
        data
    }
};

export const removeFromCartAction = (data) => {
    return {
        type: types.REMOVE_FROM_CART,
        data
    }
};

export const getCartLoggedAction = () => {
    return {
        type: types.GET_CART_LOGGED
    }
};

export const getCartAction = () => {
    return {
        type: types.GET_CART
    }
};