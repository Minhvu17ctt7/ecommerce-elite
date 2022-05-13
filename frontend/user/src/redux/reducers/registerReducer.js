import * as types from '../actions';

const initialState = {
  isLoading: false,
  error: null,
  user: null
};

export default function (state = initialState, action) {

  switch (action.type) {
    case types.REGISTER_USER:
      return { user: null, isLoading: true, error: null }
    case types.REGISTER_USER_SUCCESS:
      return { error: null, user: action.response, isLoading: false };
    case types.REGISTER_USER_ERROR:
      return { user: null, error: action.error, isLoading: false };
    case types.REFRESH_REGISTER:
      return { ...initialState };
    default:
      return state;
  }
}