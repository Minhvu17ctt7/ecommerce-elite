import * as types from './index';

export const registerUserAction = (user) => {
  return {
    type: types.REGISTER_USER,
    user
  }
};

export const loginUserAction = (user) => {
  return {
    type: types.LOGIN_USER,
    user
  }
};

export const logoutUserAction = (user) => {
  return {
    type: types.LOGOUT_USER
  }
};

export const refreshUserAction = (user) => {
  return {
    type: types.REFRESH_LOGIN
  }
};

export const refreshRegisterAction = (user) => {
  return {
    type: types.REFRESH_REGISTER
  }
};