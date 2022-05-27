import * as types from '../actions';

const initialState = {
    loading: false,
    carts: {
        "id": null,
        "totalPrice": 0,
        "totalItem": 0,
        "cartItems": []
    },
    error: null
};

export default function (state = initialState, action) {
    switch (action.type) {

        case types.ADD_TO_CART, types.REMOVE_FROM_CART:
            return { ...state, isLoading: true, error: null }

        case types.ADD_TO_CART_SUCCESS:
            const cartTempAdd = action.payload;
            state.carts.totalItem += cartTempAdd.quantity;
            state.carts.totalPrice += cartTempAdd.quantity * cartTempAdd.priceProduct;


            if (state.carts.totalItem == 0) {
                state.carts.cartItems.push(cartTempAdd);
            } else {
                let check = false;
                state.carts.cartItems.map((item, key) => {
                    if (item.id == cartTempAdd.id) {
                        state.carts.cartItems[key].quantity += cartTempAdd.quantity;
                        check = true;
                    }
                });
                if (!check) {
                    state.carts.cartItems.push(cartTempAdd);
                }
            }
            return { ...state, isLoading: true, error: null }
        

        case types.REMOVE_FROM_CART_SUCCESS:
            const cartTempRemove = action.payload;
            console.log("cartTempremove: ", cartTempRemove);
            state.carts.totalItem -= cartTempRemove.quantity;
            state.carts.totalPrice -= cartTempRemove.quantity * cartTempRemove.priceProduct;

            state.carts.cartItems.map((item, key) => {
                if (item.id == cartTempRemove.id) {
                    if (item.quantity === cartTempRemove.quantity) {
                        state.carts.cartItems.splice(key, 1);
                    } else {
                        state.carts.cartItems[key].quantity -= cartTempRemove.quantity;
                    }
                }
            });
            return { ...state, isLoading: true, error: null }

        default:
            return state;
    }
}