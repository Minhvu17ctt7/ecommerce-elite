import React, { useEffect, useState } from 'react'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import BackDrop from '../../components/BackDrop'
import Categories from '../../components/Home/Categories'
import Featured from '../../components/Home/Featured'
import Navbar from '../../components/Home/Navbar'
import Product from '../../components/Home/Product'

const Home = () => {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState();
    const [isLoading, setIsLoading] = useState(false);
    useEffect(() => {
        (async () => {
            const data = {
                "page": 1,
                "sortField": "name",
                "sortName": "asc",
                "pageSize": 4,
                "search": ""
            }
            setIsLoading(true);
            const categoriesResponse = await categoryApi.getAllCategory();
            const productsResponse = await productApi.getAllProductFilter(data);
            await setProducts(productsResponse.data);
            await setCategories(categoriesResponse.data);
            setIsLoading(false)
        })()
    }, []);

    return isLoading ? (<BackDrop />) : (
        <>
            <Navbar categories={categories} />
            <Featured />
            <Categories categories={categories} />
            <Product productPagination={products} />
        </>
    )
}

export default Home;
