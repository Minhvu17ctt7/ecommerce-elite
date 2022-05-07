import * as types from './index';

export const showToast = (data) => {
    return {
        type: types.SHOW_TOAST,
        data
    }
};

export const closeToast = () => {
    return {
        type: types.CLOSE_TOAST,
        data: {
            "title": "",
            "message": ""
        }
    }
}