import axiosClient from './axiosClients';

const orderApi = {
    createOrder: (data) => {
        const url = '/orders';
        return axiosClient.post(url, data);
    }
}

export default orderApi;