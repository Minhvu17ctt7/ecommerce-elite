import * as types from '../actions';

const carts = JSON.parse(localStorage.getItem('carts')) || {
    "id": null,
    "totalPrice": 0,
    "totalItem": 0,
    "cartItems": []
};

const initialState = {
    loading: false,
    carts: carts,
    error: null
};

export default function (state = initialState, action) {
    switch (action.type) {

        case types.ADD_TO_CART, types.REMOVE_FROM_CART, types.GET_CART_LOGGED:
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
            localStorage.setItem("carts", JSON.stringify(state.carts))
            return { ...state, isLoading: true, error: null }


        case types.ADD_TO_CART_SUCCESS_LOGGED:
        case types.REMOVE_FROM_CART_SUCCESS_LOGGED:
        case types.GET_CART_LOGGED_SUCCESS:
            return { carts: action.payload, isLoading: true, error: null }

        // action reset cart when logout
        case types.GET_CART:
            localStorage.removeItem("carts");
            const cartReset = {
                "id": null,
                "totalPrice": 0,
                "totalItem": 0,
                "cartItems": []
            };
            return { carts: cartReset, isLoading: true, error: null }
                ;

        case types.REMOVE_FROM_CART_SUCCESS:
            const cartTempRemove = action.payload;
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
            localStorage.setItem("carts", JSON.stringify(state.carts))
            return { ...state, isLoading: true, error: null }
        case types.GET_CART_LOGGED_ERROR:
        case types.ADD_TO_CART_ERROR:
        case types.REMOVE_FROM_CART_ERROR:
            return { ...state, isLoading: true, error: action.payload }
        default:
            return state;
    }
}