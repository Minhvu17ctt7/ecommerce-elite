import axios from 'axios';
import axiosClient from './axiosClients';

export class ProductService {

    getProductsSmall() {
        return axios.get('assets/demo/data/products-small.json').then(res => res.data.data);
    }

    getProducts() {
        return axios.get('assets/demo/data/products.json').then(res => res.data.data);
    }

    getProductsWithOrdersSmall() {
        return axios.get('assets/demo/data/products-orders-small.json').then(res => res.data.data);
    }

    getAllProductFilter(deleted) {
        const url = `/products/all?deleted=${deleted}`;
        return axiosClient.get(url);
    }

    createProduct(product) {
        const url = "/products";
        return axiosClient.post(url, product);
    }

    updateProduct(product) {
        const url = "/products";
        return axiosClient.put(url, product);
    }

    deleteProduct(productId) {
        const url = `/products/${productId}`;
        return axiosClient.delete(url);
    }
}