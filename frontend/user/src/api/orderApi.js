import axiosClient from './axiosClients';

const orderApi = {
    createOrder: (data) => {
        const url = '/orders';
        return axiosClient.post(url, data);
    },
    getListOrder: (page, pageSize) => {
        const url = `/orders/page/${page}?pageSize=${pageSize}`;
        return axiosClient.get(url);
    }
}

export default orderApi;