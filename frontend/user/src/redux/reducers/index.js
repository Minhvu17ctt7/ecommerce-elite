import { combineReducers } from 'redux';
import register from './registerReducer';
import login from './loginReducer';
import toast from './toastReducer'

const rootReducer = combineReducers({
  register, login, toast
});

export default rootReducer;