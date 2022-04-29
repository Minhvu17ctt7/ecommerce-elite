import { put } from 'redux-saga/effects';

import * as types from '../actions'

export function* registerSaga(payload) {
  try {
    // const response = yield call(registerUserService, payload);
    yield put({ type: types.REGISTER_USER_SUCCESS, response: payload.user });
  } catch (error) {
    yield put({ type: types.REGISTER_USER_ERROR, error });
  }
}

export function* loginSaga(payload) {
  try {
    // const response = yield call(loginUserService, payload);
    yield put({ type: types.LOGIN_USER_SUCCESS, response: payload.user });
  } catch (error) {
    yield put({ type: types.LOGIN_USER_ERROR, error })
  }
}