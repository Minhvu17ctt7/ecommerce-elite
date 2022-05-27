import { combineReducers } from 'redux';
import register from './registerReducer';
import login from './loginReducer';
import toast from './toastReducer'
import cart from './cartReducer';

const rootReducer = combineReducers({
  register, login, toast, cart
});

export default rootReducer;