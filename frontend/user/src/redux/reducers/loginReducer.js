import * as types from '../actions';

export default function (state = [], action) {
  const response = action.response;

  switch (action.type) {
    case types.LOGIN_USER:
      return { ...state, isLoading: true };
    case types.LOGIN_USER_SUCCESS:
      return { ...state, response, isLoading: false };
    case types.LOGIN_USER_ERROR:
      return { ...state, response, isLoading: false };
    default:
      return state;
  }
};