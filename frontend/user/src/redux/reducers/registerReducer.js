import * as types from '../actions';

export default function (state = [], action) {
  let response = action.response;

  switch (action.type) {
    case types.REGISTER_USER:
      return { state, isLoading: true }
    case types.REGISTER_USER_SUCCESS:
      return { ...state, response, isLoading: false };
    case types.REGISTER_USER_ERROR:
      return { ...state, response, isLoading: false };
    default:
      return state;
  }
}