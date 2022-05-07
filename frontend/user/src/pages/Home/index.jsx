import React, { useEffect, useState } from 'react'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import Categories from '../../components/Home/Categories'
import Featured from '../../components/Home/Featured'
import Navbar from '../../components/Home/Navbar'
import Product from '../../components/Home/Product'

const Home = () => {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState();
    useEffect(() => {
        (async () => {
            const categoriesResponse = await categoryApi.getAllcategory();
            const productsResponse = await productApi.getAllProduct();
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

export default Home