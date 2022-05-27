import { put } from 'redux-saga/effects';
import userApi from '../../api/authApi';
import cartApi from '../../api/cartApi';

import * as types from '../actions'

export function* addToCartSaga(payload) {
    const isLogin = localStorage.getItem("isLogin") === 'true';
    try {
        if (isLogin) {
            const requestData = {
                "list": [{
                    "productId": payload.data.productId,
                    "quantity": payload.data.quantity
                }]
            }
            const response = yield cartApi.addNewItemToCart(requestData);
            yield put({ type: types.ADD_TO_CART_SUCCESS_LOGGED, payload: response.data });
        } else {
            yield put({ type: types.ADD_TO_CART_SUCCESS, payload: payload.data });
        }
    } catch (error) {
        yield put({ type: types.ADD_TO_CART_ERROR, error });
    }
}

export function* removeFromCartSaga(payload) {
    const isLogin = localStorage.getItem("isLogin") == true;
    try {
        if (isLogin) {
            const requestData = {
                "list": [{
                    "productId": payload.data.productId,
                    "quantity": payload.data.quantity
                }]
            }
            const response = yield cartApi.removeItemToCart(requestData);
            yield put({ type: types.REMOVE_FROM_CART_SUCCESS_LOGGED, payload: response.data });
        } else {
            yield put({ type: types.REMOVE_FROM_CART_SUCCESS, payload: payload.data });
        }
    } catch (error) {
        yield put({ type: types.REMOVE_FROM_CART_ERROR, error });
    }
}
