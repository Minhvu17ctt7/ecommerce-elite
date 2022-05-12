import * as types from '../actions';

const initialState = {
  show: false,
  data: {
    "title": "",
    "message": ""
  }
};

export default function (state = initialState, action) {

  switch (action.type) {
    case types.SHOW_TOAST:
      return { show: true, data: action.data };
    case types.CLOSE_TOAST:
      return { show: false, data: action.data };
    default:
      return state;
  }
};