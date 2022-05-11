import axiosClient from './axiosClients';

const ProductService = {

    getAllProductFilter: (deleted) => {
        const url = `/admin/products/all?deleted=${deleted}`;
        return axiosClient.get(url);
    },

    createProduct: (product) => {
        const url = "/admin/products";
        return axiosClient.post(url, product);
    },

    updateProduct: (product) => {
        const url = "/admin/products";
        return axiosClient.put(url, product);
    },

    deleteProduct: (productId) => {
        const url = `/admin/products/${productId}`;
        return axiosClient.delete(url);
    }
}

export default ProductService;