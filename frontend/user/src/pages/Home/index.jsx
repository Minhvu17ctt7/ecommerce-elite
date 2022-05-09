import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import Categories from '../../components/Home/Categories'
import Featured from '../../components/Home/Featured'
import Navbar from '../../components/Home/Navbar'
import Product from '../../components/Home/Product'
import { showToast } from '../../redux/actions/toastActions'

const Home = () => {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState();
    useEffect(() => {
        (async () => {
            const data = {
                "page": 1,
                "sortField": "name",
                "sortName": "asc",
                "pageSize": 4,
                "search": ""
            }
            const categoriesResponse = await categoryApi.getAllCategory();
            const productsResponse = await productApi.getAllProductFilter(data);
            setProducts(productsResponse.data);
            setCategories(categoriesResponse.data);
        })()
    }, []);


    return (
        <>
            <Navbar categories={categories} />
            <Featured />
            <Categories categories={categories} />
            <Product productPagination={products} />
        </>
    )
}

export default Home;
