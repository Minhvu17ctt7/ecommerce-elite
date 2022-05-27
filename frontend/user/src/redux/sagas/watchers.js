import { takeLatest } from 'redux-saga/effects';
import { registerSaga, loginSaga } from './authenticationSaga';

import * as types from '../actions';
import { addToCartSaga, removeFromCartSaga } from './cartSaga';


export default function* watchUserAuthentication() {
  yield takeLatest(types.ADD_TO_CART, addToCartSaga);
  yield takeLatest(types.REMOVE_FROM_CART, removeFromCartSaga);
  yield takeLatest(types.REGISTER_USER, registerSaga);
  yield takeLatest(types.LOGIN_USER, loginSaga);
}