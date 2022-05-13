import * as types from '../actions';

const initialState = {
  loading: false,
  user: null,
  error: null,
  isLogin: false
};

export default function (state = initialState, action) {

  switch (action.type) {
    case types.LOGIN_USER:
      return { user: null, error: null, isLoading: true, isLogin: false };
    case types.LOGIN_USER_SUCCESS:
      return { error: null, user: action.response, isLoading: false, isLogin: true };
    case types.LOGIN_USER_ERROR:
      return { ...state, error: action.error, isLoading: false, isLogin: false };
    case types.LOGOUT_USER:
      return { ...initialState };
    case types.REFRESH_LOGIN:
      return { ...initialState };
    default:
      return state;
  }
};