import * as types from '../actions';

const initialState = {
  isLoading: false,
  error: null,
  user: null
};

export default function (state = initialState, action) {

  switch (action.type) {
    case types.REGISTER_USER:
      return { state, isLoading: true }
    case types.REGISTER_USER_SUCCESS:
      return { ...state, user: action.response, isLoading: false };
    case types.REGISTER_USER_ERROR:
      return { ...state, error: action.error, isLoading: false };
    default:
      return state;
  }
}