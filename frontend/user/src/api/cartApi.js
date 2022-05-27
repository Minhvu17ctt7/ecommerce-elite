import axiosClient from './axiosClients';

const cartApi = {
    addNewItemToCart: (data) => {
        const url = '/cart/add';
        return axiosClient.post(url, data);
    },
    removeItemToCart: (data) => {
        const url = '/cart/delete';
        return axiosClient.post(url, data);
    },
    getCart: () => {
        const url = '/cart';
        return axiosClient.get(url);
    }
}

export default cartApi;