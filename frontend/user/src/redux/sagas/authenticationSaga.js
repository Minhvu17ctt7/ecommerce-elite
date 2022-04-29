import { put } from 'redux-saga/effects';
import userApi from '../../api/userApi';

import * as types from '../actions'

export function* registerSaga(payload) {
  try {
    const response = yield userApi.login(payload.user);
    yield put({ type: types.REGISTER_USER_SUCCESS, response: response.data });
  } catch (error) {
    yield put({ type: types.REGISTER_USER_ERROR, error });
  }
}

export function* loginSaga(payload) {
  try {
    const response = yield userApi.login(payload.user);
    yield put({ type: types.LOGIN_USER_SUCCESS, response: response.data });
  } catch (error) {
    yield put({ type: types.LOGIN_USER_ERROR, error })
  }
}