import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import toastr from 'toastr'
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
            const categoriesResponse = await categoryApi.getAllcategory();
            const productsResponse = await productApi.getAllProduct();
            setProducts(productsResponse.data);
            setCategories(categoriesResponse.data);
        })()
    }, []);
    const dispatch = useDispatch();
    const handleshowToast = () => {
        dispatch(showToast({
            "title": "error",
            "message": "has error"
        }));
    }

    return (
        <>
            <Navbar categories={categories} />
            <button onClick={() => handleshowToast()}>Toast</button>
            <Featured />
            <Categories categories={categories} />
            <Product productPagination={products} />
        </>
    )
}

export default Home;
