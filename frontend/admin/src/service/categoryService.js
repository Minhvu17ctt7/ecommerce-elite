import axiosClient from './axiosClients';

const categoryApi = {
    getAllCategory: (deleted) => {
        const url = `/admin/categories/all?deleted=${deleted}`;
        return axiosClient.get(url);
    },
    createCategory: (category) => {
        const url = "/admin/categories";
        return axiosClient.post(url, category);
    },
    updateCategories: (category) => {
        const url = "/admin/categories";
        return axiosClient.put(url, category);
    },
    deleteCategory: (categoryId) => {
        const url = `/admin/categories/${categoryId}`;
        return axiosClient.delete(url);
    },
    checkAvailableDeleteCategory: (categoryId) => {
        const url = `/admin/categories/${categoryId}/check-available-delete`;
        return axiosClient.get(url);
    }
}

export default categoryApi;